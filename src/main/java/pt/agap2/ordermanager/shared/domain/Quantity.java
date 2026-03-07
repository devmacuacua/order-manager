package pt.agap2.ordermanager.shared.domain;

import java.util.Objects;

public final class Quantity {

	private final int value;

	private Quantity(int value) {
		this.value = value;
	}

	public static Quantity of(int value) {
		if (value < 0) {
			throw new IllegalArgumentException("Quantity cannot be negative");
		}
		return new Quantity(value);
	}

	public static Quantity positive(int value) {
		if (value <= 0) {
			throw new IllegalArgumentException("Quantity must be greater than zero");
		}
		return new Quantity(value);
	}

	public int value() {
		return value;
	}

	public Quantity add(Quantity other) {
		return new Quantity(this.value + other.value);
	}

	public Quantity subtract(Quantity other) {
		int result = this.value - other.value;
		if (result < 0) {
			throw new IllegalArgumentException("Resulting quantity cannot be negative");
		}
		return new Quantity(result);
	}

	public boolean isZero() {
		return value == 0;
	}

	public boolean isPositive() {
		return value > 0;
	}

	public boolean greaterThan(Quantity other) {
		return this.value > other.value;
	}

	public boolean greaterThanOrEqual(Quantity other) {
		return this.value >= other.value;
	}

	public boolean lessThan(Quantity other) {
		return this.value < other.value;
	}

	public boolean lessThanOrEqual(Quantity other) {
		return this.value <= other.value;
	}

	public Quantity min(Quantity other) {
		return this.value <= other.value ? this : other;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Quantity)) return false;
		Quantity quantity = (Quantity) o;
		return value == quantity.value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}