package com.arny.celestiadroid.presenter.planet


import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.arny.celestiadroid.R
import com.arny.celestiadroid.data.utils.ToastMaker
import com.arny.celestiadroid.data.utils.getExtra
import com.arny.celestiadroid.presenter.base.BaseMvpActivity
import kotlinx.android.synthetic.main.planet_layout.*

class PlanetActivity : BaseMvpActivity<PlanetContract.View, PlanetPresenter>(), PlanetContract.View {
    override fun initPresenter(): PlanetPresenter {
        return PlanetPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.planet_layout)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Планета"
        val id = intent.getExtra<Long>("id")
        mPresenter.initState(id)
        btn_add.setOnClickListener {
            val name = tiedt_planet_name.text.toString()
            val radius = tiedt_planet_radius.text.toString()
            val mass = tiedt_planet_mass.text.toString()
            val exponent = edt_planet_mass_exp.text.toString()
            mPresenter?.addPlanet(name, radius, mass,exponent)
        }
    }

    override fun onBackPress() {
        onBackPressed()
    }

    override fun setResultOk(resOk: Boolean) {
        if (resOk) {
            setResult(Activity.RESULT_OK, intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.planet_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.action_menu_delete -> {
                mPresenter.removeItem()
                return true
            }
        }
        return true
    }

    override fun showPlanetMassExp(exp: String) {
        edt_planet_mass_exp.setText(exp)
    }

    override fun showPlanetName(name: String?) {
        tiedt_planet_name.setText(name)
    }

    override fun showPlanetRadius(radiusTxt: String) {
        tiedt_planet_radius.setText(radiusTxt)
    }

    override fun showPlanetMass(massTxt: String) {
        tiedt_planet_mass.setText(massTxt)
    }

    override fun result(id: Long) {
        val intent = intent
        intent.putExtra("id", id)
        setResult(Activity.RESULT_OK, intent)
        onBackPressed()
    }

    override fun toastSuccess(msg: String) {
        ToastMaker.toastSuccess(this, msg)
    }

    override fun setBtnText(text: String) {
        btn_add.text = text
    }
}
