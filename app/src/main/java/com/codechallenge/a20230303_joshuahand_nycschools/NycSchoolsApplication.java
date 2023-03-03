package com.codechallenge.a20230303_joshuahand_nycschools;

import android.app.Activity;
import android.app.Application;

import com.codechallenge.a20230303_joshuahand_nycschools.di.app_component.DaggerAppComponent;
import com.codechallenge.a20230303_joshuahand_nycschools.di.app_component.DbModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class NycSchoolsApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder()
                .dbModule(new DbModule(getApplicationContext()))
                .build()
                .inject(this);

    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
