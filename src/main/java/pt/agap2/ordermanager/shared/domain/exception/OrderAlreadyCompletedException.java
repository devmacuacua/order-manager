package pt.agap2.ordermanager.shared.domain.exception;

public class OrderAlreadyCompletedException extends DomainException {

    public OrderAlreadyCompletedException() {
        super("Order is already completed");
    }

}