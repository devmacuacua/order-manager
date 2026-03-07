package pt.agap2.ordermanager.shared.infrastructure;

public interface IEmailService {
	public void sendOrderCompletedEmail(String to, Long orderId);
}