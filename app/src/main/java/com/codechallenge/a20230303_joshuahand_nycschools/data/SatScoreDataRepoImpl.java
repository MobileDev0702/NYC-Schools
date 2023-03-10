package com.codechallenge.a20230303_joshuahand_nycschools.data;

import com.codechallenge.a20230303_joshuahand_nycschools.data.base.AbstractWebRepo;
import com.codechallenge.a20230303_joshuahand_nycschools.domain.get_sat_score_interactor.SatScoreDataRepo;
import com.codechallenge.a20230303_joshuahand_nycschools.domain.get_sat_score_interactor.data.SatDataResponse;
import com.codechallenge.a20230303_joshuahand_nycschools.entities.SatScoreData;
import com.codechallenge.a20230303_joshuahand_nycschools.network.api_url_provider.ApiUrlProvider;
import com.codechallenge.a20230303_joshuahand_nycschools.network.auth_token_provider.AuthTokenProvider;
import com.codechallenge.a20230303_joshuahand_nycschools.network.http_client.HttpClient;
import com.codechallenge.a20230303_joshuahand_nycschools.network.http_client.NetworkRequest;
import com.codechallenge.a20230303_joshuahand_nycschools.network.http_client.NetworkResponse;
import com.codechallenge.a20230303_joshuahand_nycschools.util.StringUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;

public class SatScoreDataRepoImpl extends AbstractWebRepo implements SatScoreDataRepo {

    @Inject
    SatScoreDataRepoImpl(HttpClient httpClient, AuthTokenProvider authTokenProvider, ApiUrlProvider apiUrlProvider) {
        super(httpClient, authTokenProvider, apiUrlProvider);
    }

    @Override
    public Single<SatDataResponse> getSatScoreDataByDbn(String dbn) {
        if(!StringUtil.isStringValid(dbn)){
            return Single.just(SatDataResponse.failure());
        }

        String satApi = apiUrlProvider.getSchoolSatApi();
        satApi += "?dbn=" + dbn;

        Map<String, String> authTokenHeaders = authTokenProvider.getAuthTokenHeaders();

        NetworkRequest networkRequest = NetworkRequest.createNetworkRequest(satApi, authTokenHeaders);

        return httpClient.getJsonArray(networkRequest)
                .map(networkResponse -> parseSatDataResponse(networkResponse, dbn));
    }

    private static SatDataResponse parseSatDataResponse(NetworkResponse<JSONArray> networkResponse, String dbn){
        JSONArray data = networkResponse.getData();
        if(!networkResponse.isSuccessful() || data == null){
            return SatDataResponse.failure();
        }

        JSONObject satDataObject = data.optJSONObject(0);

        if(satDataObject == null){
            return SatDataResponse.noDataAvailable(dbn);
        }

        String dbnFromJson = satDataObject.optString("dbn");

        //wrong school or null in response
        if(!dbn.equals(dbnFromJson)){
            return SatDataResponse.noDataAvailable(dbn);
        }

        int mathScore       = satDataObject.optInt("sat_math_avg_score", -1);
        int readingScore    = satDataObject.optInt("sat_critical_reading_avg_score", -1);
        int writingScore    = satDataObject.optInt("sat_writing_avg_score", -1);

        boolean isDataAvailable = true;
        if (mathScore == -1 || readingScore == -1 || writingScore == -1){
            isDataAvailable = false;
        }

        SatScoreData satScoreData = SatScoreData.newBuilder()
                .dbn(dbn)
                .isDataAvailable(isDataAvailable)
                .math(mathScore)
                .reading(readingScore)
                .writing(writingScore)
                .build();

        return SatDataResponse.success(satScoreData);

    }

}
