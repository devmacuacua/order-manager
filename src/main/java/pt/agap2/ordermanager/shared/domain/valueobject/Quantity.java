package pt.agap2.ordermanager.shared.domain.valueobject;

import pt.agap2.ordermanager.shared.domain.exception.InvalidQuantityException;
public class Quantity {

    private final int value;

    public Quantity(int value) {

        if (value <= 0) {
            throw new InvalidQuantityException(value);
        }

        this.value = value;
    }

    public int getValue() {
        return value;
    }

}