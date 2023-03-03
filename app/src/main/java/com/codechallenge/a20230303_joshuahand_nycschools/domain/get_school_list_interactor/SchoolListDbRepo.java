package com.codechallenge.a20230303_joshuahand_nycschools.domain.get_school_list_interactor;

import com.codechallenge.a20230303_joshuahand_nycschools.entities.School;

import java.util.List;

import io.reactivex.Completable;

public interface SchoolListDbRepo extends SchoolListRepo{

    Completable storeSchools(List<School> schools);

}
