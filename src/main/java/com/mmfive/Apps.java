package com.mmfive;/*
 * (c) Copyright Ymagis S.A.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
*/


import com.mmfive.api.ApiClient;
import com.mmfive.responses.StartResponse;
import com.mmfive.responses.TestResponse;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MM5_DevTeam
 */
public class Apps {

    public static void main(String[] args) {
        try {
            System.out.println("Testing schedule retrieval: OK");

            ApiClient apiClient = new ApiClient("http://172.16.37.129/api/","token","tokenmm5",30000,3000);
            TestResponse testResponse = apiClient.getTestResponse("12145");
            StartResponse startResponse = apiClient.getStartResponse();
            System.out.println(testResponse.toString());
            System.out.println(startResponse.toString());
        } catch (Exception ex) {
            Logger.getLogger(Apps.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
