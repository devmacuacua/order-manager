package pt.agap2.ordermanager.shared.domain.exception;

public class InvalidRequestException extends DomainException {

    private static final long serialVersionUID = 1L;

    public InvalidRequestException(String message) {
        super("INVALID_REQUEST", message, 400);
    }
}