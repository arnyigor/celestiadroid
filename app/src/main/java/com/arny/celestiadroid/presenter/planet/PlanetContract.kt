package com.arny.celestiadroid.presenter.planet

import com.arny.celestiadroid.presenter.base.BaseMvpPresenter
import com.arny.celestiadroid.presenter.base.BaseMvpView

object PlanetContract {
    interface View : BaseMvpView {
        fun showPlanetName(name: String?)
        fun showPlanetRadius(radiusTxt: String)
        fun showPlanetMass(massTxt: String)
        fun result(id: Long)
        fun toastSuccess(msg: String)
        fun onBackPress()
        fun setResultOk(resOk: Boolean)
        fun setBtnText(text: String)
        fun showPlanetMassExp(exp: String)
    }

    interface Presenter : BaseMvpPresenter<View> {
        fun initState(id: Long?)
        fun addPlanet(name: String?, radius: String?, mass: String?, exponent: String)
        fun removeItem()
    }
}