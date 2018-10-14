package haur.haurrankingandroid.service.file;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import haur.haurrankingandroid.domain.Match;
import haur.haurrankingandroid.domain.MatchScore;

/**
 * Created by Jarno on 11.10.2018.
 */

public class PractiScoreFileParser {
	private static final String TAG = PractiScoreFileParser.class.getSimpleName();
	public static Match readMatchDefDataFromExportFile(File file) {
		try {
			String jason = readPractiScoreExportFileData(file, PractiScoreFileType.MATCH_DEF);
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(jason, new TypeReference<Match>() {
			});
		}
		catch (Exception e) {
			Log.d(TAG, e.getStackTrace().toString());
			return null;
		}
	}
	public static MatchScore readMatchResultDataFromExportFile(File file) {
		try {
			String jason = readPractiScoreExportFileData(file, PractiScoreFileType.MATCH_SCORES);
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(jason, new TypeReference<MatchScore>() {
			});
		}
		catch (Exception e) {
			Log.d(TAG, e.getStackTrace().toString());
			return null;
		}
	}
	public static String readPractiScoreExportFileData(File file, PractiScoreFileType fileType) {
		String fileContentString;

		try {
			ZipFile zipFile = new ZipFile(file);
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				if (entry.getName().equals(fileType.fileName)) {
					InputStream inputStream = zipFile.getInputStream(entry);
					fileContentString = IOUtils.toString(inputStream, "utf-8");
					inputStream.close();
					return fileContentString;
				}
			}
			zipFile.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
