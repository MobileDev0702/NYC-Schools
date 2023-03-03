package com.codechallenge.a20230303_joshuahand_nycschools.domain.get_sat_score_interactor;

import com.codechallenge.a20230303_joshuahand_nycschools.entities.SatScoreData;

import io.reactivex.Completable;

public interface SatScoreDataDbRepo extends SatScoreDataRepo{

    Completable storeSatData(SatScoreData satScoreData);

}
