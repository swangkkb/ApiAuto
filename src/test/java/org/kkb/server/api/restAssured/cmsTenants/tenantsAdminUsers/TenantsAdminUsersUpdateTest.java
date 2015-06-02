package org.kkb.server.api.restAssured.cmsTenants.tenantsAdminUsers;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;


/**
 * ws.wang
 * 修改租户管理员
 */
public class TenantsAdminUsersUpdateTest {
    private static final String token = TestConfig.getToken("kauth/authorize?uid=239&cid=www&tenant_id=0");//9d1dc7e9d558677630fa610652708de3

    //token
    @Test
    public void testParamWithToken(){
        int tenantsAdminUserId=TenantsAdminUsersAddTest.tenantsAdminUserId;
        int userId=TenantsAdminUsersAddTest.userId;

        String str1 = "{\"user_id\":\""+userId+"\",\"access_token\":\"7f005b1f6b2cd905f41eb0705ad2e\",\"role_ids\":[1,2]}";
        JSONObject jsonObject1 = JSONObject.fromObject(str1);
        Response response1 = TestConfig.postOrPutExecu("put", "/tenants/1/tenant_admins/"+tenantsAdminUserId, jsonObject1);
        response1.then().
                assertThat().statusCode(401);
    }
    //正常修改-添加角色
    @Test
    public void testSuc(){
        int tenantsAdminUserId=TenantsAdminUsersAddTest.tenantsAdminUserId;
        int userId=TenantsAdminUsersAddTest.userId;

        String str1 = "{\"user_id\":\""+userId+"\",\"access_token\":\""+token+"\",\"role_ids\":[95]}";

        JSONObject jsonObject1 = JSONObject.fromObject(str1);
        Response response1 = TestConfig.postOrPutExecu("put", "/tenants/1/tenant_admins/"+tenantsAdminUserId, jsonObject1);
        response1.then().log().all().
                assertThat().statusCode(200).body("data.roles.id", Matchers.hasItems(95));
    }

}
