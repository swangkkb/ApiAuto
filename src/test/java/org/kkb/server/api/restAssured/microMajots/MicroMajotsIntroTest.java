package org.kkb.server.api.restAssured.microMajots;

import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;


/**
 * ws.wang
 * 获取学校微专业 简介
 */
@Test
public class MicroMajotsIntroTest {
    public void testSuc(){
        Response response= TestConfig.getOrDeleteExecu("get", "/micro_major/intro?school_id=227");
        response.then().
                assertThat().statusCode(200).
                body("data.micro_majors.short_name.get(0)", Matchers.is("互联网营销"));
    }
    public void testError(){
        Response response= TestConfig.getOrDeleteExecu("get", "/micro_major/intro?school_id=0");
        response.then().
                assertThat().statusCode(200).
                body("message", Matchers.equalTo("暂无该学校信息"));
    }

}
