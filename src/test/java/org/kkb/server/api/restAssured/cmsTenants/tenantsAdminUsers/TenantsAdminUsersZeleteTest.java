package org.kkb.server.api.restAssured.cmsTenants.tenantsAdminUsers;

import com.jayway.restassured.response.Response;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static org.hamcrest.Matchers.equalTo;

/**
 * ws.wang
 * 删除租户管理员
 */
public class TenantsAdminUsersZeleteTest {
    private static final String token = TestConfig.getToken("kauth/authorize?uid=239&cid=www&tenant_id=0");//9d1dc7e9d558677630fa610652708de3
    //token 失效
    @Test
    public void testWithToken(){
        int tenantsAdminUserId=TenantsAdminUsersAddTest.tenantsAdminUserId;
        Response response = TestConfig.getOrDeleteExecu("delete", "tenants/1/tenant_admins/" + tenantsAdminUserId + "?access_token=6044e1f0adfc2001194c");
        response.then().
                assertThat().statusCode(401);
    }

    //正常删除
    @Test
    public void testZuc() throws IllegalAccessException, InstantiationException {
        int tenantsAdminUserId=TenantsAdminUsersAddTest.tenantsAdminUserId;
        Response response1 = TestConfig.getOrDeleteExecu("delete", "tenants/1/tenant_admins/" + tenantsAdminUserId + "?access_token="+token);
        response1.then().
                assertThat().statusCode(200).
                body("message", equalTo("success"));
    }

}
