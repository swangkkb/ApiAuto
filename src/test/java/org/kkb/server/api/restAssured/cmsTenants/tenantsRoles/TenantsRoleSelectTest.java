package org.kkb.server.api.restAssured.cmsTenants.tenantsRoles;

import com.jayway.restassured.response.Response;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

/**
 * Created by pc on 15-5-8.
 */
@Test

public class TenantsRoleSelectTest {
    private static final String token = TestConfig.getToken("kauth/authorize?uid=239&cid=www&tenant_id=0");//9d1dc7e9d558677630fa610652708de3
    //没有token
    public void testNoToken(){
        Response response = TestConfig.getOrDeleteExecu("get", "tenants/1/tenants_roles");
        response.then().
                assertThat().statusCode(400).
                body("message", equalTo("请输入access_token"));
    }
    //无效的token
    public void testErrorToken(){
        Response response = TestConfig.getOrDeleteExecu("get", "tenants/1/tenants_roles?access_token=6044e1f0adfc2001194c");
        response.then().
                assertThat().statusCode(401).
                body("message", equalTo("无效的access_token"));
    }
    //查询成功
    public void testSuc(){
        Response response2 = TestConfig.getOrDeleteExecu("get", "tenants/1/tenants_roles?access_token=" + token);
        response2.then().log().all().
                assertThat().statusCode(200).
                body("message", equalTo("success"));
    }
}
