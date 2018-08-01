package com.mmfive;/*
 * (c) Copyright Ymagis S.A.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
*/


import com.mmfive.api.ApiClient;
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

    static int MAX_CODE_DIGIT = 7;
    static String CODE = "3289532";
    static int MAX_NUMBER = 10; // from 0 to 9

    public static class ApiTestReturn {

        int wrong_place;
        int good;

        public ApiTestReturn(int wrong_place, int good) {
            this.wrong_place = wrong_place;
            this.good = good;
        }

        public int getWrong_place() {
            return wrong_place;
        }

        public void setWrong_place(int wrong_place) {
            this.wrong_place = wrong_place;
        }

        public int getGood() {
            return good;
        }

        public void setGood(int good) {
            this.good = good;
        }

        @Override
        public String toString() {
            return "Good : " + this.good + " - Wrong place : " + this.wrong_place;
        }

    }

    final static int[] findNumbersInCode(int maxNumber) {
        int res[] = new int[MAX_NUMBER];

        for (int i = 0; i < MAX_NUMBER; i++) {
            String codeTest = new String();
            for (int j = 0; j < MAX_CODE_DIGIT; j++) {
                codeTest += i;
            }
            ApiTestReturn testReturn = testCode(codeTest);
            res[i] = testReturn.getGood();
        }

        return res;
    }

    final static ApiTestReturn testCode(String code) {
        int good = 0;
        int wrong_place = 0;
        for (int i = 0; i < MAX_CODE_DIGIT; i++) {
            if (code.charAt(i) == CODE.charAt(i)) {
                good++;
            } else {
                for (int y = 0; y < MAX_CODE_DIGIT; y++) {
                    if (code.charAt(i) == CODE.charAt(y)) {
                        wrong_place++;
                        y = MAX_CODE_DIGIT;
                    }
                }
            }
        }

        return new ApiTestReturn(wrong_place, good);
    }

    public static void main(String[] args) {
//        int[] test = {3, 3, 3, 3, 3};
//        ApiTestReturn testResult = testCode(test);
//        System.out.println(testResult);

        int[] resfindNumbersInCode = findNumbersInCode(MAX_CODE_DIGIT);

        for (int i = 0; i < 10; i++) {
            System.out.println("Il y a " + resfindNumbersInCode[i] + " chiffre(s) " + i + " dans le code");
        }

        try {
            System.out.println("Testing schedule retrieval: OK");

            ApiClient apiClient = new ApiClient("http://172.16.37.129/api/","token","tokenmm5",30000,3000);
            TestRequest testRequest = new TestRequest("12145","tokenmm5");
            TestResponse testResponse = apiClient.getTestResponse(testRequest);

            System.out.println(testResponse.toString());
            StartResponse startResponse = apiClient.getStartResponse();
            System.out.println(startResponse.toString());
        } catch (Exception ex) {
            Logger.getLogger(Apps.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
