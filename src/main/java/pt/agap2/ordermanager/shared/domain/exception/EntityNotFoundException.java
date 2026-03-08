package pt.agap2.ordermanager.shared.domain.exception;

import javax.ws.rs.core.Response;

public abstract class EntityNotFoundException extends DomainException {

	private static final long serialVersionUID = -6483861842043944537L;

	protected EntityNotFoundException(String code, String entity, Object id) {
		super(code, entity + " with id " + id + " was not found", Response.Status.NOT_FOUND.getStatusCode());
	}
}