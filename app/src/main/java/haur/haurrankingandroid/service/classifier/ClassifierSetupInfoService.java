package haur.haurrankingandroid.service.classifier;

import java.util.List;

import haur.haurrankingandroid.domain.ClassifierSetup;
import haur.haurrankingandroid.domain.ClassifierSetupObject;
import haur.haurrankingandroid.service.file.FileService;

/**
 * Created by Jarno on 6.11.2018.
 */

public class ClassifierSetupInfoService {

	private static ClassifierSetupObject classifierSetupObject;

	public static ClassifierSetupObject getClassifierSetupObject() {
		if (classifierSetupObject == null) {
			classifierSetupObject = FileService.readClassifierSetupData();
		}
		return classifierSetupObject;
	}
}
