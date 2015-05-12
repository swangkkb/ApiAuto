package org.kkb.server.api.restAssured.cmsAdmin;

import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

/**
 * ws.wang
 *
 * 查询 管理员
 */
@Test
public class AdminSelectTest {
    private static final String token = TestConfig.getToken("/kauth/authorize?uid=239&cid=www&tenant_id=0");//9d1dc7e9d558677630fa610652708de3

    //无效token
    public void testWithErrorToken(){
        Response response2 = TestConfig.getOrDeleteExecu("get", "/admin_users?access_token=adasdfa234234");
        response2.then().
                assertThat().statusCode(401).
                body("message", Matchers.equalTo("无效的access_token"));

    }
    //缺少token
    public void testWithNoToken(){
        Response response1 = TestConfig.getOrDeleteExecu("get", "/admin_users");
        response1.then().
                assertThat().statusCode(400).
                body("message", Matchers.equalTo("请输入access_token"));
    }
    //缺少权限
   /* public void testWithQx(){
        Response response3 = TestConfig.getOrDeleteExecu("get", "/admin_users?access_token=" + TestConfig.getToken("/kauth/authorize?uid=239&cid=www&tenant_id=0"));
        response3.then().
                assertThat().statusCode(200).
                body("data.type", Matchers.hasItems("SystemAdmin", "TenantAdmin")).
                body("data.user_username", Matchers.hasItems("swangtest3"));
    }*/
    //成功查找
    public void testSuc(){
        Response response3 = TestConfig.getOrDeleteExecu("get", "/admin_users?access_token=" + token);
        response3.then().
                assertThat().statusCode(200).
                body("data.type", Matchers.hasItems("SystemAdmin", "TenantAdmin")).
                body("data.user_username", Matchers.hasItems("swangtest3"));
    }
    //根据uid查找管理员信息
    public void test() {
        String userId=String.valueOf(AdminAddTest.userId);
        Response response1 = TestConfig.getOrDeleteExecu("get", "/admin_users/0");
        response1.then().
                assertThat().statusCode(400).
                body("message", Matchers.equalTo("请输入access_token"));
        Response response2 = TestConfig.getOrDeleteExecu("get", "/admin_users/" + userId + "?access_token=" + token);
        response2.then().
                assertThat().statusCode(200).body("data.user_username", Matchers.is("swangtest3"));
    }

}
