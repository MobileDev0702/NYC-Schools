package com.codechallenge.a20230303_joshuahand_nycschools.di;

import com.codechallenge.a20230303_joshuahand_nycschools.di.app_component.AppComponent;

public class ComponentProviderImpl implements ComponentProvider{

    private final AppComponent appComponent;

    private static ComponentProvider instance;

    private ComponentProviderImpl(AppComponent appComponent) {
        this.appComponent = appComponent;
    }

    public static void initComponentProvider(AppComponent appComponent) {
        instance = new ComponentProviderImpl(appComponent);
    }

    public static ComponentProvider getInstance() {
        return instance;
    }

    @Override
    public AppComponent getAppComponent() {
        return appComponent;
    }
}
