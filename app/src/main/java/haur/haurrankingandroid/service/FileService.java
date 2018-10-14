package haur.haurrankingandroid.service;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import haur.haurrankingandroid.RankingAppContext;
import haur.haurrankingandroid.domain.Match;
import haur.haurrankingandroid.domain.MatchScore;
import haur.haurrankingandroid.domain.ScoreCard;
import haur.haurrankingandroid.domain.StageScore;

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
}
