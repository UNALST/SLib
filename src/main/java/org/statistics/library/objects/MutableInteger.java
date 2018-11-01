package org.statistics.library.objects;

public class MutableInteger {
	public static final MutableInteger ZERO = new MutableInteger(0);
	
	private int integer;
	
	public MutableInteger(final int integer) {
		this.integer = integer;
	}
	
	public int intValue() {
		return this.integer;
	}
	
	public void setValue(final int integer) {
		this.integer = integer;
	}
	
	public int add(final int amount) {
		return this.integer += amount;
	}
	
	public int subtract(final int amount) {
		return this.integer -= amount;
	}
	
	@Override
	public boolean equals(final Object object) {
		return this == object || (object != null && this.getClass() == object.getClass() && this.integer == ((MutableInteger) object).integer);
	}
	
	@Override
	public int hashCode() {
		return this.integer;
	}
}