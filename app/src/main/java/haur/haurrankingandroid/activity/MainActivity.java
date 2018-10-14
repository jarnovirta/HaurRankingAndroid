package haur.haurrankingandroid.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import haur.haurrankingandroid.R;
import haur.haurrankingandroid.activity.fragment.ClassifiersFragment;
import haur.haurrankingandroid.activity.fragment.CompetitorsFragment;
import haur.haurrankingandroid.activity.fragment.ImportFragment;
import haur.haurrankingandroid.activity.fragment.RankingFragment;
import haur.haurrankingandroid.pdf.PdfGenerator;
import haur.haurrankingandroid.service.file.FileService;
import haur.haurrankingandroid.service.ranking.RankingService;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
	private final int PERMISSIONS_REQUEST_READ_AND_WRITE_SDK = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		RankingService.saveTestData();
		FileService.createDirectoriesIfNotExist();

		setContentView(R.layout.activity_main);
		getPermissions();
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

	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
	                                       @NonNull String permissions[], @NonNull int[] grantResults) {
		switch (requestCode) {
			case PERMISSIONS_REQUEST_READ_AND_WRITE_SDK: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					PdfGenerator.generatePdf();
				} else {
					finish();
				}
			}
		}
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

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

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

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();
		if (id == R.id.nav_ranking_list) {
			getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
					new RankingFragment()).commit();
		}
		else if (id == R.id.nav_import_results) {
			getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
					new ImportFragment()).commit();
		} else if (id == R.id.nav_db_classifiers) {
			getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
					new ClassifiersFragment()).commit();
		} else if (id == R.id.nav_db_competitors) {
			getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
					new CompetitorsFragment()).commit();
		}
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
