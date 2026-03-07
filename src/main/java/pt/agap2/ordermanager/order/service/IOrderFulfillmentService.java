package pt.agap2.ordermanager.order.service;

import javax.persistence.EntityManager;

import pt.agap2.ordermanager.order.entity.OrderEntity;
import pt.agap2.ordermanager.stock.entity.StockMovementEntity;

public interface IOrderFulfillmentService {

	void fulfillOrder(EntityManager em, OrderEntity order);

	void fulfillPendingOrdersWithMovement(EntityManager em, StockMovementEntity movement);
}