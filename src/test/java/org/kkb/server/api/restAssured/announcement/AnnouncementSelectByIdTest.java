package org.kkb.server.api.restAssured.announcement;

import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

/**
 * ws.wang
 * 根据学校的ID获取上线公告的前三条
 */
@Test
public class AnnouncementSelectByIdTest {
    //没有传递id参数
    public void testWithNoSchool(){
        Response response = TestConfig.getOrDeleteExecu("get", "/school_announcements/0");
        response.then().
                assertThat().statusCode(200).
                body("data.message", Matchers.equalTo("暂无该学校信息或改公告没有上线"));
    }
    //告学校没有公告
    public void testSuc(){
        Response response = TestConfig.getOrDeleteExecu("get", "/school_announcements/217");
        response.then().
                assertThat().statusCode(200).
                body("data.size()", Matchers.lessThan(3));
    }
    //缺少参数
    public void testWithNoParam(){
        Response response = TestConfig.getOrDeleteExecu("get", "/school_announcements");
        response.then().
                assertThat().statusCode(405);
    }
}
