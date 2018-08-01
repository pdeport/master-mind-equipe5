package com.mmfive;/*
 * (c) Copyright Ymagis S.A.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */


import com.mmfive.api.ApiClient;
import com.mmfive.exceptions.ApiCallFailedException;
import com.mmfive.exceptions.requests.TestRequest;
import com.mmfive.responses.StartResponse;
import com.mmfive.responses.TestResponse;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MM5_DevTeam
 */
public class Apps {
    static int MAX_NUMBER = 10; // from 0 to 9
    private static int tryTime;

    final static int[] findNumbersInCode(ApiClient apiClient,StartResponse startResponse) throws ApiCallFailedException {
        int res[] = new int[MAX_NUMBER];
        int found = 0;

        for (int i = 0; i < MAX_NUMBER && found < startResponse.getSize(); i++) {
            String codeTest = new String();
            for (int j = 0; j < startResponse.getSize(); j++) {
                codeTest += i;
            }
            TestResponse testReturn = tryCode(codeTest, apiClient);

            res[i] = testReturn.getGood();
            found += testReturn.getGood();
        }

        return res;
    }

    public static String replaceCharAt(String s, int pos, int c) {
        return s.substring(0, pos) + c + s.substring(pos + 1);
    }

    public static int searchNumberAtIndex(int index, String testCode, int[] resfindNumbersInCode, TestResponse test, ApiClient apiClient) throws ApiCallFailedException {
        System.out.println(testCode);
        for (int i = 0; i < MAX_NUMBER; i++) {
            if (i != Integer.parseInt(testCode.charAt(index) + "")
                    && resfindNumbersInCode[i] > 0) {
                String testCode2 = replaceCharAt(testCode, index, (char) i);
                TestResponse test2 = tryCode(testCode2, apiClient);

                if (test2.getGood() > test.getGood()) {
                    return i;
                } else if (test2.getGood() < test.getGood()) {
                    return Integer.parseInt(testCode.charAt(index) + "");
                }
            }
        }
        System.out.println("Error to manage");
        return 11;
    }

    final static String generateCodeFromTable(int[] resfindNumbersInCode) {
        String testCode = "";
        for (int i = 0; i < MAX_NUMBER; i++) {
            for (int j = 0; j < resfindNumbersInCode[i]; j++) {
                testCode += i;
            }
        }
        return testCode;
    }

    final static String sortNumbersInCode(int[] resfindNumbersInCode, ApiClient apiClient,StartResponse startResponse) throws ApiCallFailedException {
        String finalCode = "";
        for (int i = 0; i < startResponse.getSize(); i++) {
            String testCode = finalCode + generateCodeFromTable(resfindNumbersInCode);
            TestResponse test = tryCode(testCode, apiClient);
            if (test.getGood() < startResponse.getSize()) {
                int foundNumber = searchNumberAtIndex(i, testCode, resfindNumbersInCode, test, apiClient);
                finalCode += foundNumber;
                resfindNumbersInCode[foundNumber]--;
            } else {
                i = startResponse.getSize();
                finalCode = testCode;
            }
        }
        return finalCode;
    }

    final static TestResponse tryCode(String code, ApiClient apiClient) throws ApiCallFailedException {
        tryTime++;
        if (code == "53375480") {
            return new TestResponse(code.length(), 0);
        }
        return apiClient.getTestResponse(code);
    }

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
            int[] resfindNumbersInCode = findNumbersInCode(apiClient,startResponse);
            String foundCode = sortNumbersInCode(resfindNumbersInCode, apiClient,startResponse);
            System.out.println("Found Code : " + foundCode);
            System.out.println("Number of try : " + tryTime);
        } catch (Exception ex) {
            Logger.getLogger(Apps.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
