package org.kkb.server.api.restAssured.announcement;

import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;


/**
 * ws.wang
 * 根据id获取公告详情
 */
@Test
public class AnnouncementGetInfoByIdTest {
    //参数不正确
    public void testWithErrorParam(){
        Response response = TestConfig.getOrDeleteExecu("get", "/school_announcements/notice/");
        response.then().
                assertThat().statusCode(404);
    }
    //争取查找
    public void testSuc(){
        Response response = TestConfig.getOrDeleteExecu("get", "/school_announcements/notice/44");
        response.then().
                assertThat().statusCode(200).
                body("data.viewable_type", Matchers.equalTo("Announcement"));
    }
}
