package haur.haurrankingandroid.domain;

public class ClassifierSetup {
	private Classifier classifier;
	private String classifierName;
	private int paperTargets;
	private int steelTargets;
	private int noshootTargets;
	private int minRounds;

	public int getPaperTargets() {
		return paperTargets;
	}

	public void setPaperTargets(int paperTargets) {
		this.paperTargets = paperTargets;
	}

	public int getSteelTargets() {
		return steelTargets;
	}

	public void setSteelTargets(int steelTargets) {
		this.steelTargets = steelTargets;
	}

	public int getNoshootTargets() {
		return noshootTargets;
	}

	public void setNoshootTargets(int noshootTargets) {
		this.noshootTargets = noshootTargets;
	}

	public int getMinRounds() {
		return minRounds;
	}

	public void setMinRounds(int minRounds) {
		this.minRounds = minRounds;
	}

	public void setClassifierName(String classifierName) {
		this.classifier = Classifier.fromPractiScoreCode(classifierName);
	}

	public Classifier getClassifier() {
		return classifier;
	}

	public void setClassifier(Classifier classifier) {
		this.classifier = classifier;
	}
}
