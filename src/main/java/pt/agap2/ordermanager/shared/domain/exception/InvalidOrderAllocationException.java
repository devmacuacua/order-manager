package pt.agap2.ordermanager.shared.domain.exception;

public class InvalidOrderAllocationException extends DomainException {

	private static final long serialVersionUID = 1L;

	public InvalidOrderAllocationException(int allocation, int remaining) {
		super("INVALID_ORDER_ALLOCATION",
				"Allocation quantity " + allocation + " exceeds remaining quantity " + remaining, 400);
	}
}