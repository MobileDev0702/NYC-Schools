package com.codechallenge.a20230303_joshuahand_nycschools.domain.get_school_list_interactor;

import com.codechallenge.a20230303_joshuahand_nycschools.domain.get_school_list_interactor.data.SchoolListResponse;
import com.codechallenge.a20230303_joshuahand_nycschools.entities.Borough;

import io.reactivex.Single;


public interface SchoolListRepo {

    Single<SchoolListResponse> getSchoolsByBorough(Borough borough);

}
