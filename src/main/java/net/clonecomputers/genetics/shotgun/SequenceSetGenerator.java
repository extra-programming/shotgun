package net.clonecomputers.genetics.shotgun;

import java.util.*;

public class SequenceSetGenerator {
	private final Random r;
	public static final List<String> basePairs = Arrays.asList(
			"a",
			"c",
			"t",
			"g"
	);
	public static final String unreadableBasePair = "?";

	public static void main(String[] args) {
		SequenceSetGenerator gen = new SequenceSetGenerator();
		Collection<String> sequences = gen.sliceGenomeIntoSequences(
				gen.generateGenome(100000), 8, 100);
		for(String s: sequences) {
			System.out.printf("%d\t%s\n",s.length(),s);
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
	public String generateGenome(int length) {
		StringBuilder genome = new StringBuilder();
		for(int i = 0; i < length; i++) {
			genome.append(basePairs.get(r.nextInt(basePairs.size())));
		}
		return genome.toString();
	}
	
	/**
	 * Generate several squences of DNA by duplicating a genome {@code numCopies} times,
	 * then slice them up so that each sequence is shorter than maxSize. 
	 * @param genome the original genome
	 * @param numCopies how many copies of it to slice
	 * @param maxSize the maximum size of each fragment
	 * @return a bunch of fragments of {@code genome}
	 */
	public List<String> sliceGenomeIntoSequences(String genome,
			int numCopies, int maxSize) {
		List<String> sequences = new ArrayList<>();
		for(int i = 0; i < numCopies; i++) {
			sequences.add(genome);
		}
		while(true) {
			List<String> newSequences = new LinkedList<>();
			for(Iterator<String> it = sequences.iterator(); it.hasNext();) {
				String s = it.next();
				if(s.length() > maxSize) {
					it.remove();
					int cutLoc = r.nextInt(s.length() - 1) + 1;
					newSequences.add(s.substring(0, cutLoc));
					newSequences.add(s.substring(cutLoc));
				}
			}
			sequences.addAll(newSequences);
			if(newSequences.isEmpty()) break;
		}
		return sequences;
	}
	
	/**
	 * Corrupt a bunch of sequences of base pairs, to simulate the sequencing process. 
	 * This is equivalent to calling {@link #corruptSequence(String, double, double) corruptSequence} on each sequence. 
	 * 
	 * @see {@link #corruptSequence(String, double, double) corruptSequence}
	 */
	public void corruptSequences(List<String> sequences,
			double unreadableProbability, double misreadProbability) {
		for(ListIterator<String> li = sequences.listIterator(); li.hasNext();) {
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
	public String corruptSequence(String sequence,
			double unreadableProbability, double misreadProbability) {
		if(unreadableProbability + misreadProbability > 1) {
			throw new IllegalArgumentException("Sum of probabilities cannot be greater than 1");
		}
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < sequence.length(); i++) {
			String basePair = sequence.substring(i, i+1);
			double outcome = r.nextDouble();
			if(outcome < misreadProbability) {
				sb.append(randomOtherBase(basePair));
			} else if(r.nextDouble() < misreadProbability + unreadableProbability) {
				sb.append(unreadableBasePair);
			} else {
				sb.append(basePair);
			}
		}
		return null;
	}
	
	/**
	 * Generate a random base that is not {@code currentBase}. 
	 * @param currentBase
	 * @return a random base that is not {@code currentBase}
	 */
	private String randomOtherBase(String currentBase) {
		//TODO: this could be a lot better and more efficient
		String otherBase = basePairs.get(r.nextInt(basePairs.size()));
		if(otherBase.equals(currentBase)) return randomOtherBase(currentBase);
		return otherBase;
	}

}
