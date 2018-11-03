package haur.haurrankingandroid.pdf;

import android.net.Uri;
import android.util.Log;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;

import haur.haurrankingandroid.domain.Ranking;

/**
 * Created by Jarno on 10.10.2018.
 */

public class PdfGenerator {

	public static Uri generatePdf(Ranking ranking) {
		/**
		 * Creating Document
		 */
		try {
			String fname ="rankingtest";
			String directoryPath = "/sdcard/HaurRanking/pdf";

			String fpath = directoryPath + fname + ".pdf";

			File file = new File(fpath);
			if (!file.exists()) {
				file.createNewFile(); }
			Log.d("Main", "\n***Created file");
			Document document = new Document();
			// Location to save
			PdfWriter.getInstance(document, new FileOutputStream(file.getAbsoluteFile()));
			Log.d("Main", "\n***Opened PdfWriter");
			// Open to write
			document.open();

			Log.d("Main", "\n***Document open. Setting page size");
			// Document Settings
			document.setPageSize(PageSize.A4);

			Log.d("Main", "\n*** Setting creation date");
			document.addCreationDate();
			document.addAuthor("Android School");
			document.addCreator("Pratik Butani");


			Log.d("Main", "\n***Adding paragraph");
			document.add(new Paragraph("test"));
			document.add(new Chunk("Test chunk"));
			Log.d("Main", "\n***Closing");
			document.close();

			Log.d("Main", "File done");

			return Uri.fromFile(file);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
