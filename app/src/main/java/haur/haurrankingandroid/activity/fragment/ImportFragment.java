package haur.haurrankingandroid.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import haur.haurrankingandroid.R;
import haur.haurrankingandroid.service.practiScore.PractiScoreDataService;

/**
 * Created by Jarno on 13.10.2018.
 */

public class ImportFragment extends Fragment {

	private final int CHOOSE_FILE_REQUEST_CODE = 1;

	private TextView matchNameTextView;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_import, container, false);
		matchNameTextView = view.findViewById(R.id.match_name);

		Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
		intent.setType("*/*");
		startActivityForResult(intent, CHOOSE_FILE_REQUEST_CODE);
		return view;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CHOOSE_FILE_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK && data != null) {
			if (data.getData() != null) {
				PractiScoreDataService.importFromFile(data.getData());
			}
		}
		else {
			getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
		}
	}

}
