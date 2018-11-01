package org.statistics.library;

import java.util.Map.Entry;
import java.util.Objects;

public class Statistic<A> implements Entry<A, Integer> {
	private final A key;
	private Integer value;

	public Statistic(final A key, final int value) {
		this.key = key;
		this.value = value;
	}

	public Statistic(final A key) {
		this(key, 0);
	}

	@Override
	public boolean equals(final Object object) {
		return this == object || (object != null && this.getClass() == object.getClass() && this.key.equals(((Statistic<?>) object).getKey()));
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.key);
	}

	@Override
	public String toString() {
		return this.key.toString();
	}

	@Override
	public A getKey() {
		return this.key;
	}

	@Override
	public Integer getValue() {
		return this.value;
	}

	@Override
	public Integer setValue(final Integer value) {
		return this.value = value;
	}
}