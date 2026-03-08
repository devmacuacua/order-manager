package pt.agap2.ordermanager.shared.domain.exception;

public class ItemNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public ItemNotFoundException(Long id) {
		super("ITEM_NOT_FOUND", "Item", id);
	}
}