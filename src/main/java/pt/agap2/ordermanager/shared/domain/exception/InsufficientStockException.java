package pt.agap2.ordermanager.shared.domain.exception;

public class InsufficientStockException extends DomainException {

    private static final long serialVersionUID = 1L;

    public InsufficientStockException(String message) {
        super(
            "INSUFFICIENT_STOCK_AVAILABLE",
            message,
            409
        );
    }
}