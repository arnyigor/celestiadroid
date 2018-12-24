package com.arny.celestiatools.presenter.asteroids;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arny.celestiatools.R;
public class AsteroidsFragment extends Fragment {

	private Context context;
	private RecyclerView mRecyclerView;

	public AsteroidsFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_asteroids, container, false);
		this.context = container.getContext();
		initUI(rootView);
		return rootView;
	}

	private void initUI(View rootView) {
		mRecyclerView = rootView.findViewById(R.id.rv_asteroids_list);
	}

}
