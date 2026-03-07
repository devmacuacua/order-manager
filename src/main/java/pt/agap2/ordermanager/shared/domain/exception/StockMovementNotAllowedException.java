package pt.agap2.ordermanager.shared.domain.exception;

public class StockMovementNotAllowedException extends DomainException {

	private static final long serialVersionUID = -4040161263963066452L;

	public StockMovementNotAllowedException(String message) {
		super("STOCK_MOVEMENT_NOT_ALLOWED", message);
	}
}