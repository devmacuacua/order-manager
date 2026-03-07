package pt.agap2.ordermanager.shared.domain.entity;

import pt.agap2.ordermanager.order.entity.OrderStatus;
import pt.agap2.ordermanager.shared.domain.exception.OrderAlreadyCompletedException;

public class Order {

	private OrderStatus status;

	public Order() {
		this.status = OrderStatus.CREATED;
	}

	public void complete() {

		if (status == OrderStatus.COMPLETED) {
			throw new OrderAlreadyCompletedException();
		}

		status = OrderStatus.COMPLETED;
	}

	public OrderStatus getStatus() {
		return status;
	}
}