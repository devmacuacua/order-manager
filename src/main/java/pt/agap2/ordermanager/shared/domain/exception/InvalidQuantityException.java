package pt.agap2.ordermanager.shared.domain.exception;

public class InvalidQuantityException extends DomainException {

    public InvalidQuantityException(int value) {
        super("Invalid quantity: " + value);
    }

}