package pt.agap2.ordermanager;

import pt.agap2.ordermanager.item.repository.IItemRepository;
import pt.agap2.ordermanager.item.repository.ItemRepository;
import pt.agap2.ordermanager.item.service.IItemService;
import pt.agap2.ordermanager.item.service.ItemService;
import pt.agap2.ordermanager.order.repository.IOrderRepository;
import pt.agap2.ordermanager.order.repository.IOrderStockMovementRepository;
import pt.agap2.ordermanager.order.repository.OrderRepository;
import pt.agap2.ordermanager.order.repository.OrderStockMovementRepository;
import pt.agap2.ordermanager.order.service.IOrderFulfillmentService;
import pt.agap2.ordermanager.order.service.IOrderService;
import pt.agap2.ordermanager.order.service.OrderFulfillmentService;
import pt.agap2.ordermanager.order.service.OrderService;
import pt.agap2.ordermanager.stock.repository.IStockMovementRepository;
import pt.agap2.ordermanager.stock.repository.StockMovementRepository;
import pt.agap2.ordermanager.stock.service.IStockMovementService;
import pt.agap2.ordermanager.stock.service.StockMovementService;
import pt.agap2.ordermanager.user.repository.IUserRepository;
import pt.agap2.ordermanager.user.repository.UserRepository;
import pt.agap2.ordermanager.user.service.IUserService;
import pt.agap2.ordermanager.user.service.UserService;

public final class ApplicationContext {

	private static final IUserRepository USER_REPOSITORY = new UserRepository();
	private static final IItemRepository ITEM_REPOSITORY = new ItemRepository();
	private static final IStockMovementRepository STOCK_MOVEMENT_REPOSITORY = new StockMovementRepository();
	private static final IOrderRepository ORDER_REPOSITORY = new OrderRepository();
	private static final IOrderStockMovementRepository ORDER_STOCK_MOVEMENT_REPOSITORY = new OrderStockMovementRepository();

	private static final IOrderFulfillmentService ORDER_FULFILLMENT_SERVICE = new OrderFulfillmentService(
			ORDER_REPOSITORY, STOCK_MOVEMENT_REPOSITORY, ORDER_STOCK_MOVEMENT_REPOSITORY);

	private static final IUserService USER_SERVICE = new UserService(USER_REPOSITORY);

	private static final IItemService ITEM_SERVICE = new ItemService(ITEM_REPOSITORY);

	private static final IOrderService ORDER_SERVICE = new OrderService(ORDER_REPOSITORY, ORDER_FULFILLMENT_SERVICE,
			ORDER_STOCK_MOVEMENT_REPOSITORY);

	private static final IStockMovementService STOCK_MOVEMENT_SERVICE = new StockMovementService(
			STOCK_MOVEMENT_REPOSITORY, ORDER_FULFILLMENT_SERVICE, ORDER_STOCK_MOVEMENT_REPOSITORY);

	private ApplicationContext() {
	}

	public static IUserService userService() {
		return USER_SERVICE;
	}

	public static IItemService itemService() {
		return ITEM_SERVICE;
	}

	public static IOrderService orderService() {
		return ORDER_SERVICE;
	}

	public static IStockMovementService stockMovementService() {
		return STOCK_MOVEMENT_SERVICE;
	}
}