package net.clonecomputers.genetics.shotgun;

import java.util.*;

public class SequenceSetGenerator {
	private final Random r;
	public static final String[] basePairs = {
			"a",
			"c",
			"t",
			"g",
	};
	public static final String unreadableBasePair = "?";

	public static void main(String[] args) {
		SequenceSetGenerator gen = new SequenceSetGenerator();
		Collection<String> sequences = gen.generateSequences(
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
	
	public String generateGenome(int length) {
		StringBuilder genome = new StringBuilder();
		for(int i = 0; i < length; i++) {
			genome.append(basePairs[r.nextInt(basePairs.length)]);
		}
		return genome.toString();
	}
	
	/**
	 * A method to generate 
	 * @param genome
	 * @param numCopies
	 * @param maxSize
	 * @return
	 *///TODO: finish javadoc
	public List<String> generateSequences(String genome,
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
	
	public void corruptSequences(List<String> sequences,
			double unreadableProbability, double misreadProbability) {
		for(ListIterator<String> li = sequences.listIterator(); li.hasNext();) {
			li.set(corruptSequence(li.next(), unreadableProbability, misreadProbability));
		}
	}
	
	/**
	 * Corrupts a sequence of base pairs, to simulate the sequencing process.
	 * @param sequence is the sequence to corrupt
	 * @param unreadableProbability is the probability that a base pair is unreadable
	 * @param misreadProbability is the probability that a base pair is read as another base pair
	 * @return the corrupted sequence
	 */
	private String corruptSequence(String sequence,
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
	
	public String randomOtherBase(String currentBase) {
		//TODO: this could be a lot better and more efficient
		String otherBase = basePairs[r.nextInt(4)];
		if(otherBase.equals(currentBase)) return randomOtherBase(currentBase);
		return otherBase;
	}

}
