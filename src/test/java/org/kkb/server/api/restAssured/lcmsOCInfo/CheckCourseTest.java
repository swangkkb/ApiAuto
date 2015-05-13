package org.kkb.server.api.restAssured.lcmsOCInfo;

import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

/**
 * ws.wang
 *
 * 检查班次中是否包含傲思资源媒体
 */
@Test
public class CheckCourseTest {
    public void testWithNoParam(){
        Response response= TestConfig.getOrDeleteExecu("get", "/oc_infos/check_course/");
        response.then().
                assertThat().statusCode(404);

    }
    public void testWithNoCourse(){
        Response response= TestConfig.getOrDeleteExecu("get", "/oc_infos/check_course/0");
        response.then().
                assertThat().statusCode(200).body("message",Matchers.equalTo("not exist oc_content chapter"));

    }
    public void testSuc(){
        Response response= TestConfig.getOrDeleteExecu("get", "/oc_infos/check_course/611");
        response.then().
                assertThat().statusCode(200).body("message",Matchers.equalTo("exist oc_content chapter"));
    }



}
