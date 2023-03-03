package com.codechallenge.a20230303_joshuahand_nycschools.di.app_component;

import com.codechallenge.a20230303_joshuahand_nycschools.data.SatScoreDataDbRepoImpl;
import com.codechallenge.a20230303_joshuahand_nycschools.data.SatScoreDataRepoImpl;
import com.codechallenge.a20230303_joshuahand_nycschools.domain.get_sat_score_interactor.GetSatScoreDataInteractor;
import com.codechallenge.a20230303_joshuahand_nycschools.domain.get_sat_score_interactor.SatScoreDataDbRepo;
import com.codechallenge.a20230303_joshuahand_nycschools.domain.get_sat_score_interactor.SatScoreDataRepo;
import com.codechallenge.a20230303_joshuahand_nycschools.domain.get_sat_score_interactor.impl.GetSatScoreDataInteractorImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class SatScoreModule {

    @Singleton
    @Provides
    static GetSatScoreDataInteractor getSatScoreDataInteractor(GetSatScoreDataInteractorImpl getSatScoreDataInteractor){
        return getSatScoreDataInteractor;
    }

    @Singleton
    @Provides
    static SatScoreDataRepo satScoredDataRepo(SatScoreDataRepoImpl satScoredDataRepo){
        return satScoredDataRepo;
    }

    @Singleton
    @Provides
    static SatScoreDataDbRepo satScoredDataDbRepo(SatScoreDataDbRepoImpl satScoredDataRepo){
        return satScoredDataRepo;
    }

}

