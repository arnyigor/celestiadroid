package com.arny.celestiadroid.presenter.planets

import com.arny.celestiadroid.data.source.MainRepositoryImpl
import com.arny.celestiadroid.data.utils.launchAsync
import com.arny.celestiadroid.presenter.base.BaseMvpPresenterImpl

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