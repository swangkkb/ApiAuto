package org.kkb.server.api.restAssured.microMajots;

import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

/**
 * 根据 微专业id 获取微专业详情
 * ws.wang
 */
@Test
public class MicroMajotsTest {
    public void testSuc(){
        Response response= TestConfig.getOrDeleteExecu("get", "/micro_major/2");
        response.then().
                assertThat().statusCode(200).
                body("data.title", Matchers.is("Android移动开发")).
                body("data.promotional_video_url",Matchers.notNullValue()).
                body("data.Background_image_url",Matchers.notNullValue()).
                body("data.video_image_url", Matchers.notNullValue());
    }
    public void testError(){
        Response response= TestConfig.getOrDeleteExecu("get", "/micro_major/0");
        response.then().
                assertThat().statusCode(200).body("message",Matchers.equalTo("ID输入有误"));
    }
}
