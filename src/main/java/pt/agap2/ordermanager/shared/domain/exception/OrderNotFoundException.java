package pt.agap2.ordermanager.shared.domain.exception;

public class OrderNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public OrderNotFoundException(Long id) {
    	 super("ORDER_NOT_FOUND", "Order", id);
    }
}