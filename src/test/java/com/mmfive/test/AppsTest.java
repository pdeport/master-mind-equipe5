package com.mmfive.test;

import com.mmfive.Mmcinq;
import com.mmfive.api.ApiClient;
import com.mmfive.exceptions.ApiCallFailedException;
import com.mmfive.responses.StartResponse;
import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.net.MalformedURLException;


public class AppsTest extends TestCase {

    static String CODE = "123456789";
    static int MAX_CODE_DIGIT = CODE.length();
    static int MAX_NUMBER = 10; // from 0 to 9


/*
    @Test
    public void testFindNumberInCode(){
        int[] expected = new int[]{0,0,2,1,1,1,2,2,1,2};
        int[] result = Mmcinq.findNumbersInCode(MAX_NUMBER);
        assertArrayEquals(expected,result);
    }

    @Test
    public void replaceCharAt(){
        String result = Mmcinq.replaceCharAt("12345",1,(char) 3);
        assertEquals("13345" ,result);
    }

    @Test
    public void searchNumberAtIndex() throws ApiCallFailedException, MalformedURLException {
//        TestResponse tr = new TestResponse(0,0 );
//        int[] t = new int[]{1,2,6,8,0};
//        int n = Mmcinq.searchNumberAtIndex(1,"12345", t , tr,
//                new ApiClient("http://172.16.37.129/api/", "token", "tokenmm5", 30000, 3000));
        Assertions.assertEquals(2,2);
    }*/

    @Test
    public void generateCodeFromTable() throws MalformedURLException, ApiCallFailedException {

        try {
            System.out.println("Testing schedule retrieval: OK");

            try {
                startResponse = apiClient.getStartResponse();
                System.out.println(startResponse.toString());
            } catch (ApiCallFailedException ex) {
                System.out.println("Already started");
                startResponse = new StartResponse(8,"mm5",123456);
                System.out.println(startResponse.toString());
        StartResponse sr = null;
        ApiClient apiClient = new ApiClient("http://172.16.37.129/api/", "token", "tokenmm5", 30000, 3000);

        sr =  new ApiClient("http://172.16.37.129/api/", "token", "tokenmm5", 30000, 3000).getStartResponse();
        int[] t = Mmcinq.findNumbersInCode( new ApiClient("http://172.16.37.129/api/", "token", "tokenmm5", 30000, 3000)
        ,sr);//new int[]{0,1,1,2,2,2,3,3,3,3,4,4,4,4,4,5,5,5,5,5,5,6,6,6,6,6,6,6,7,7,7,7,7,7,7,7,8,8,8,8,8,8,8,8,8};
        String result = Mmcinq.generateCodeFromTable(t);
        System.out.println("Printint result : "+result);
        Assertions.assertEquals(t,result);
    }
/*
    @Test
    public void sortNumbersInCode(int[] resfindNumbersInCode){
        fail("test sort number in code not implemented yet");
    }
*/
}
