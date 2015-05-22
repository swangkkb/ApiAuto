package org.kkb.server.api.restAssured.course;

import com.jayway.restassured.response.Response;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * 根据课程id获取课程信息
 * ws.wang
 */
@Test
public class GetCourseInfoTest {
    //course id 不存在
    public void testWithNoId(){
        Response response= TestConfig.getOrDeleteExecu("get", "/courses/0/course_intro");
        response.then().
                assertThat().statusCode(400).
                body("message", equalTo("没有这个课程"));
    }
    //导学课
    public void testWithDX(){
        Response response= TestConfig.getOrDeleteExecu("get", "/courses/15/course_intro");
        response.then().
                assertThat().statusCode(200).
                body("course_name",equalTo("联接与跨越")).body("category_name",equalTo("创新创业"));
    }
    //公开课
    public void testWithOpenCourse(){
        Response response= TestConfig.getOrDeleteExecu("get", "/courses/371/course_intro");
        response.then().
                assertThat().statusCode(200).
                body("course_name",equalTo("Excel基础应用轻松学")).body("category_name",equalTo("办公软件"));
    }


}
