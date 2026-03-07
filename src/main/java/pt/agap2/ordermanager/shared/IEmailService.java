package pt.agap2.ordermanager.shared;

public interface IEmailService {
	public void sendOrderCompletedEmail(String to, Long orderId);
}