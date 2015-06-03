package org.kkb.server.api.restAssured.courseVersion;

import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;


/**
 *获取模板课或是班次的版本
 * ws.wang
 */
@Test
public class GetCourseVsersionsTest {
    //token 丢失
    public void testWithNoToken(){
        Response response= TestConfig.getOrDeleteExecu("get", "/course_versions?type=InstructiveCourse");
        response.then().
                assertThat().statusCode(400);
    }
    //token 失效
    public  void testWithErrorToken(){
        Response response= TestConfig.getOrDeleteExecu("get", "/course_versions?access_token=56789klkjhgfghjkt&type=InstructiveCourse");
        response.then().
                assertThat().statusCode(401);
    }
    //正确查找--模板课
    public void testSucInstructiveCourse(){
        String token=TestConfig.getToken("/kauth/authorize?uid=812277&cid=www&tenant_id=1");
        Response response= TestConfig.getOrDeleteExecu("get", "/course_versions?access_token="+token+"&type=InstructiveCourse");
        response.then().
                assertThat().statusCode(200).
                body("data",Matchers.notNullValue()).
                body("data.size()",Matchers.is(104));
    }
    //正确查找--班次
    public void testSucCourseClass(){
        String token=TestConfig.getToken("/kauth/authorize?uid=812277&cid=www&tenant_id=1");
        Response response= TestConfig.getOrDeleteExecu("get", "/course_versions?access_token="+token+"&type=CourseClass");
        response.then().
                assertThat().statusCode(200).
                body("data",Matchers.notNullValue());
    }
}
