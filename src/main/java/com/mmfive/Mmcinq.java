package com.mmfive;

import com.mmfive.api.ApiClient;
import com.mmfive.exceptions.ApiCallFailedException;
import com.mmfive.responses.StartResponse;
import com.mmfive.responses.TestResponse;

import java.util.ArrayList;
import java.util.List;

public class Mmcinq {

    public static int getTryTime() {
        return tryTime;
    }
    private static int tryTime;

    public static List<Character> generateAlphanumList(){
        List<Character> c = new ArrayList<>();
        for (int i = 48; i < 48 + 10; i++){
            c.add((char)i);
        }
        for (int i = 65; i < 65 + 26 + 26; i++){
            c.add((char) i );
        }
        return c;
    }

    public static List<Character>  CHAR_LIST = generateAlphanumList();
    public static int  MAX_NUMBER = CHAR_LIST.size();


    public static int[] findNumbersInCode(ApiClient apiClient, StartResponse startResponse) throws ApiCallFailedException {
        int res[] = new int[MAX_NUMBER];
        int found = 0;

        for (int i = 0; i < MAX_NUMBER && found < startResponse.getSize(); i++) {
            String codeTest = new String();
            for (int j = 0; j < startResponse.getSize(); j++) {
                codeTest += CHAR_LIST.get(i);
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

    public final static String generateCodeFromTable(int[] resfindNumbersInCode) {
        String testCode = "";
        for (int i = 0; i < MAX_NUMBER; i++) {
            for (int j = 0; j < resfindNumbersInCode[i]; j++) {
                testCode += CHAR_LIST.get(i);
            }
        }
        return testCode;
    }

    public final static String sortNumbersInCode(int[] resfindNumbersInCode, ApiClient apiClient,StartResponse startResponse) throws ApiCallFailedException {
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

    public final static TestResponse tryCode(String code, ApiClient apiClient) throws ApiCallFailedException {
        tryTime++;
       /* if (code == "53375480") {
            return new TestResponse(code.length(), 0);
        }*/
        return apiClient.getTestResponse(code);
    }

}
