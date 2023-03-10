package com.codechallenge.a20230303_joshuahand_nycschools.domain.get_school_list_interactor.impl;

import com.codechallenge.a20230303_joshuahand_nycschools.domain.get_school_list_interactor.GetSchoolListInteractor;
import com.codechallenge.a20230303_joshuahand_nycschools.domain.get_school_list_interactor.SchoolListDbRepo;
import com.codechallenge.a20230303_joshuahand_nycschools.domain.get_school_list_interactor.SchoolListRepo;
import com.codechallenge.a20230303_joshuahand_nycschools.domain.get_school_list_interactor.data.SchoolListResponse;
import com.codechallenge.a20230303_joshuahand_nycschools.entities.Borough;
import com.codechallenge.a20230303_joshuahand_nycschools.rx_util.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public class GetSchoolListInteractorImpl implements GetSchoolListInteractor{

    private final SchoolListRepo schoolListWebRepo;
    private final SchoolListDbRepo schoolListDbRepo;
    private final SchedulerProvider schedulerProvider;

    @Inject
    GetSchoolListInteractorImpl(SchoolListRepo schoolListWebRepo, SchoolListDbRepo schoolListDbRepo, SchedulerProvider schedulerProvider) {
        this.schoolListWebRepo = schoolListWebRepo;
        this.schoolListDbRepo = schoolListDbRepo;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public Single<SchoolListResponse> getSchoolsByBorough(Borough borough) {
        return schoolListDbRepo.getSchoolsByBorough(borough)
                .flatMap(schoolListResponse -> {
                    if (schoolListResponse.isSuccessful()) {
                        return Single.just(schoolListResponse);
                    }
                    return getSchoolsFromWeb(borough);
                });
    }

    private SingleSource<SchoolListResponse> getSchoolsFromWeb(Borough borough) {
        return schoolListWebRepo.getSchoolsByBorough(borough)
                .subscribeOn(schedulerProvider.io())
                .flatMap(this::cacheResults);
    }

    private SingleSource<SchoolListResponse> cacheResults(SchoolListResponse schoolListResponse) {
        if(schoolListResponse.isSuccessful()){
            //store result and then return the received data
            return schoolListDbRepo.storeSchools(schoolListResponse.getSchools())
                    .andThen(Single.just(schoolListResponse));
        }
        return Single.just(schoolListResponse);
    }


}
