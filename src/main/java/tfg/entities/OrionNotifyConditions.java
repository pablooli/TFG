package tfg.entities;

/**
 * @author Pablo OLiver Remon
 * Nip: 595792
 */

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class OrionNotifyConditions {
	
	private String type;
	@SerializedName("condValues")
	private List<String> condValues;
	
	
	public OrionNotifyConditions(String type, List<String> condValues) {
		this.type = type;
		this.condValues = condValues;
	}
	


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(" type: ").append(type).append("\n");
		for (String element : condValues) {
			sb.append(element).append("\n");
		}

		return sb.toString();
	}




	public String getType() {
		return type;
	}




	public void setType(String type) {
		this.type = type;
	}




	public List<String> getCondValues() {
		return condValues;
	}




	public void setCondValues(List<String> condValues) {
		this.condValues = condValues;
	}
	
	
	
	
	
}
