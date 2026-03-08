package pt.agap2.ordermanager.shared.domain.exception;

import javax.ws.rs.core.Response;

public class OrderAlreadyCompletedException extends DomainException {

	private static final long serialVersionUID = -2173486140440927492L;

	public OrderAlreadyCompletedException(String message) {
		super("ORDER_IS_ALREADY_COMPLETED", message, Response.Status.CONFLICT.getStatusCode());
	}
}