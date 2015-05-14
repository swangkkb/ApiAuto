package org.kkb.server.api.restAssured.cmsAdmin;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

/**
 * ws.wang
 *
 * 修改 管理员
 */
@Test
public class AdminUpdateTest {
    private static final String token = TestConfig.getToken("/kauth/authorize?uid=239&cid=www&tenant_id=0");//9d1dc7e9d558677630fa610652708de3

    //token失效
    public void  testWithErrorToken(){
        String userId=String.valueOf(AdminAddTest.userId);
        String str1="{\"user_id\":"+userId+",\"access_token\":\"12412fsdfasdt2446\",\"tenant_ids\":[2,9]}";

        JSONObject jsonObject1 = JSONObject.fromObject(str1);

        Response response1 = TestConfig.postOrPutExecu("put", "/admin_users", jsonObject1);
        response1.then().log().all().
                assertThat().statusCode(401);
    }
    //token 缺失
    public void testWithNoToken(){
        String userId=String.valueOf(AdminAddTest.userId);
        String str1="{\"user_id\":"+userId+",\"tenant_ids\":[2,9]}";

        JSONObject jsonObject1 = JSONObject.fromObject(str1);

        Response response1 = TestConfig.postOrPutExecu("put", "/admin_users", jsonObject1);
        response1.then().log().all().
                assertThat().statusCode(400);
    }

    public  void testSuc(){
        String userId=String.valueOf(AdminAddTest.userId);
        String str1="{\"user_id\":"+userId+",\"access_token\":\""+token+"\",\"tenant_ids\":[2,9]}";

        JSONObject jsonObject1 = JSONObject.fromObject(str1);

        Response response1 = TestConfig.postOrPutExecu("put", "/admin_users", jsonObject1);
        response1.then().log().all().
                assertThat().statusCode(200).
                body("data.tenants.name", Matchers.hasItems("广东第二师范学院"));
    }
}
