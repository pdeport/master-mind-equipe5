/*
 * (c) Copyright Ymagis S.A.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.mmfive.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mmfive.api.tools.ArrayAdapterFactory;
import com.mmfive.exceptions.ApiCallFailedException;
import com.mmfive.exceptions.requests.TestRequest;
import com.mmfive.responses.TestResponse;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.client.urlconnection.HttpURLConnectionFactory;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.codehaus.jackson.map.ObjectMapper;
import sun.text.resources.FormatData;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

/**
 * @author MM5_DevTeam
 */
public class ApiFactory {

    private final static String RESULT_STRING = "result";
    private final String baseUrl;
    private final ObjectMapper mapper;
    private final Client client;
    private final String tokenKey;
    private final String tokenValue;
    private final Gson gson = new GsonBuilder().create();

    public ApiFactory(final String baseUrl, final String tokenKey, final String tokenValue, final int connectionTimeout, final int readTimeout) {
        this.baseUrl = baseUrl;
        client = getClient(connectionTimeout, readTimeout);
        this.tokenKey = tokenKey;
        this.tokenValue = tokenValue;
        this.mapper = new ObjectMapper();
    }

    private Client getClient(final int connectionTimeout, final int readTimeout) {
        Client c;
        c = new Client(new URLConnectionClientHandler(new HttpURLConnectionFactory() {
            @Override
            public HttpURLConnection getHttpURLConnection(URL url) throws IOException {
                return (HttpURLConnection) url.openConnection();
            }
        }));
        c.setConnectTimeout(connectionTimeout);
        c.setReadTimeout(readTimeout);
        return c;
    }

    public <T> T getEndpointResponse(String path, Type type,String result) throws ApiCallFailedException {
        ClientResponse res = null;
        MultivaluedMap formData = new MultivaluedMapImpl();
        try {
            if (result.equals(null)) {
                formData.add("token", "tokenmm5");
                res = client.resource(baseUrl).path(path)
                        .header(tokenKey, tokenValue)
                        .accept(MediaType.APPLICATION_JSON)
                        .post(ClientResponse.class);
            }else {
                formData.add("token", "tokenmm5");
                formData.add("result", result);
                res = client.resource(baseUrl).path(path)
                        .accept(MediaType.APPLICATION_JSON)
                        .post(ClientResponse.class, formData);
            }

            if (res.getStatus() == 200) {
                Reader reader = new InputStreamReader(res.getEntityInputStream());
                T data = gson.fromJson(reader, type);
                Logger.getLogger("Api Good");
                return data;
            } else {
                Logger.getLogger("");
                throw new ApiCallFailedException("the appi call to " + type.getClass().getName() + " failed, code error : " + res.getStatus());
            }
        } catch (ApiCallFailedException e) {
            Logger.getLogger("");
            throw new ApiCallFailedException("Api failed while trying to call the Endpoint :" + e.toString());
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (Exception ignored) {
                    Logger.getLogger("");
                }
            }
        }
    }

}
