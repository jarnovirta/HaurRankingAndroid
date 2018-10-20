package haur.haurrankingandroid.domain;

/**
 * Created by Jarno on 13.10.2018.
 */

public enum Division {
		PRODUCTION, PRODUCTION_OPTICS, STANDARD, OPEN, CLASSIC, REVOLVER;

		@Override
		public String toString() {
			if (this == Division.PRODUCTION_OPTICS)
				return "Production optics";
			return Character.toUpperCase(this.name().charAt(0)) + this.name().substring(1).toLowerCase();
		}
		public static Division fromString(String divisionString) {
			for (Division division : Division.values()) {
				if (division.toString().equals(divisionString))
					return division;
			}
			return null;
		}
}

