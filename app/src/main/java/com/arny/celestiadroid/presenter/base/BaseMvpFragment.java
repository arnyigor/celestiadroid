package com.arny.celestiadroid.presenter.base;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.arny.celestiadroid.data.utils.ToastMaker;


public abstract class BaseMvpFragment<V extends BaseMvpView, P extends BaseMvpPresenter<V>>
        extends Fragment implements BaseMvpView {

    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    protected P mPresenter;

    @SuppressWarnings("unchecked")
    @CallSuper
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BaseViewModel<V, P> viewModel = ViewModelProviders.of(this).get(BaseViewModel.class);
        if (viewModel.getPresenter() == null) {
            viewModel.setPresenter(initPresenter());
            mPresenter = viewModel.getPresenter();
            mPresenter.attachLifecycle(getLifecycle());
            mPresenter.attachView((V) this);
            mPresenter.onPresenterCreated();
        } else {
            mPresenter = viewModel.getPresenter();
            mPresenter.attachLifecycle(getLifecycle());
            mPresenter.attachView((V) this);
            mPresenter.onPresenterRestored();
        }
    }

    @NonNull
    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

    @Override
    public void toastError(@Nullable String error) {
        ToastMaker.toastError(getContext(), error);
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachLifecycle(getLifecycle());
        mPresenter.detachView();
    }

    protected abstract P initPresenter();
}