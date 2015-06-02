package org.kkb.server.api.restAssured.announcement;

import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

/**
 * ws.wang
 *
 * 新增公告
 */
@Test
public class AnnouncementAddTest {
    //缺少参数
    public void testWithNoParam(){
        Response response = TestConfig.getOrDeleteExecu("get", "/announcements/");
        response.then().
                assertThat();

    }
    //参数不存在
    public  void testWithErrorParam(){
        Response response = TestConfig.getOrDeleteExecu("get", "/announcements/0");
        response.then().
                assertThat().statusCode(400).body("message",Matchers.equalTo("操作失败"));
    }
    //查找成功
    public void testSuc(){
        Response response = TestConfig.getOrDeleteExecu("get", "/announcements/44");
        response.then().
                assertThat().statusCode(200).
                body("context_type", Matchers.equalTo("Course")).
                body("context_id",Matchers.equalTo(155));
    }
}
