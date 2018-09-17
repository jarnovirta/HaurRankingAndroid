package haur.haurrankingandroid;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
	private final int PERMISSIONS_REQUEST_READ_AND_WRITE_SDK = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("MainActivity", "*** APP STARTED");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d("MainActivity", "*** REQUESTING PERMISSIONS");
		ActivityCompat.requestPermissions(this,
				new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
						Manifest.permission.READ_EXTERNAL_STORAGE},
				PERMISSIONS_REQUEST_READ_AND_WRITE_SDK);

	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
	                                       @NonNull String permissions[], @NonNull int[] grantResults) {
		switch (requestCode) {
			case PERMISSIONS_REQUEST_READ_AND_WRITE_SDK: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					Log.d("MainActivity", "App permissions granted");
					generatePdf();
				} else {
					Log.d("MainActivity", "App permissions not granted. Exiting.");
					finish();
				}
			}
		}
	}

	private void generatePdf() {
		/**
		 * Creating Document
		 */
		try {
		String fname ="rankingtest";
		String fpath = "/sdcard/" + fname + ".pdf";
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


		}
		catch (Exception e) {
			e.printStackTrace();
		}


	}
}
