package pt.agap2.ordermanager.shared.domain.exception;

public class InsufficientStockException extends DomainException {

    public InsufficientStockException() {
        super("Insufficient stock available");
    }

}