package haur.haurrankingandroid.domain;

/**
 * Created by Jarno on 13.10.2018.
 */

public enum Division {
		PRODUCTION, PRODUCTION_OPTICS, STANDARD, OPEN, CLASSIC, REVOLVER;

		public static Division getDivisionByWinMSSTypeId(int winMSSDivisionTypeId) {
			if (winMSSDivisionTypeId == 1)
				return Division.OPEN;
			if (winMSSDivisionTypeId == 2)
				return Division.STANDARD;
			if (winMSSDivisionTypeId == 4)
				return Division.PRODUCTION;
			if (winMSSDivisionTypeId == 5)
				return Division.REVOLVER;
			if (winMSSDivisionTypeId == 18)
				return Division.CLASSIC;
			if (winMSSDivisionTypeId == 24)
				return Division.PRODUCTION_OPTICS;
			else
				return null;
		}

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

