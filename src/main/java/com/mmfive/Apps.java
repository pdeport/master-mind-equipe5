package com.mmfive;
/*
 * (c) Copyright Ymagis S.A.
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */

import com.mmfive.api.ApiClient;
import com.mmfive.exceptions.ApiCallFailedException;
import com.mmfive.responses.StartResponse;

import java.sql.Time;
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
            StartResponse startResponse = null;
            ApiClient apiClient = new ApiClient("http://172.16.37.129/api/", "token", "tokenmm5", 30000, 3000);
            try {
                startResponse = apiClient.getStartResponse();
                System.out.println(startResponse.toString());
            } catch (ApiCallFailedException ex) {
                System.out.println("Already started");
                startResponse = new StartResponse(8,"mm5",123456);
                System.out.println(startResponse.toString());
            }
            int[] resfindNumbersInCode = Mmcinq.findNumbersInCode(apiClient,startResponse);
            String foundCode = Mmcinq.sortNumbersInCode(resfindNumbersInCode, apiClient,startResponse);
            System.out.println("Found Code : " + foundCode);
            System.out.println("Number of try : " + Mmcinq.getTryTime());
        } catch (Exception ex) {
            Logger.getLogger(Apps.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
