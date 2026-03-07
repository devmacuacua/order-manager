package pt.agap2.ordermanager.order.repository;

public interface IOrderQueries {
	public static final String GET_ORDERS_BY_ITEM_AND_QUANTITY = "SELECT o FROM OrderEntity o "
			+ "WHERE o.item.id = :itemId AND o.fulfilledQuantity < o.quantity " + "ORDER BY o.creationDate ASC";
	public static final String GET_ORDER_ORDER_BY_O_CREATION_DATE_DESC = "SELECT o FROM OrderEntity o ORDER BY o.creationDate DESC";

}
