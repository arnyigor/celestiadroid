package  com.arny.celestiadroid.presenter.base

import android.arch.lifecycle.Lifecycle


interface BaseMvpPresenter<V : BaseMvpView> {
    fun attachView(mvpView: V)
    fun detachView()
    fun getView(): V?
    fun attachLifecycle(lifecycle: Lifecycle)
    fun detachLifecycle(lifecycle: Lifecycle)
    fun isViewAttached(): Boolean
    fun onPresenterCreated()
    fun onPresenterDestroy()
    fun onPresenterRestored()
}