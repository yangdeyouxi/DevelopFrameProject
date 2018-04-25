package com.develop.frame.bases;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by sam on 2018/3/23.
 */

public class BasePresenter<V extends IBaseView> implements IBasePresenter {

    protected V mView;

    private CompositeDisposable mCompositeDisposable;

    public BasePresenter(){
        mView = null;
        mCompositeDisposable=null;
    }
    public BasePresenter(V view){
        attach(view);
    }

    @Override
    public void detach() {
        if (mView!=null){
            mView = null;
        }
    }

    private void attach(V view) {
        if (mView ==null){
            mView = view;
        }
    }

    @Override
    public void addDisposable(Disposable disposable) {
        if (mCompositeDisposable== null || mCompositeDisposable.isDisposed()){
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);

    }

    @Override
    public void doDispose() {
        if (mCompositeDisposable!=null){
            mCompositeDisposable.dispose();
        }
    }
}
