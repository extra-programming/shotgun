package net.clonecomputers.genetics.shotgun;

import static org.junit.Assert.*;
import static java.lang.Math.*;
import static net.clonecomputers.genetics.shotgun.Base.*;

import java.util.*;
import java.util.function.*;

import org.junit.*;

public class BaseTest {

	@Test
	public void testRandomOtherBase() {
		Random r = new Random();
		for(Base b: Base.values()) {
			for(int i = 0; i < 1000; i++) {
				try{
					Base b2 = b.randomOtherBase(r);
					assertFalse("RandomOtherBase on unknown should throw an exception", b.isUnknown());
					assertNotNull(b2);
					assertNotEquals("RandomOtherBase on "+b+" returned the same base", b, b2);
					assertFalse("RandomOtherBase on "+b+" returned unknown",b2.isUnknown());
				} catch(IllegalArgumentException e) {
					if(!b.isUnknown()) throw e;
				}
			}
		}
	}
	
	@Test
	public void testIsUnknown() {
		assertFalse(A.isUnknown());
		assertFalse(C.isUnknown());
		assertFalse(T.isUnknown());
		assertFalse(G.isUnknown());
		assertTrue(UNKNOWN.isUnknown());
	}
	
	@Test
	public void testGetOpposite() {
		assertEquals(A.getOpposite(),T);
		assertEquals(C.getOpposite(),G);
		assertEquals(G.getOpposite(),C);
		assertEquals(T.getOpposite(),A);
		assertEquals(UNKNOWN.getOpposite(),UNKNOWN);
		for(Base b: Base.values()) {
			assertEquals(b.getOpposite().getOpposite(), b);
		}
	}
	
	@Test
	public void testListOfBases() {
		List<Base> bases = Arrays.asList(Base.values());
		assertTrue(bases.contains(A));
		assertTrue(bases.contains(C));
		assertTrue(bases.contains(T));
		assertTrue(bases.contains(G));
		assertTrue(bases.contains(UNKNOWN));
		assertEquals(bases.size(),5);
	}
	
	@Test
	public void testIsOpposite() {
		Random r = new Random();
		for(Base b: Base.values()) {
			assertTrue(UNKNOWN.isOpposite(b));
			assertTrue(b.isOpposite(UNKNOWN));
			for(Base b2: Base.values()) {
				assertEquals(b.isOpposite(b2), b2.isOpposite(b));
			}
			assertTrue(b.isOpposite(b.getOpposite()));
			if(!b.isUnknown()) {
				for(int i = 0; i < 100; i++) {
					assertFalse(b.isOpposite(b.getOpposite().randomOtherBase(r)));
				}
			}
		}
	}
	
	@Test
	public void testRandomBase() {
		Random r = new Random();
		List<Base> realBases = Arrays.asList(A,C,T,G);
		for(int i = 0; i < 1000; i++) {
			Base b = randomBase(r);
			assertFalse(b.isUnknown());
			assertTrue(realBases.contains(b));
		}
		//TODO: test some statistcal stuff?
	}
}
