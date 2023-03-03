package com.codechallenge.a20230303_joshuahand_nycschools.di.school_list_component;

import com.codechallenge.a20230303_joshuahand_nycschools.view.school_list_activity.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract public class SchoolListActivityModule {

    @ContributesAndroidInjector
    abstract MainActivity contributeYourAndroidInjector();

}
