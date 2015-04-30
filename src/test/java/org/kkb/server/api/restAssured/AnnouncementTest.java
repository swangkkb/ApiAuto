package org.kkb.server.api.restAssured;

import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * ws.wang
 */
public class AnnouncementTest {
    @Test
    public void testGetAnnouncement(){
        //测试announcementId不存在
        Response response= TestConfig.getOrDeleteExecu("get","/announcements/1000000");
        response.then().
                assertThat().statusCode(400).
                body("message",equalTo("操作失败"));

        Response response1=TestConfig.getOrDeleteExecu("get","/announcements/12");
        response1.then().
                assertThat().statusCode(200).
                body("title",Matchers.notNullValue());

    }
}
