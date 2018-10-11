package haur.haurrankingandroid;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import haur.haurrankingandroid.haur.haurranking.service.FileService;
import haur.haurrankingandroid.pdf.PdfGenerator;

public class MainActivity extends AppCompatActivity {
	private final int PERMISSIONS_REQUEST_READ_AND_WRITE_SDK = 1;
	private TextView matchNameTextView;
	private Button selectFileButton;

	private final int CHOOSE_FILE_REQUEST_CODE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("MainActivity", "*** APP STARTED");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d("MainActivity", "*** REQUESTING PERMISSIONS");
		getPermissions();
		matchNameTextView = findViewById(R.id.match_name);
		setButtonClickListeners();
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
					PdfGenerator.generatePdf();
				} else {
					Log.d("MainActivity", "App permissions not granted. Exiting.");
					finish();
				}
			}
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CHOOSE_FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
			if (data.getData() != null) {
				Log.d("MainActivity", "Got data from Activity. Setting file path");
				FileService.setPractiScoreExportFilePath(data.getData());
				Log.d("MainActivity", "Setting text field. ");
				matchNameTextView.setText(FileService.getPractiScoreExportFileMatchname());
			}
		}
	}
	private void setButtonClickListeners() {
		selectFileButton = findViewById(R.id.select_file_button);
		selectFileButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
				intent.setType("*/*");
				startActivityForResult(intent, CHOOSE_FILE_REQUEST_CODE);
			}
		});
	}
	private void getPermissions() {
		if (Build.VERSION.SDK_INT >= 23 &&
				ContextCompat.checkSelfPermission(this,
						Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
				ContextCompat.checkSelfPermission(this,
						Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

			ActivityCompat.requestPermissions(this,
					new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
							Manifest.permission.READ_EXTERNAL_STORAGE},
					PERMISSIONS_REQUEST_READ_AND_WRITE_SDK);
		}
	}
}
