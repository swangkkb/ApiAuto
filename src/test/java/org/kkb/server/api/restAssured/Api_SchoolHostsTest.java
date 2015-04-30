package org.kkb.server.api.restAssured;

import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * ws.wang
 *
 */
public class Api_SchoolHostsTest {
   @Test
    public void testSchoolHost(){
       Response response= TestConfig.getOrDeleteExecu("get","/school_hosts/buaa");
       response.then().
               assertThat().statusCode(200).
               body("host_url",equalTo("http://192.168.30.67"));

       Response response1=TestConfig.getOrDeleteExecu("get","/school_hosts/buaaa");
       response1.then().
               assertThat().statusCode(400).
               body("message", Matchers.is("暂无信息"));
   }

}
