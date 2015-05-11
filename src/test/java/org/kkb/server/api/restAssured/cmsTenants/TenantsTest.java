package org.kkb.server.api.restAssured.cmsTenants;

import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * ws.wang
 *
 * 获取 租户
 */
@Test
public class TenantsTest {
    private static final String token = TestConfig.getToken("kauth/authorize?uid=239&cid=www&tenant_id=0");

    //无效的token
    public void  testWithErrorToken(){
        Response response = TestConfig.getOrDeleteExecu("get", "tenants?access_token=6044e1f0adfc2001194c");
        response.then().
                assertThat().statusCode(401).
                body("message", equalTo("无效的access_token"));
    }
    //参数确实
    public  void testWithNoParam(){
        Response response1 = TestConfig.getOrDeleteExecu("get", "tenants");
        response1.then().
                assertThat().statusCode(400).
                body("message", equalTo("请输入access_token"));
    }
    //正确获取
    public void testSuc(){
        Response response2 = TestConfig.getOrDeleteExecu("get", "tenants?access_token="+token);
        response2.then().
                assertThat().statusCode(200).
                body("message", equalTo("success")).
                body("data.name", Matchers.hasItems("内部系统","北京工商大学嘉华学院","莱芜职业技术学院"));
    }

}
