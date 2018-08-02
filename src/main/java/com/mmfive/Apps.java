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
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MM5_DevTeam
 */
public class Apps {

    public static List<Character> generatePossibleValueList() {
        List<Character> possibleChar = new ArrayList<>();
        for (int i = 48; i < 48 + 10; i++) {
            possibleChar.add((char) i);
        }
        for (int i = 65; i < 65 + 26; i++) {
            possibleChar.add((char) i);
        }
        for (int i = 97; i < 97 + 26; i++) {
            possibleChar.add((char) i);
        }
        return possibleChar;
    }
    
    public static List<Character> POSSIBLE_CHAR_LIST = generatePossibleValueList();
    static int MAX_NUMBER = POSSIBLE_CHAR_LIST.size();
    private static int tryTime;

    final static int[] findNumbersInCode(ApiClient apiClient, StartResponse startResponse) throws ApiCallFailedException {
        int res[] = new int[MAX_NUMBER];
        int found = 0;

        for (int i = 0; i < MAX_NUMBER && found < startResponse.getSize(); i++) {
            String codeTest = new String();
            for (int j = 0; j < startResponse.getSize(); j++) {
                codeTest += POSSIBLE_CHAR_LIST.get(i);
            }
            TestResponse testReturn = tryCode(codeTest, apiClient);

            res[i] = testReturn.getGood();
            found += testReturn.getGood();
        }

        return res;
    }

    public static String replaceCharAt(String s, int pos, char c) {
        return s.substring(0, pos) + c + s.substring(pos + 1);
    }

    public static char searchCharAtIndex(int index, String testCode, int[] resfindNumbersInCode, TestResponse test, ApiClient apiClient) throws ApiCallFailedException {
        for (int i = 0; i < MAX_NUMBER; i++) {
            if (POSSIBLE_CHAR_LIST.get(i) != testCode.charAt(index)
                    && resfindNumbersInCode[i] > 0) {
                String testCode2 = replaceCharAt(testCode, index, POSSIBLE_CHAR_LIST.get(i));
                TestResponse test2 = tryCode(testCode2, apiClient);

                if (test2.getGood() > test.getGood()) {
                    return POSSIBLE_CHAR_LIST.get(i);
                } else if (test2.getGood() < test.getGood()) {
                    return testCode.charAt(index);
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
                testCode += POSSIBLE_CHAR_LIST.get(i);
            }
        }
        return testCode;
    }

    final static String sortNumbersInCode(int[] resfindNumbersInCode, ApiClient apiClient, StartResponse startResponse) throws ApiCallFailedException {
        String finalCode = "";
        for (int i = 0; i < startResponse.getSize(); i++) {
            String testCode = finalCode + generateCodeFromTable(resfindNumbersInCode);
            TestResponse test = tryCode(testCode, apiClient);
            if (test.getGood() < startResponse.getSize()) {
                char foundChar = searchCharAtIndex(i, testCode, resfindNumbersInCode, test, apiClient);
                finalCode += foundChar;
                resfindNumbersInCode[POSSIBLE_CHAR_LIST.indexOf(foundChar)]--;
            } else {
                i = startResponse.getSize();
                finalCode = testCode;
            }
        }
        return finalCode;
    }

    final static TestResponse tryCode(String code, ApiClient apiClient) throws ApiCallFailedException {
        tryTime++;
        return apiClient.getTestResponse(code);
    }

    public static void main(String[] args) {
        try {
            StartResponse startResponse = null;
            ApiClient apiClient = new ApiClient("http://172.16.37.129/api/", "token", "tokenmm5", 30000, 3000);
            try {
                startResponse = apiClient.getStartResponse();
                System.out.println(startResponse.toString());
            } catch (Exception ex) {
                System.out.println("Already started");
                startResponse = new StartResponse(28, "mm5", 123456);
                System.out.println(startResponse.toString());
            }
            int[] resfindNumbersInCode = findNumbersInCode(apiClient, startResponse);
            String foundCode = sortNumbersInCode(resfindNumbersInCode, apiClient, startResponse);
            System.out.println("Found Code : " + foundCode);
            System.out.println("Number of try : " + tryTime);
        } catch (Exception ex) {
            Logger.getLogger(Apps.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
