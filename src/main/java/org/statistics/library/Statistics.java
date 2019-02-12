package org.statistics.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.statistics.library.objects.MutableInteger;

public class Statistics<K> {
	private final List<Statistic<K>> statistics;
	private final Map<K, MutableInteger> indexes;
	
	public Statistics(final int initialCapacity) {
		this.statistics = new ArrayList<>(initialCapacity);
		this.indexes = new HashMap<>(initialCapacity);
	}
	
	public Statistics() {
		this.statistics = new ArrayList<>();
		this.indexes = new HashMap<>();
	}
	
	public void put(final K key, final int value) {
		final int size = this.statistics.size();
		int index = 0, offset = size, middle;
		
		while (index < offset) {
			middle = (index + offset) / 2;
			
			if (this.statistics.get(middle).getValue() < value) {
				offset = middle;
			} else {
				index = middle + 1;
			}
		}
		final MutableInteger previousIndex = this.indexes.get(key);
		
		if (previousIndex != null) {
			final int unboxedValue = previousIndex.intValue();
			
			 if (unboxedValue == index) {
				this.statistics.get(index).setValue(value);
				return;
			} else if (unboxedValue > index) {
				for (int i = index; i < unboxedValue; ++i) {
					this.indexes.get(this.statistics.get(i).getKey()).add(1);
				}
			} else {
				if (unboxedValue == index - 1) {
					return;
				}
				for (int i = unboxedValue + 1; i < index; ++i) {
					this.indexes.get(this.statistics.get(i).getKey()).subtract(1);
				}
			}
			previousIndex.setValue(index);
			this.statistics.remove(unboxedValue);
		} else {
			for (int i = index; i < size; ++i) {
				this.indexes.get(this.statistics.get(i).getKey()).add(1);
			}
			this.indexes.put(key, new MutableInteger(index));
		}
		this.statistics.add(index, new Statistic<>(key, value));
	}
	
	public void putWithoutChecking(final K key, final int value) {
		this.statistics.add(new Statistic<>(key, value));
		this.indexes.put(key, new MutableInteger(this.statistics.size() - 1));
	}
	
	public void remove(final K key) {
		final MutableInteger index = this.indexes.remove(key);
		
		if (index != null) {
			this.statistics.remove(index.intValue());
		}
	}
	
	public int get(final K key) {
		final MutableInteger index = this.indexes.get(key);
		return index == null ? 0 : this.statistics.get(index.intValue()).getValue();
	}
	
	public Statistic<K> get(final int index) {
		return this.statistics.get(index);
	}
	
	public void change(final K key, final int amount) {
		final int value = this.get(key) + amount;
		
		if (value == 0) {
			this.remove(key);
		} else {
			this.put(key, value);
		}
	}
	
	public List<K> getKeys(boolean parallel) {
		return (parallel ? this.statistics.parallelStream() : this.statistics.stream()).map(Statistic::getKey).collect(Collectors.toList());
	}
	
	public List<Integer> values(boolean parallel) {
		return (parallel ? this.statistics.parallelStream() : this.statistics.stream()).map(Statistic::getValue).collect(Collectors.toList());
	}
	
	public List<Statistic<K>> getStatistics() {
		return this.statistics;
	}
	
	public List<Entry<K, Integer>> getEntries(boolean parallel) {
		return (parallel ? this.statistics.parallelStream() : this.statistics.stream()).map(entry -> (Entry<K, Integer>) entry).collect(Collectors.toList());
	}
	
	public int size() {
		return this.statistics.size();
	}
	
	public void clear() {
		this.statistics.clear();
	}
	
	@Override
	public String toString() {
		return this.statistics.toString();
	}
}