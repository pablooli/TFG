package tfg.entities;

/**
 * @author Pablo OLiver Remon
 * Nip: 595792
 */

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class OrionSubscribeElement {
	
	@SerializedName("entities")
	private List<Entities> entities = newArrayList();
	@SerializedName("attributes")
	private List<String> atributes = newArrayList();
	private String reference = "";
	private String duration = "";
	@SerializedName("notifyConditions")
	private List<OrionNotifyConditions> notifyConditions = newArrayList();
	
	public OrionSubscribeElement(List<Entities> entities,
			List<String> atributes, String reference, String duration,
			List<OrionNotifyConditions> notifyConditions) {
		this.entities = entities;
		this.atributes = atributes;
		this.reference = reference;
		this.duration = duration;
		this.notifyConditions = notifyConditions;
	}
	
	public List<Entities> getEntities() {
		return entities;
	}
	public void setEntities(List<Entities> entities) {
		this.entities = entities;
	}
	public List<String> getAtributes() {
		return atributes;
	}
	public void setAtributes(List<String> atributes) {
		this.atributes = atributes;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public List<OrionNotifyConditions> getNotifyConditions() {
		return notifyConditions;
	}
	public void setNotifyConditions(List<OrionNotifyConditions> notifyConditions) {
		this.notifyConditions = notifyConditions;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Entities element : entities) {
			sb.append(element).append("\n");
		}
		for (String element : atributes) {
			sb.append(element).append("\n");
		}
		sb.append(" reference: ").append(reference).append("\n");
		sb.append(" duration: ").append(duration).append("\n");
		for (OrionNotifyConditions element : notifyConditions) {
			sb.append(element).append("\n");
		}

		return sb.toString();
	}
}
