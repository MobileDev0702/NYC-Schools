package com.codechallenge.a20230303_joshuahand_nycschools.data.base;

import com.codechallenge.a20230303_joshuahand_nycschools.network.api_url_provider.ApiUrlProvider;
import com.codechallenge.a20230303_joshuahand_nycschools.network.auth_token_provider.AuthTokenProvider;
import com.codechallenge.a20230303_joshuahand_nycschools.network.http_client.HttpClient;
public abstract class AbstractWebRepo {

    protected final HttpClient httpClient;
    protected final AuthTokenProvider authTokenProvider;
    protected final ApiUrlProvider apiUrlProvider;

    public AbstractWebRepo(HttpClient httpClient, AuthTokenProvider authTokenProvider, ApiUrlProvider apiUrlProvider) {
        this.httpClient = httpClient;
        this.authTokenProvider = authTokenProvider;
        this.apiUrlProvider = apiUrlProvider;
    }

}