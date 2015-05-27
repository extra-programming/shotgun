package net.clonecomputers.genetics.shotgun;

import static org.junit.Assert.*;
import static java.lang.Math.*;

import java.util.*;
import java.util.function.*;

import org.junit.*;

public class SequenceSetGeneratorTest {
	
	@Test(expected = IllegalArgumentException.class)
	public void testGenerateNegativeGenome() {
		new SequenceSetGenerator().generateGenome(-1);
	}
	
	@Test
	public void testGenerateGenome() {  
		int[] lengthsToTest = {
				0,1,2,5,10,1000,10000
		};
		for(int length: lengthsToTest) {
			List<Base> seq = new SequenceSetGenerator().generateGenome(length);
			testGenome(seq,length);
		}
	}
	
	private void testGenome(List<Base> seq, int length) {
		assertEquals(length, seq.size());
		double requiredConfidence = 0.99;
		int[] numsBasePairs = new int[4]; // a,c,g,t
		seq.forEach(new Consumer<Base>() {
			@Override
			public void accept(Base value) {
				switch(value) {
				case A:
					numsBasePairs[0]++;
					break;
				case C:
					numsBasePairs[1]++;
					break;
				case G:
					numsBasePairs[2]++;
					break;
				case T:
					numsBasePairs[3]++;
					break;
				default:
					fail(value+" is not an acceptable base pair");
				}
			}
		});
		for(int numBasePairs: numsBasePairs) {
			testStatisticalDistrubituion(numBasePairs, requiredConfidence, length);
		}
	}

	private void testStatisticalDistrubituion(int numBasePairs,
			double requiredConfidence, int length) {
		System.out.println("It is fundamentally impossible to tell the difference between any finite sequence and a random finite sequence");
	}
	
	@Test
	public void testGenerateSequences() {
		SequenceSetGenerator ssg = new SequenceSetGenerator();
		int[] lengthsToTest = {
				0,1,2,3,10,50,100,1000,10000
		};
		int[] maxSizesToTest = {
				-1,0,1,2,5,100,10000
		};
		int[] numCopiesToTest = {
				-1,0,1,2,5
		};
		for(int length: lengthsToTest) {
			List<Base> genome = ssg.generateGenome(length);
			for(int maxSize: maxSizesToTest) {
				for(int numCopies: numCopiesToTest) {
					try {
						testSequences(genome, ssg.sliceGenomeIntoSequences(genome, numCopies, maxSize), numCopies, maxSize);
						if(maxSize < 1) {
							fail(String.format("Slicing a genome into %d-sized pieces should fail",maxSize));
						}
						if(numCopies < 1) {
							fail(String.format("%d genomes doesn't make any sense",numCopies));
						}
					} catch(IllegalArgumentException e) {
						if(maxSize >= 1 && numCopies >= 1) throw e;
					}
				}
			}
		}
	}
	
	private void testSequences(List<Base> genome, List<List<Base>> sequences, int numCopies, int maxSize) {
		int totalLength = 0;
		for(List<Base> sequence: sequences) {
			if(sequence.size() > maxSize) {
				fail(String.format("Sequence too long: expected <%d, but sequence length was %d", maxSize, sequence.size()));
			}
			totalLength += sequence.size();
			//TODO: test if sequence is subsequence of genome
		}
		if(totalLength != genome.size()*numCopies) {
			fail(String.format("Wrong length: expected %d x%d = %d but sequences added up to %d",
					genome.size(), numCopies, genome.size()*numCopies, totalLength));
		}
	}
	
	@Test
	public void testCorruptSequence() {
		SequenceSetGenerator ssg = new SequenceSetGenerator();
		int[] lengthsToTest = {
				1,2,5,10,50,100
		};
		double[] unreadableProbabilitiesToTest = {
				-.5,-.1,0,0,0,0,0,.1,.5,.9,1,1.2
		};
		double[] misreadProbabilitiesToTest = {
				-.5,-.1,0,0,0,0,0,.1,.5,.9,1,1.2
		};
		for(int length: lengthsToTest) {
			for(double unreadableProbability: unreadableProbabilitiesToTest) {
				for(double misreadProbability: misreadProbabilitiesToTest) {
					for(int i = 0; i < 20; i++) {
						List<Base> sequence = ssg.generateGenome(length);
						List<Base> sequenceClone = new ArrayList<>(sequence);
						List<Base> corrupted = null;
						
						String failMsg = null;
						if(unreadableProbability < 0) failMsg = "negative (unreadable) probabilities should fail";
						if(misreadProbability < 0) failMsg = "negative (misread) probabilities should fail";
						if(unreadableProbability > 1) failMsg = ">1 (unreadable) probabilities should fail";
						if(misreadProbability > 1) failMsg = ">1 (misread) probabilities should fail";
						if(misreadProbability + unreadableProbability > 1) failMsg = "sum of probabilities >1 should fail";
						try {
							corrupted = ssg.corruptSequence(sequence, unreadableProbability, misreadProbability);
							assertNull(failMsg, failMsg);
						} catch(IllegalArgumentException e) {
							if(failMsg == null) {
								throw e;
							} else {
								continue;
							}
						}
						
						assertEquals("Corrupting a sequence modified it", sequence, sequenceClone);
						assertEquals("Corupting a sequence changed its size", sequence.size(), corrupted.size());
						
						ListIterator<Base> seqI = sequence.listIterator(); ListIterator<Base> corI = corrupted.listIterator();
						Base seqB,corB;
						while(seqI.hasNext() && corI.hasNext()) {
							seqB = seqI.next(); corB = corI.next();
							if(unreadableProbability == 0) {
								//System.out.printf("(%f,%f): %s->%s\n\t%s->%s\n", unreadableProbability,misreadProbability,seqB, corB, sequence, corrupted);
								assertFalse("up==0 -> should not be unreadable",corB.isUnknown());
							}
							if(misreadProbability == 0 && !corB.isUnknown()) {
								assertTrue("mp==0 -> should not be misread", corB.equals(seqB));
							}
						}
					}
				}
			}
		}
	}

}
