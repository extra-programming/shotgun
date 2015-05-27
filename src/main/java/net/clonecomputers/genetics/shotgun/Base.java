package net.clonecomputers.genetics.shotgun;

import java.util.*;

public enum Base {
	A,
	C,
	G,
	T,
	UNKNOWN;
	
	private static final Base[] bases = {A,C,G,T};
	private final Base[] otherBases = new Base[3];
	
	static {
		for(Base b: bases) {
			int j = 0;
			for(Base b2: bases) {
				if(b2.equals(b)) continue;
				b.otherBases[j++] = b2;
			}
		}
	}

	public boolean isOpposite(Base other) {
		return this.isUnknown() || other.isUnknown() ||
				other.equals(this.getOpposite());
	}
	
	public Base getOpposite() {
		switch(this) {
		case A:
			return T;
		case C:
			return G;
		case G:
			return C;
		case T:
			return A;
		case UNKNOWN:
			return UNKNOWN;
		default:
			throw new IllegalStateException("Who the hell are we? (" + this + ")");
		}
	}

	public boolean isUnknown() {
		return this.equals(UNKNOWN);
	}
	
	public static Base randomBase(Random r) {
		return bases[r.nextInt(4)];
	}
	
	public Base randomOtherBase(Random r) {
		if(this.isUnknown()) throw new IllegalArgumentException("RandomOtherBase on UNKNOWN makes no sense");
		return otherBases[r.nextInt(3)];
	}
}
