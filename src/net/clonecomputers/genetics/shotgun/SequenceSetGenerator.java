package net.clonecomputers.genetics.shotgun;

import java.util.*;

public class SequenceSetGenerator {
	private final Random r;
	public final String[] basePairs = {
			"a",
			"c",
			"t",
			"g",
	};

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
	
	public Collection<String> generateSequences(String genome,
			int numCopies, int maxSize) {
		Collection<String> sequences = new ArrayList<>();
		for(int i = 0; i < numCopies; i++) {
			sequences.add(genome);
		}
		while(true) {
			Collection<String> newSequences = new LinkedList<>();
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

}
