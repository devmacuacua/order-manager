package pt.agap2.ordermanager.shared.domain.valueobject;

import pt.agap2.ordermanager.shared.domain.exception.InvalidQuantityException;

public final class Quantity {

	private final int value;

	private Quantity(int value) {
		if (value < 0) {
			throw new InvalidQuantityException(value);
		}
		this.value = value;
	}

	public static Quantity of(int value) {
		return new Quantity(value);
	}

	public static Quantity positive(int value) {
		if (value <= 0) {
			throw new InvalidQuantityException(value);
		}
		return new Quantity(value);
	}

	public int value() {
		return value;
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

	public Quantity add(Quantity other) {
		return new Quantity(this.value + other.value);
	}

	public Quantity subtract(Quantity other) {
		int result = this.value - other.value;

		if (result < 0) {
			throw new InvalidQuantityException(result);
		}

		return new Quantity(result);
	}

	public Quantity min(Quantity other) {
		return this.value <= other.value ? this : other;
	}
}