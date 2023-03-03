package com.codechallenge.a20230303_joshuahand_nycschools.di.app_component;

import com.codechallenge.a20230303_joshuahand_nycschools.data.SchoolListDbRepoImpl;
import com.codechallenge.a20230303_joshuahand_nycschools.data.SchoolListWebRepoImpl;
import com.codechallenge.a20230303_joshuahand_nycschools.domain.get_school_list_interactor.GetSchoolListInteractor;
import com.codechallenge.a20230303_joshuahand_nycschools.domain.get_school_list_interactor.SchoolListDbRepo;
import com.codechallenge.a20230303_joshuahand_nycschools.domain.get_school_list_interactor.SchoolListRepo;
import com.codechallenge.a20230303_joshuahand_nycschools.domain.get_school_list_interactor.impl.GetSchoolListInteractorImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class SchoolListModule {

    @Singleton
    @Provides
    static SchoolListRepo getSchoolListRepo(SchoolListWebRepoImpl schoolListWebRepo){
        return schoolListWebRepo;
    }

    @Singleton
    @Provides
    static SchoolListDbRepo getSchoolListDbRepo(SchoolListDbRepoImpl schoolListDbRepo){
        return schoolListDbRepo;
    }

    @Singleton
    @Provides
    static GetSchoolListInteractor getSchoolListInteractor(GetSchoolListInteractorImpl getSchoolListInteractor){
        return getSchoolListInteractor;
    }

}
