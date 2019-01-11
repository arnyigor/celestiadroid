package com.arny.celestiatools.presenter.planets

import com.arny.celestiatools.data.source.MainRepositoryImpl
import com.arny.celestiatools.data.utils.launchAsync
import com.arny.celestiatools.presenter.base.BaseMvpPresenterImpl

class PlanetsPresenter : BaseMvpPresenterImpl<PlanetsContract.View>(), PlanetsContract.Presenter {
    private val repository = MainRepositoryImpl.instance

    override fun loadPlanets() {
        launchAsync({
            repository.loadPlanets()
        },{
            if (it.isNotEmpty()) {
                mView?.updateAdapter(it)
                mView?.showList(true)
                mView?.showEmptyView(false)
            } else {
                mView?.showList(false)
                mView?.showEmptyView(true)
            }
        },{
            it.printStackTrace()
            mView?.showEmptyView(true)
            mView?.toastError(it.message)
        })
    }
}