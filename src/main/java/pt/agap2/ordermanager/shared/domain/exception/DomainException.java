package pt.agap2.ordermanager.shared.domain.exception;

public abstract class DomainException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String code;
    private final int status;

    protected DomainException(String code, String message, int status) {
        super(message);
        this.code = code;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }
}