package org.kkb.server.api.restAssured.cmsAdmin;

import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;



/**
 * ws.wang
 *
 * 删除 管理员
 */
@Test
public class AdminZeleteTest {
    private static final String token = TestConfig.getToken("/kauth/authorize?uid=239&cid=www&tenant_id=0");//9d1dc7e9d558677630fa610652708de3

    //没有token
    public  void testWithNoToken(){
        //没有输入token
        Response response1 = TestConfig.getOrDeleteExecu("delete", "/admin_users/0");
        response1.then().
                assertThat().statusCode(400).
                body("message", Matchers.equalTo("请输入access_token"));
    }
    //token 没有权限
    public void testWithuthority(){
        String userId=String.valueOf(AdminAddTest.userId);
        Response response2 = TestConfig.getOrDeleteExecu("delete", "/admin_users/" + userId + "?access_token=asdfasdf13452fasdf");
        response2.then().
                assertThat().statusCode(401);
    }
    //删除成功
    public void testSuc(){
        String userId=String.valueOf(AdminAddTest.userId);
        Response response2 = TestConfig.getOrDeleteExecu("delete", "/admin_users/" + userId + "?access_token=" + token);
        response2.then().
                assertThat().statusCode(200).
                body("message", Matchers.equalTo("success"));
    }


}
