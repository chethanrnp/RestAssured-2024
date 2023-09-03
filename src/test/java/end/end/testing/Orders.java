package end.end.testing;

import java.util.List;

public class Orders {

	private List<OrderDetails> orders;

	public Orders() {

	}

	public Orders(List<OrderDetails> orders) {
		this.orders = orders;
	}

	public List<OrderDetails> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDetails> orders) {
		this.orders = orders;
	}

}
