package com.codechallenge.a20230303_joshuahand_nycschools.view.base;

import androidx.lifecycle.ViewModel;

import com.codechallenge.a20230303_joshuahand_nycschools.di.ComponentProvider;
import com.codechallenge.a20230303_joshuahand_nycschools.di.ComponentProviderImpl;

import io.reactivex.disposables.CompositeDisposable;


public class BaseViewModel extends ViewModel {

    protected final CompositeDisposable onPauseDisposable = new CompositeDisposable();
    protected final CompositeDisposable onDestroyDisposable = new CompositeDisposable();
    protected final ComponentProvider componentProvider = ComponentProviderImpl.getInstance();

}
