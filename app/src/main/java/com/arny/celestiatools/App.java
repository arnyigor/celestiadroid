package com.arny.celestiatools;

import android.support.multidex.MultiDexApplication;
import com.arny.arnylib.database.DBProvider;
public class App extends MultiDexApplication {
	@Override
	public void onCreate() {
		super.onCreate();
		DBProvider.initDB(getApplicationContext(),"CelestaiDB",1);
	}
}
