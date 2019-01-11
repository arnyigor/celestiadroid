package com.arny.celestiatools.presenter.planets

import com.arny.celestiatools.data.models.Planet
import com.arny.celestiatools.presenter.base.BaseMvpPresenter
import com.arny.celestiatools.presenter.base.BaseMvpView

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