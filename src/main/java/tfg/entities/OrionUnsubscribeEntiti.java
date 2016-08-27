package tfg.entities;

/**
 * @author Pablo OLiver Remon
 * Nip: 595792
 */

public class OrionUnsubscribeEntiti {
	private String subscriptionId="";

	public OrionUnsubscribeEntiti(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(" subscriptionId: ").append(subscriptionId).append("\n");


		return sb.toString();
	}
	
	
}
