package com.codechallenge.a20230303_joshuahand_nycschools.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.codechallenge.a20230303_joshuahand_nycschools.data.database.OpenDbHelper;
import com.codechallenge.a20230303_joshuahand_nycschools.domain.get_school_list_interactor.SchoolListDbRepo;
import com.codechallenge.a20230303_joshuahand_nycschools.domain.get_school_list_interactor.data.SchoolListResponse;
import com.codechallenge.a20230303_joshuahand_nycschools.entities.Borough;
import com.codechallenge.a20230303_joshuahand_nycschools.entities.School;
import com.codechallenge.a20230303_joshuahand_nycschools.rx_util.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class SchoolListDbRepoImpl implements SchoolListDbRepo {

    private final OpenDbHelper openDbHelper;
    private final SchedulerProvider schedulerProvider;

    @Inject
    public SchoolListDbRepoImpl(OpenDbHelper openDbHelper, SchedulerProvider schedulerProvider) {
        this.openDbHelper = openDbHelper;
        this.schedulerProvider = schedulerProvider;
    }


    @Override
    public Completable storeSchools(List<School> schools) {
        return Completable.fromAction(() -> {
            SQLiteDatabase writableDatabase = openDbHelper.getWritableDatabase();
            for(School school : schools){

                ContentValues contentValues = new ContentValues();

                contentValues.put(OpenDbHelper.SCHOOL_DBN,      school.getDbn());
                contentValues.put(OpenDbHelper.SCHOOL_NAME,     school.getName());
                contentValues.put(OpenDbHelper.SCHOOL_BOROUGH,  school.getBorough().code);
                contentValues.put(OpenDbHelper.SCHOOL_WEB_PAGE_LINK, school.getWebPageLink());

                try {
                    writableDatabase.insertWithOnConflict(OpenDbHelper.SCHOOL_TABLE, null, contentValues, SQLiteDatabase.CONFLICT_ROLLBACK);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            openDbHelper.close();

        }).subscribeOn(schedulerProvider.db());
    }

    @Override
    public Single<SchoolListResponse> getSchoolsByBorough(Borough borough) {
        return Single.fromCallable(() -> {

            List<School> schoolList = new ArrayList<>();

            String query = "SELECT * FROM " + OpenDbHelper.SCHOOL_TABLE
                    + " WHERE " + OpenDbHelper.SCHOOL_BOROUGH + " = " + "\"" + borough.code + "\"";

            SQLiteDatabase readableDatabase = openDbHelper.getReadableDatabase();

            Cursor cursor = readableDatabase.rawQuery(query, null);

            while (cursor.moveToNext()){

                String dbn      = cursor.getString(0);
                String name     = cursor.getString(1);
                String webpage  = cursor.getString(3);

                School school = School.newBuilder()
                        .dbn(dbn)
                        .name(name)
                        .borough(borough)
                        .webpageLink(webpage)
                        .build();

                schoolList.add(school);

            }

            cursor.close();
            readableDatabase.close();

            if(schoolList.isEmpty()){
                return SchoolListResponse.failure();
            }

            return SchoolListResponse.success(schoolList, borough);
        });
    }
}
