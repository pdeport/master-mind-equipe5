package com.mmfive.test;

import com.mmfive.Apps;
import com.mmfive.api.ApiClient;
import com.mmfive.exceptions.ApiCallFailedException;
import com.mmfive.responses.TestResponse;
import org.junit.Test;

import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;

public class AppsTest {

    static String CODE = "123456789";
    static int MAX_CODE_DIGIT = CODE.length();
    static int MAX_NUMBER = 10; // from 0 to 9

//    @Test
//    public void testFindNumberInCode(){
//        int[] expected = new int[]{0,0,2,1,1,1,2,2,1,2};
//        int[] result = Mmcinq.findNumbersInCode(MAX_NUMBER);
//        assertArrayEquals(expected,result);
//    }

    @Test
    public void replaceCharAtTest(){
        String result = Apps.replaceCharAt("12345",1,(char) 3);
        assertEquals("13345" ,result);
    }

//    @Test
//    public void searchNumberAtIndex() throws ApiCallFailedException, MalformedURLException {
//        assertEquals(2, 2);
//    }
/*
    @Test
    public void generateCodeFromTable(int[] resfindNumbersInCode){
        fail("test generate code from table not implemented yet");
    }

    @Test
    public void sortNumbersInCode(int[] resfindNumbersInCode){
        fail("test sort number in code not implemented yet");
    }
*/
}
