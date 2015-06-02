package org.kkb.server.api.restAssured.cmsTenants.tenantsAdminUsers;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.path.json.JsonPath.with;

/**
 * ws.wang
 * 增加租户管理员
 */
public class TenantsAdminUsersAddTest {
    private static final String token = TestConfig.getToken("kauth/authorize?uid=239&cid=www&tenant_id=0");//9d1dc7e9d558677630fa610652708de3
    public static    int tenantsAdminUserId;
    public static   int userId;

    //token失效
    @Test
    public void testNoToken(){
        String str1 = "{\"user_email\":\"swangtest1@163.com\",\"access_token\":\"7f005b1d905f41eb0705ad2e\",\"role_ids\":[1,2]}";
        JSONObject jsonObject1 = JSONObject.fromObject(str1);
        Response response1 = TestConfig.postOrPutExecu("post", "/tenants/1/tenant_admins", jsonObject1);
        response1.then().
                assertThat().statusCode(401);
    }
    //缺少参数
    @Test
    public  void  testNoParam(){
        String str2 = "{\"access_token\":\""+token+"\",\"role_ids\":[1,2]}";
        JSONObject jsonObject2 = JSONObject.fromObject(str2);
        Response response2 = TestConfig.postOrPutExecu("post", "/tenants/1/tenant_admins", jsonObject2);
        response2.then().
                assertThat().statusCode(400).body("message", Matchers.is("请完善用户唯一标示/access_token"));
    }
    //新建成功，没有角色
    @Test
    public void testSuc(){
        String str3 = "{\"user_email\":\"swangtest1@163.com\",\"access_token\":\""+token+"\"}";
        JSONObject jsonObject3 = JSONObject.fromObject(str3);
        Response response3 = TestConfig.postOrPutExecu("post", "/tenants/1/tenant_admins", jsonObject3);
        response3.then().
                assertThat().statusCode(200).body("message", Matchers.equalTo("success"));
               // body("data.user_username", Matchers.equalTo("swangTest1")).
                //body("data.user_name", Matchers.equalTo("王爽"));


        tenantsAdminUserId = with(response3.body().asString()).get("data.id");
        userId=with(response3.body().asString()).get("data.user_id");
    }
}
