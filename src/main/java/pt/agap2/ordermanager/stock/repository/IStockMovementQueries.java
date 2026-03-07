package pt.agap2.ordermanager.stock.repository;

public interface IStockMovementQueries {
	public static final String GET_STOCK_MOVEMENTS_BY_ITEM_ID_ORDER = "SELECT s FROM StockMovementEntity s WHERE s.item.id = :itemId ORDER BY s.creationDate ASC";
	public static final String GET_STOCK_MOVEMENTS_ORDER_BY_CREATION_DATE_DESC = "SELECT s FROM StockMovementEntity s ORDER BY s.creationDate DESC";

}
