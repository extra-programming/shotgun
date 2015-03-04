package net.clonecomputers.genetics.shotgun;

import java.util.*;

public class SequenceSetGenerator {
	private final Random r;

	public static void main(List<Base>[] args) {
		SequenceSetGenerator gen = new SequenceSetGenerator();
		Collection<List<Base>> sequences = gen.sliceGenomeIntoSequences(
				gen.generateGenome(100000), 8, 100);
		for(List<Base> s: sequences) {
			System.out.printf("%d\t%s\n",s.size(),s);
		}
	}
	
	public SequenceSetGenerator() {
		r = new Random();
	}
	
	public SequenceSetGenerator(long seed) {
		r = new Random(seed);
	}
	
	/**
	 * Generate a random genome of maximum length {@code length}
	 * @param length
	 * @return
	 */
	public List<Base> generateGenome(int length) {
		List<Base> genome = new ArrayList<Base>();
		for(int i = 0; i < length; i++) {
			genome.add(Base.randomBase(r));
		}
		return genome;
	}
	
	/**
	 * Generate several squences of DNA by duplicating a genome {@code numCopies} times,
	 * then slice them up so that each sequence is shorter than maxSize. 
	 * @param genome the original genome
	 * @param numCopies how many copies of it to slice
	 * @param maxSize the maximum size of each fragment
	 * @return a bunch of fragments of {@code genome}
	 */
	public List<List<Base>> sliceGenomeIntoSequences(List<Base> genome,
			int numCopies, int maxSize) {
		List<List<Base>> sequences = new ArrayList<>();
		for(int i = 0; i < numCopies; i++) {
			sequences.add(genome);
		}
		while(true) {
			List<List<Base>> newSequences = new LinkedList<>();
			for(Iterator<List<Base>> it = sequences.iterator(); it.hasNext();) {
				List<Base> s = it.next();
				if(s.size() > maxSize) {
					it.remove();
					int cutLoc = r.nextInt(s.size() - 1) + 1;
					newSequences.add(s.subList(0, cutLoc));
					newSequences.add(s.subList(cutLoc, s.size()));
				}
			}
			sequences.addAll(newSequences);
			if(newSequences.isEmpty()) break;
		}
		return sequences;
	}
	
	/**
	 * Corrupt a bunch of sequences of base pairs, to simulate the sequencing process. 
	 * This is equivalent to calling {@link #corruptSequence(List<Base>, double, double) corruptSequence} on each sequence. 
	 * 
	 * @see {@link #corruptSequence(List<Base>, double, double) corruptSequence}
	 */
	public void corruptSequences(List<List<Base>> sequences,
			double unreadableProbability, double misreadProbability) {
		for(ListIterator<List<Base>> li = sequences.listIterator(); li.hasNext();) {
			li.set(corruptSequence(li.next(), unreadableProbability, misreadProbability));
		}
	}
	
	/**
	 * Corrupt a sequence of base pairs, to simulate the sequencing process. 
	 * @param sequence is the sequence to corrupt
	 * @param unreadableProbability is the probability that a base pair is unreadable
	 * @param misreadProbability is the probability that a base pair is read as another base pair
	 * @return the corrupted sequence
	 */
	public List<Base> corruptSequence(List<Base> sequence,
			double unreadableProbability, double misreadProbability) {
		if(unreadableProbability + misreadProbability > 1) {
			throw new IllegalArgumentException("Sum of probabilities cannot be greater than 1");
		}
		List<Base> seq = new ArrayList<Base>();
		for(int i = 0; i < sequence.size(); i++) {
			Base basePair = sequence.get(i);
			double outcome = r.nextDouble();
			if(outcome < misreadProbability) {
				seq.add(basePair.randomOtherBase(r));
			} else if(r.nextDouble() < misreadProbability + unreadableProbability) {
				seq.add(Base.UNKNOWN);
			} else {
				seq.add(basePair);
			}
		}
		return seq;
	}

}
