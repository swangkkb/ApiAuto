package org.kkb.server.api.restAssured.course;

import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * 获取用户已结课的课程信息接口
 * ws.wang
 */
@Test
public class ConcludedEnrollCourseInfoTest {
    //缺少用户名
    public void testWithNoName(){
        Response response= TestConfig.getOrDeleteExecu("get", "/courses/concluded_enrollment_courses?password=123456");
        response.then().
                assertThat().statusCode(400).
                body("message",equalTo("用户名不能为空"));
    }
    //缺少密码
    public void testWithNoPass(){
        Response response= TestConfig.getOrDeleteExecu("get", "/courses/concluded_enrollment_courses?username=swangTest1");
        response.then().
                assertThat().statusCode(400).
                body("message",equalTo("密码不能为空"));
    }
    //用户名和密码不对应
    public void testWithErrorParam(){
        Response response= TestConfig.getOrDeleteExecu("get", "/courses/concluded_enrollment_courses?username=swangTest&password=123456");
        response.then().assertThat().statusCode(400).
                body("message",equalTo("用户名或密码错误"));
    }
    //正确获取
    public void testSuc(){
        Response response= TestConfig.getOrDeleteExecu("get", "/courses/concluded_enrollment_courses?username=swangTest1&password=123456");
        response.then().assertThat().statusCode(200).
                body("name", Matchers.hasItems("互联网营销概论", "网站分析工具"));
    }

}
