package haur.haurrankingandroid.activity;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import java.util.Date;

import haur.haurrankingandroid.R;
import haur.haurrankingandroid.RankingAppContext;
import haur.haurrankingandroid.activity.fragment.BrowseDatabaseFragment;
import haur.haurrankingandroid.activity.fragment.ImportFragment;
import haur.haurrankingandroid.activity.fragment.RankingFragment;
import haur.haurrankingandroid.domain.ClassifierSetup;
import haur.haurrankingandroid.domain.ClassifierSetupObject;
import haur.haurrankingandroid.domain.Ranking;
import haur.haurrankingandroid.pdf.PdfGenerator;
import haur.haurrankingandroid.service.classifier.ClassifierSetupInfoService;
import haur.haurrankingandroid.service.file.FileService;
import haur.haurrankingandroid.service.ranking.RankingService;
import haur.haurrankingandroid.util.DataFormatUtils;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
	private final int PERMISSIONS_REQUEST_READ_AND_WRITE_SDK = 1;
	private final int EXPORT_RANKING_REQUEST_CODE = 1;
	private Ranking exportRanking = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();
		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
				new RankingFragment()).commit();
		navigationView.setCheckedItem(R.id.nav_ranking_list);

		if (isPermissionsGranted()) {
			FileService.createDirectoriesIfNotExist();
		}
		else getPermissions();
	}


	public boolean isPermissionsGranted() {
		if (Build.VERSION.SDK_INT < 23) return true;
		int writePermission = ContextCompat.checkSelfPermission(this,
				Manifest.permission.WRITE_EXTERNAL_STORAGE);
		int readPermission = ContextCompat.checkSelfPermission(this,
				Manifest.permission.READ_EXTERNAL_STORAGE);
		return writePermission == PackageManager.PERMISSION_GRANTED
				&& readPermission != PackageManager.PERMISSION_GRANTED;
	}
	private void getPermissions() {
		ActivityCompat.requestPermissions(this,
			new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.READ_EXTERNAL_STORAGE },
			PERMISSIONS_REQUEST_READ_AND_WRITE_SDK);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
	                                       @NonNull String permissions[], @NonNull int[] grantResults) {
		switch (requestCode) {
			case PERMISSIONS_REQUEST_READ_AND_WRITE_SDK:
				if (grantResults.length == 0
						|| grantResults[0] != PackageManager.PERMISSION_GRANTED) {
					finish();
				}
				FileService.createDirectoriesIfNotExist();
				RankingService.getRanking();
				break;
		}
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_exit) {
			finish();
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case R.id.nav_ranking_list:
				getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
						new RankingFragment()).commit();
				break;

			case R.id.nav_view_pdf:
				RankingService.getRanking().observe(this, new Observer<Ranking> () {
					@Override
					public void onChanged(@Nullable Ranking ranking) {
						if (ranking != null) {
							exportRanking = ranking;
							Intent intent = new Intent(Intent.ACTION_VIEW);
							intent.setDataAndType(PdfGenerator.generatePdf(ranking, null),"application/pdf");
							intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

							try {
								startActivityForResult(Intent.createChooser(intent, "Avaa PDF"),
										EXPORT_RANKING_REQUEST_CODE);
							}
							catch (ActivityNotFoundException e) {
								String message = "Laitteella ei ole PDF-ohjelmaa";
								Toast toast = Toast.makeText(RankingAppContext.getAppContext(),
										message,
										Toast.LENGTH_SHORT);
								toast.show();
							}
						}
						RankingService.getRanking().removeObserver(this);
					}
				});
				break;

			case R.id.nav_email:
				RankingService.getRanking().observe(this, new Observer<Ranking> () {
					@Override
					public void onChanged(@Nullable Ranking ranking) {
						if (ranking != null) {
							exportRanking = ranking;
							Intent intent = new Intent(Intent.ACTION_SEND);
							intent.setType(".pdf -> application/pdf");
							Uri uri = PdfGenerator.generatePdf(ranking, null);
							intent.putExtra(Intent.EXTRA_STREAM, uri);
							String subject = "Haur Ranking " + DataFormatUtils.dateToString(new Date());
							intent.putExtra(Intent.EXTRA_SUBJECT, subject);
							try {
								startActivityForResult(Intent.createChooser(intent, "Jaa ranking..."), EXPORT_RANKING_REQUEST_CODE);
							} catch (ActivityNotFoundException e) {
								String message = "Laitteella ei ole sähköpostiohjelmaa";
								Toast toast = Toast.makeText(RankingAppContext.getAppContext(),
										message,
										Toast.LENGTH_SHORT);
								toast.show();
							}
						}
						RankingService.getRanking().removeObserver(this);
					}
				});
				break;

			case R.id.nav_import_results:
				getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
						new ImportFragment()).commit();
				break;

			case R.id.nav_browse_db:
				getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
						new BrowseDatabaseFragment()).commit();
				break;
		}
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == EXPORT_RANKING_REQUEST_CODE) {
			RankingService.saveExportedRanking(exportRanking);
		}
	}
}
