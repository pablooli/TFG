package tfg.entities;

/**
 * Autor : Pablo Oliver Remon
 * Nip: 595792
 */

	public class OrionQueryElement {

		private String type;

		private boolean isPattern;
		private String id;

		/**
		 * @return the type
		 */
		public String getType() {
			return type;
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

			sb.append(" Type: ").append(type).append("\n");
			sb.append(" Id: ").append(id).append("\n");
			sb.append(" IsPattern: ").append(isPattern).append("\n");

			return sb.toString();
		}
}