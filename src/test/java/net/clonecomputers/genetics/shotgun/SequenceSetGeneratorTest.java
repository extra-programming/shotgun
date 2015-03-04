package net.clonecomputers.genetics.shotgun;

import static org.junit.Assert.*;

import static java.lang.Math.*;

import java.util.function.IntConsumer;

import org.junit.Test;

public class SequenceSetGeneratorTest {
	
	@Test
	public void testGenerateGenome() {  
		int length = 1000000;
		double requiredConfidence = 0.99;
		String seq = new SequenceSetGenerator().generateGenome(length);
		int[] numsBasePairs = new int[4]; // a,c,g,t
		seq.chars().forEach(new IntConsumer() {
			@Override
			public void accept(int value) {
				switch(value) {
				case 'a':
					numsBasePairs[0]++;
					break;
				case 'c':
					numsBasePairs[1]++;
					break;
				case 'g':
					numsBasePairs[2]++;
					break;
				case 't':
					numsBasePairs[3]++;
					break;
				default:
					fail((char)value+" is not an acceptable base pair");
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
		fail("test not written yet");
		//TODO: write test
	}
	
	@Test
	public void testCorruptSequences() {
		fail("test not written yet");
		//TODO: write test
	}
	
	@Test
	public void testRandomOtherBase() {
		fail("test not written yet");
		//TODO: write test
	}

}
