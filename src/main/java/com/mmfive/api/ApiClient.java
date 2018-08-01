/*
 * (c) Copyright Ymagis S.A.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.mmfive.api;

import com.google.gson.reflect.TypeToken;
import com.mmfive.exceptions.ApiCallFailedException;
import com.mmfive.exceptions.requests.TestRequest;
import com.mmfive.responses.StartResponse;
import com.mmfive.responses.TestResponse;

import java.lang.reflect.Type;
import java.net.MalformedURLException;

/**
 * @author MM5_DevTeam
 */
public class ApiClient {

    private static final String TEST_END_POINT = "test";
    private static final String START_END_POINT = "start";

    private ApiFactory apiFactory;

    public ApiClient(String baseUrl, String tokenKey, String tokenValue, int connectionTimeout, int readTimeout) throws MalformedURLException {
        apiFactory = new ApiFactory(baseUrl, tokenKey, tokenValue, connectionTimeout, readTimeout);
    }

    public TestResponse getTestResponse(TestRequest result) throws ApiCallFailedException {
        Type testType = new TypeToken<TestResponse>() {
        }.getType();
        return apiFactory.getEndpointResponse(TEST_END_POINT, testType,result);
    }

    public StartResponse getStartResponse() throws ApiCallFailedException {
        Type testType = new TypeToken<TestResponse>() {
        }.getType();
        return apiFactory.getEndpointResponse(START_END_POINT, testType,null);
    }

}