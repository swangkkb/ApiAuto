package org.kkb.server.api.restAssured.cmsTenants;

import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 *
 * ws.wang
 *
 * 获取 角色的功能列表
 */
@Test
public class TenantsRolesPerFunctionTest {
    private static final String token = TestConfig.getToken("kauth/authorize?uid=239&cid=www&tenant_id=0");

    //无效token
    public void testWithNoToken(){
        Response response = TestConfig.getOrDeleteExecu("get", "tenants/1/tenants_roles/1?access_token=6044e1f0adfc2001194c");
        response.then().
                assertThat().statusCode(401).
                body("message", equalTo("无效的access_token"));
    }
    //缺少token
    public void testWithNoParam(){
        Response response1 = TestConfig.getOrDeleteExecu("get", "tenants/1/tenants_roles/1");
        response1.then().
                assertThat().statusCode(400).
                body("message", equalTo("请输入access_token"));
    }
    //正常获取
    public void testSuc(){
        Response response3 = TestConfig.getOrDeleteExecu("get", "tenants/1/tenants_roles/100?access_token=" + token);
        response3.then().
                assertThat().statusCode(200).
                body("message", equalTo("success")).
                body("data.name", Matchers.notNullValue());
    }
}
