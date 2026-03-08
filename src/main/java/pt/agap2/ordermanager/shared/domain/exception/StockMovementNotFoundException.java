package pt.agap2.ordermanager.shared.domain.exception;

public class StockMovementNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = -178788452806317003L;

	public StockMovementNotFoundException(Long id) {
        super("STOCK_MOVEMENT_NOT_FOUND", "Stock movement", id);
    }
}