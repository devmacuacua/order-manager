package pt.agap2.ordermanager.shared.domain.exception;

public class InvalidQuantityException extends DomainException {

    private static final long serialVersionUID = 1L;

    public InvalidQuantityException(int value) {
        super(
            "INVALID_QUANTITY",
            "Invalid quantity: " + value,
            400
        );
    }
}