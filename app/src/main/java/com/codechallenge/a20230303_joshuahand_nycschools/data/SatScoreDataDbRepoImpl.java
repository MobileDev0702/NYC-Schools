package com.codechallenge.a20230303_joshuahand_nycschools.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.codechallenge.a20230303_joshuahand_nycschools.data.database.OpenDbHelper;
import com.codechallenge.a20230303_joshuahand_nycschools.domain.get_sat_score_interactor.SatScoreDataDbRepo;
import com.codechallenge.a20230303_joshuahand_nycschools.domain.get_sat_score_interactor.data.SatDataResponse;
import com.codechallenge.a20230303_joshuahand_nycschools.entities.SatScoreData;
import com.codechallenge.a20230303_joshuahand_nycschools.rx_util.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class SatScoreDataDbRepoImpl implements SatScoreDataDbRepo{

    private final OpenDbHelper openDbHelper;
    private final SchedulerProvider schedulerProvider;

    @Inject
    public SatScoreDataDbRepoImpl(OpenDbHelper openDbHelper, SchedulerProvider schedulerProvider) {
        this.openDbHelper = openDbHelper;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public Single<SatDataResponse> getSatScoreDataByDbn(String dbn) {

        return Single.fromCallable(() -> {

            String query = "SELECT * FROM " + OpenDbHelper.SCORE_DATA_TABLE
                    + " WHERE " + OpenDbHelper.SCORE_DATA_DBN + " = " + "\"" + dbn + "\"";

            SQLiteDatabase readableDatabase = openDbHelper.getReadableDatabase();

            Cursor cursor = readableDatabase.rawQuery(query, null);

            SatScoreData satScoreData = null;

            if(cursor.moveToFirst()){

                String dbnFromDb    = cursor.getString(0);
                boolean isAvailable = cursor.getInt(1) == 1;
                int math            = cursor.getInt(2);
                int reading         = cursor.getInt(3);
                int writing         = cursor.getInt(4);

                satScoreData = SatScoreData.newBuilder()
                        .dbn(dbnFromDb)
                        .isDataAvailable(isAvailable)
                        .math(isAvailable ? math : -1)
                        .reading(isAvailable ? reading : -1)
                        .writing(isAvailable ? writing : -1)
                        .build();

            }

            cursor.close();
            readableDatabase.close();

            if(satScoreData == null){
                return SatDataResponse.failure();
            }
            return SatDataResponse.success(satScoreData);

        }).subscribeOn(schedulerProvider.db());

    }

    @Override
    public Completable storeSatData(SatScoreData satScoreData) {
        return Completable.fromAction(() -> {
            ContentValues contentValues = new ContentValues();

            contentValues.put(OpenDbHelper.SCORE_DATA_DBN,          satScoreData.getDbn());
            contentValues.put(OpenDbHelper.SCORE_DATA_IS_AVAILABLE, satScoreData.isDataAvailable() ? 1 : 2);
            contentValues.put(OpenDbHelper.SCORE_DATA_MATH,         satScoreData.getMath());
            contentValues.put(OpenDbHelper.SCORE_DATA_READING,      satScoreData.getReading());
            contentValues.put(OpenDbHelper.SCORE_DATA_WRITING,      satScoreData.getWriting());

            SQLiteDatabase writableDatabase = openDbHelper.getWritableDatabase();

            try {
                writableDatabase.insertWithOnConflict(OpenDbHelper.SCORE_DATA_TABLE, null, contentValues, SQLiteDatabase.CONFLICT_ROLLBACK);
            }
            catch (Exception e){
                e.printStackTrace();
            }

            writableDatabase.close();
        }).subscribeOn(schedulerProvider.db());
    }
}
