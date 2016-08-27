package tfg.entities;

/**
 * @author Pablo OLiver Remon
 * Nip: 595792
 */

public class Entities {

	private String type;
	private boolean isPattern;
	private String id;
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	public Entities(String type, boolean isPattern, String id) {
		this.type = type;
		this.isPattern = isPattern;
		this.id = id;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the isPattern
	 */
	public boolean isPattern() {
		return isPattern;
	}

	/**
	 * @param isPattern
	 *            the isPattern to set
	 */
	public void setPattern(boolean isPattern) {
		this.isPattern = isPattern;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(" type: ").append(type).append("\n");
		sb.append(" isPattern: ").append(isPattern).append("\n");
		sb.append(" id: ").append(id).append("\n");

		return sb.toString();
	}
}
