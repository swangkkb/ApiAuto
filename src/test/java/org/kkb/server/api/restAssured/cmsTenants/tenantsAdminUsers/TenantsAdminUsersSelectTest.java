package org.kkb.server.api.restAssured.cmsTenants.tenantsAdminUsers;

import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestB;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

/**
 * ws.wang
 * 增加租户管理员
 */
public class TenantsAdminUsersSelectTest {
    private static final String token = TestConfig.getToken("kauth/authorize?uid=239&cid=www&tenant_id=0");//9d1dc7e9d558677630fa610652708de3
    //测试token
    @Test
    public void testParamToken(){
        Response response = TestConfig.getOrDeleteExecu("get", "tenants/1/tenant_admins?access_token=6044e1f0adfc2001194c");
        response.then().
                assertThat().statusCode(401).
                body("message", equalTo("无效的access_token"));
    }
    @Test
    public void testSuc(){
        Response response2 = TestConfig.getOrDeleteExecu("get", "tenants/1/tenant_admins?access_token=" + token);
        response2.then().
                assertThat().statusCode(200).
                body("message", equalTo("success")).
                body("data.user_name", Matchers.hasItems("王爽"));
    }
   /* @Test(dataProvider = "tenantAdminUsers", dataProviderClass = TenantsAdminUsersAddTest.class)
    public void BeforClass(Integer n1,Integer n2) {
        tenantsAdminUserId=n1;
        userId=n2;
    }*/

}
