package pt.agap2.ordermanager.shared.domain.exception;

public class UserNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

	public UserNotFoundException(Long id) {
        super("USER_NOT_FOUND", "User", id);
    }
}