package net.clonecomputers.genetics.shotgun;

import static org.junit.Assert.*;
import static java.lang.Math.*;

import java.util.*;
import java.util.function.*;

import org.junit.*;

public class SequenceSetGeneratorTest {
	
	@Test
	public void testGenerateGenome() {  
		int length = 1000000;
		double requiredConfidence = 0.99;
		List<Base> seq = new SequenceSetGenerator().generateGenome(length);
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
