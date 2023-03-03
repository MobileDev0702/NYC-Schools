package com.codechallenge.a20230303_joshuahand_nycschools.domain.get_sat_score_interactor;

import com.codechallenge.a20230303_joshuahand_nycschools.domain.get_sat_score_interactor.data.SatDataResponse;

import io.reactivex.Single;

public interface SatScoreDataRepo {

    Single<SatDataResponse> getSatScoreDataByDbn(String dbn);

}
