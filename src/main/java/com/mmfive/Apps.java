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

    static String CODE = "798697264532";
    static int MAX_CODE_DIGIT = CODE.length();
    static int MAX_NUMBER = 10; // from 0 to 9
    private static int tryTime;
    TestRequest testRequest = new TestRequest("12145", "tokenmm5");


    final static int[] findNumbersInCode(int maxNumber, ApiClient apiClient) throws ApiCallFailedException {
        int res[] = new int[MAX_NUMBER];
        int found = 0;

        for (int i = 0; i < MAX_NUMBER && found < MAX_CODE_DIGIT; i++) {
            String codeTest = new String();
            for (int j = 0; j < MAX_CODE_DIGIT; j++) {
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
        for (int i = 0; i < MAX_NUMBER; i++) {
            if (i != Integer.parseInt(testCode.charAt(index) + "") && resfindNumbersInCode[i] > 0) {
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

    final static String sortNumbersInCode(int[] resfindNumbersInCode, ApiClient apiClient) throws ApiCallFailedException {
        String finalCode = "";
        for (int i = 0; i < MAX_CODE_DIGIT; i++) {
            String testCode = finalCode + generateCodeFromTable(resfindNumbersInCode);
            TestResponse test = tryCode(testCode, apiClient);
            if (test.getGood() < MAX_CODE_DIGIT) {
                int foundNumber = searchNumberAtIndex(i, testCode, resfindNumbersInCode, test, apiClient);
                finalCode += foundNumber;
                resfindNumbersInCode[foundNumber]--;
            } else {
                i = MAX_CODE_DIGIT;
                finalCode = testCode;
            }
        }
        return finalCode;
    }

    final static TestResponse tryCode(String code, ApiClient apiClient) throws ApiCallFailedException {
        if (code == "12345") {
            return new TestResponse(CODE.length(), 0);
        }
        return apiClient.getTestResponse(code);
    }

    public static void main(String[] args) {
        try {
            System.out.println("Testing schedule retrieval: OK");

            ApiClient apiClient = new ApiClient("http://172.16.37.129/api/", "token", "tokenmm5", 30000, 3000);
            TestRequest testRequest = new TestRequest("12145", "tokenmm5");
            try {
                StartResponse startResponse = apiClient.getStartResponse();
                System.out.println(startResponse.toString());
            } catch (ApiCallFailedException ex) {
                System.out.println("Already started");
            }

            int[] resfindNumbersInCode = findNumbersInCode(MAX_CODE_DIGIT, apiClient);

            String foundCode = sortNumbersInCode(resfindNumbersInCode, apiClient);
            System.out.println("Found Code : " + foundCode);
            System.out.println("Number of try : " + tryTime);
            System.out.println("Is it the good one ? " + CODE.equals(foundCode));
        } catch (Exception ex) {
            Logger.getLogger(Apps.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
