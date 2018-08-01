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

    static String CODE = "798697264532";
    static int MAX_CODE_DIGIT = CODE.length();
    static int MAX_NUMBER = 10; // from 0 to 9
    private static int tryTime;

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
        int found = 0;

        for (int i = 0; i < MAX_NUMBER && found < MAX_CODE_DIGIT; i++) {
            String codeTest = new String();
            for (int j = 0; j < MAX_CODE_DIGIT; j++) {
                codeTest += i;
            }
            ApiTestReturn testReturn = tryCode(codeTest);
            res[i] = testReturn.getGood();
            found += testReturn.getGood();
        }

        return res;
    }

    public static String replaceCharAt(String s, int pos, int c) {
        return s.substring(0, pos) + c + s.substring(pos + 1);
    }

    public static int searchNumberAtIndex(int index, String testCode, int[] resfindNumbersInCode, ApiTestReturn test) {
        for (int i = 0; i < MAX_NUMBER; i++) {
            if (i != Integer.parseInt(testCode.charAt(index) + "") && resfindNumbersInCode[i] > 0) {
                String testCode2 = replaceCharAt(testCode, index, (char) i);
                ApiTestReturn test2 = tryCode(testCode2);
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

    final static String sortNumbersInCode(int[] resfindNumbersInCode) {
        String finalCode = "";
        for (int i = 0; i < MAX_CODE_DIGIT; i++) {
            String testCode = finalCode + generateCodeFromTable(resfindNumbersInCode);
            ApiTestReturn test = tryCode(testCode);
            if (test.getGood() < MAX_CODE_DIGIT) {
                int foundNumber = searchNumberAtIndex(i, testCode, resfindNumbersInCode, test);
                finalCode += foundNumber;
                resfindNumbersInCode[foundNumber]--;
            } else {
                i = MAX_CODE_DIGIT;
                finalCode = testCode;
            }
        }
        return finalCode;
    }

    final static ApiTestReturn tryCode(String code) {
        int good = 0;
        int wrong_place = 0;
        tryTime++;

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
        int[] resfindNumbersInCode = findNumbersInCode(MAX_CODE_DIGIT);

        for (int i = 0; i < 10; i++) {
            System.out.println("Il y a " + resfindNumbersInCode[i] + " chiffre(s) " + i + " dans le code");
        }

        try {
            System.out.println("Testing schedule retrieval: OK");

            ApiClient apiClient = new ApiClient("http://172.16.37.129/api/", "token", "tokenmm5", 30000, 3000);
            TestRequest testRequest = new TestRequest("12145", "tokenmm5");
            TestResponse testResponse = apiClient.getTestResponse(testRequest);

            System.out.println(testResponse.toString());
            StartResponse startResponse = apiClient.getStartResponse();
            System.out.println(startResponse.toString());
        } catch (Exception ex) {
            Logger.getLogger(Apps.class.getName()).log(Level.SEVERE, null, ex);
        }

        String foundCode = sortNumbersInCode(resfindNumbersInCode);
        System.out.println("Found Code : " + foundCode);
        System.out.println("Number of try : " + tryTime);
        System.out.println("Is it the good one ? " + CODE.equals(foundCode));
    }
}
