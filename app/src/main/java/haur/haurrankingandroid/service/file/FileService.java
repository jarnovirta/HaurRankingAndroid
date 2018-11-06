package haur.haurrankingandroid.service.file;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import haur.haurrankingandroid.RankingAppContext;
import haur.haurrankingandroid.domain.ClassifierSetupObject;
import haur.haurrankingandroid.domain.Match;
import haur.haurrankingandroid.domain.MatchScore;

/**
 * Created by Jarno on 11.10.2018.
 */

public class FileService {

	private static final String TAG = FileService.class.getSimpleName();

	private static final String APP_DATA_DIRECTORY = Environment.getExternalStorageDirectory().getAbsolutePath() + "/HaurRanking/data";

	public static Object[] readPractiScoreExportFile(Uri uri) {
		Match matchDef = null;
		MatchScore matchScoreData = null;

		try {
			String practiScoreExportFilePath = FileUtil.getPath(RankingAppContext.getAppContext(), uri);
			File file = new File(practiScoreExportFilePath);

			ObjectMapper objectMapper = new ObjectMapper();
			String jsonString = PractiScoreFileParser.readPractiScoreExportFileData(file, PractiScoreFileType.MATCH_DEF);
			if (jsonString != null) {
				matchDef = objectMapper.readValue(jsonString, new TypeReference<Match>() {
				});
			}
			jsonString = PractiScoreFileParser.readPractiScoreExportFileData(file, PractiScoreFileType.MATCH_SCORES);
			if (jsonString != null && jsonString.length() > 0) {
				matchScoreData = objectMapper.readValue(jsonString, new TypeReference<MatchScore>() {
				});
			}
		}
		catch (IOException e) {
			Log.e(TAG, "Error reading PractiScore export file!", e);

		}
		return new Object[] { matchDef, matchScoreData};
	}
	public static void createDirectoriesIfNotExist() {
		File directory = new File(APP_DATA_DIRECTORY);
		if (!directory.exists()) {
			directory.mkdirs();
		}
	}

	public static void savePdf() {
		try {
			String fname = "rankingtest";
			String directoryPath = "/sdcard/HaurRanking/pdf";

			String fpath = directoryPath + fname + ".pdf";

			File file = new File(fpath);
			if (!file.exists()) {
				file.createNewFile();
			}
			Log.d("Main", "\n***Created file");

		}
		catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);		}
	}

	public static ClassifierSetupObject readClassifierSetupData() {
		try {
			InputStream is = RankingAppContext.getAppContext().getAssets().open("classifier_stage_setup.json");
			BufferedReader r = new BufferedReader(new InputStreamReader(is));
			StringBuilder json = new StringBuilder();
			for (String line; (line = r.readLine()) != null; ) {
				json.append(line).append('\n');
			}
			Log.i("TEST", "JSON: " + json);
			r.close();
			is.close();

			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(json.toString(), new TypeReference<ClassifierSetupObject>() {
			});

		}
		catch(IOException e) {
			Log.e(TAG, "Error reading classifier_stage_setup.json", e);
		}
		return null;
	}
}
