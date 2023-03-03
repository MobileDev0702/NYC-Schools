package com.codechallenge.a20230303_joshuahand_nycschools.network.auth_token_provider;

import java.util.Map;

public interface AuthTokenProvider {

   Map<String, String> getAuthTokenHeaders();

}
