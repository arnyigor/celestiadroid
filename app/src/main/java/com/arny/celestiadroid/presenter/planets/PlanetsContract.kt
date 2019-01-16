package com.arny.celestiadroid.presenter.planets

import com.arny.celestiadroid.data.models.Planet
import com.arny.celestiadroid.presenter.base.BaseMvpPresenter
import com.arny.celestiadroid.presenter.base.BaseMvpView

object PlanetsContract {
    interface View : BaseMvpView {
        fun showEmptyView(vis: Boolean)
        fun updateAdapter(list: ArrayList<Planet>)
        fun showList(vis: Boolean)

    }

    interface Presenter : BaseMvpPresenter<View> {
        fun loadPlanets()
    }
}