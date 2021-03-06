package com.lst.wanandroid.base.presenter;

import com.lst.wanandroid.base.AbstractView;
import com.lst.wanandroid.core.DataManager;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter<T extends AbstractView> implements AbstractPresenter<T> {
    protected T mView;
    private CompositeDisposable compositeDisposable;
    private DataManager dataManager;

    public BasePresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    protected void addSubscribe(Disposable disposable) {
        if (compositeDisposable != null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    @Override
    public void addRxBindingSubscribe(Disposable disposable) {
        addSubscribe(disposable);
    }

    @Override
    public boolean getNightModeState() {
        return dataManager.getNightModeState();
    }

    @Override
    public void setLoginState(boolean loginState) {
        dataManager.setLoginStatus(loginState);
    }

    @Override
    public boolean getLoginStatus() {
        return dataManager.getLoginStatus();
    }

    @Override
    public String getLoginAccount() {
        return dataManager.getLoginAccount();
    }

    @Override
    public int getCurrentPage() {
        return dataManager.getCurrentPage();
    }
}
