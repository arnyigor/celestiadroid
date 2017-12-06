package com.arny.celestiatools.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import com.arny.arnylib.security.CryptoFiles;
import com.arny.celestiatools.R;
import com.arny.celestiatools.api.ApiConstants;
import com.arny.celestiatools.ui.fragments.AsteroidsFragment;
import com.arny.celestiatools.ui.fragments.OrbitCalcFragment;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends AppCompatActivity {

    private static final int MENU_ORBIT_CALC = 1;
    private static final int MENU_ASTEROIDS = 2;
	private static final String DRAWER_SELECTION = "drawer_selection";
	private Toolbar toolbar;
	private Drawer drawer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		initDrawer(savedInstanceState);

//		String nasaApiKey = ApiConstants.NASA_API_KEY;
//		Log.i(MainActivity.class.getSimpleName(), "onCreate: nasa api_key = " + nasaApiKey);
//		String hex = CryptoFiles.generateHexKey("com.arny.celestiatools");
//		String encryped = CryptoFiles.encryptString(hex, nasaApiKey);
//		Log.i(MainActivity.class.getSimpleName(), "onCreate: hex = " + hex);
//		Log.i(MainActivity.class.getSimpleName(), "onCreate: nasa encrypted = " + encryped);
//		Log.i(MainActivity.class.getSimpleName(), "onCreate: nasa decrypted = " + CryptoFiles.decryptString(hex,encryped));
	}

	private void initDrawer(Bundle savedInstanceState) {
		drawer = new DrawerBuilder()
				.withActivity(this)
				.withOnDrawerListener(new Drawer.OnDrawerListener() {
					@Override
					public void onDrawerOpened(View drawerView) {

					}

					@Override
					public void onDrawerClosed(View drawerView) {

					}

					@Override
					public void onDrawerSlide(View drawerView, float slideOffset) {

					}
				})
				.withRootView(R.id.drawer_container)
				.withToolbar(toolbar)
				.withActionBarDrawerToggle(true)
				.withActionBarDrawerToggleAnimated(true)
				.addDrawerItems(
                        new PrimaryDrawerItem()
                                .withIdentifier(MENU_ORBIT_CALC)
                                .withName(R.string.calc_orbit_fragment)
                                .withIcon(GoogleMaterial.Icon.gmd_3d_rotation),
						new PrimaryDrawerItem()
								.withIdentifier(MENU_ASTEROIDS)
								.withName(R.string.fragment_asteroids)
								.withIcon(GoogleMaterial.Icon.gmd_flight_takeoff))
				.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
					@Override
					public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
						selectItem((int) drawerItem.getIdentifier());
						return true;
					}
				})
				.build();
		if (savedInstanceState == null) {
			selectItem(MENU_ORBIT_CALC);
		} else {
			try {
				drawer.setSelection(Long.parseLong(savedInstanceState.getString(DRAWER_SELECTION)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void selectItem(int position) {
		Fragment fragment = null;
		switch (position) {
            case MENU_ORBIT_CALC:
                fragment = new OrbitCalcFragment();
                toolbar.setTitle(getString(R.string.calc_orbit_fragment));
                break;
			case MENU_ASTEROIDS:
				fragment = new AsteroidsFragment();
				toolbar.setTitle(getString(R.string.fragment_asteroids));
				break;

		}
		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
			drawer.closeDrawer();
		}
	}
}
