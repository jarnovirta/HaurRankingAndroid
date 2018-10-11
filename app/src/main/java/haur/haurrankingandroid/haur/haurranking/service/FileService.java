package haur.haurrankingandroid.haur.haurranking.service;

import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import haur.haurrankingandroid.RankingAppContext;
import haur.haurrankingandroid.haur.haurranking.domain.Match;

/**
 * Created by Jarno on 11.10.2018.
 */

public class FileService {
	private static String practiScoreExportFilePath;

	private static String matchName;

	private static final String TAG = FileService.class.getSimpleName();

	public static void setPractiScoreExportFilePath(Uri uri) {
		practiScoreExportFilePath = FileUtil.getPath(RankingAppContext.getAppContext(), uri);

		File file = new File(practiScoreExportFilePath);
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonString = PractiScoreFileParser.readPractiScoreExportFileData(file, PractiScoreFileType.MATCH_DEF);
			if (jsonString != null) {
				Match match = objectMapper.readValue(jsonString, new TypeReference<Match>() {
				});
				matchName = match.getName();
			}

		}
		catch (IOException e) {
			Log.e(TAG, "Error setting PractiScore export file path", e);

		}
	}
	public static String getPractiScoreExportFileMatchname() {
		return matchName;
	}
}
