package haur.haurrankingandroid.service.file;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

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
			ContentResolver cr = RankingAppContext.getAppContext().getContentResolver();
			InputStream is = cr.openInputStream(uri);

			OutputStream outputStream;

			String externalStorageFolder = Environment.getExternalStorageDirectory().getAbsolutePath();

			outputStream = new FileOutputStream(new File(externalStorageFolder + "/HaurRanking/matchImport.temp"));
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = is.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			outputStream.close();
			is.close();
			File file = new File(externalStorageFolder + "/HaurRanking/matchImport.temp");

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
			} 		}
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

	public static ClassifierSetupObject readClassifierSetupData() {
		try {
			InputStream is = RankingAppContext.getAppContext().getAssets().open("classifier_stage_setup.json");
			BufferedReader r = new BufferedReader(new InputStreamReader(is));
			StringBuilder json = new StringBuilder();
			for (String line; (line = r.readLine()) != null; ) {
				json.append(line).append('\n');
			}
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
