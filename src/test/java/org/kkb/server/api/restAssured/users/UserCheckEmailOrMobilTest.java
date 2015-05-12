package org.kkb.server.api.restAssured.users;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

/**
 * ws.wang
 * 邮箱手机号验证
 */
@Test
public class UserCheckEmailOrMobilTest {

    //body 参数=null
    public void testWithPramNull(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("email_or_mobile","");
        Response response = TestConfig.postOrPutExecu("post", "/users/check_email_or_mobile_unique", jsonObject);
        response.then().
                assertThat().statusCode(400).
                body("message", Matchers.equalTo("email_or_mobile can not empty"));
    }
    //已经被添加--邮箱异常
    public void testWithErrorEmail(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("email_or_mobile","835721919@qq.com");

        Response response = TestConfig.postOrPutExecu("post", "/users/check_email_or_mobile_unique", jsonObject);
        response.then().
                assertThat().statusCode(200).
                body("message", Matchers.equalTo("验证失败，该账号已经被添加"));
    }
    //已经被添加--手机号
    public void  testWithErrorMobile(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("email_or_mobile","18801308506");

        Response response = TestConfig.postOrPutExecu("post", "/users/check_email_or_mobile_unique", jsonObject);
        response.then().
                assertThat().statusCode(200).
                body("message", Matchers.equalTo("验证失败，该账号不存在"));
    }
    //新的手机
    public void testNewMobile(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("email_or_mobile","18801308505");
        Response response = TestConfig.postOrPutExecu("post", "/users/check_email_or_mobile_unique", jsonObject);
        response.then().
                assertThat().statusCode(200).
                body("message", Matchers.equalTo("验证成功"));
    }
    //新的邮箱
    public void testNewEmail(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("email_or_mobile","swangtest7@163.com");

        Response response = TestConfig.postOrPutExecu("post", "/users/check_email_or_mobile_unique", jsonObject);
        response.then().
                assertThat().statusCode(200).
                body("message", Matchers.equalTo("验证成功"));
    }



}
