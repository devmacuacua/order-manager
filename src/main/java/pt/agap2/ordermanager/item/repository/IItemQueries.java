package pt.agap2.ordermanager.item.repository;

public interface IItemQueries {
	public static final String GET_ITEMS_ORDER_BY_I_ID_DESC = "SELECT i FROM ItemEntity i ORDER BY i.id DESC";
}
