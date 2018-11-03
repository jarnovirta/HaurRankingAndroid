package haur.haurrankingandroid.service.file;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import haur.haurrankingandroid.RankingAppContext;
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
			Log.e(TAG, e.getStackTrace().toString());		}
	}
}
