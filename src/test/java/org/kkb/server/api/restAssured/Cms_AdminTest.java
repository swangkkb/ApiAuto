package org.kkb.server.api.restAssured;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import static com.jayway.restassured.path.json.JsonPath.with;

/**
 * ws.wang
 */
public class Cms_AdminTest {
    private static final String token=TestConfig.getToken("/kauth/authorize?uid=239&cid=www&tenant_id=0");
    private String userId;
    /**
     * 创建管理员
     * */
    @Test
    public void testAddAdminUsers(){
        //token无权限
        String str1="{\"user_email\":\"swangtest3@163.com\",\"type\":\"SystemAdmin\",\"access_token\":\"26ff6a00c9a6fcdb67db048fb164184a\",\"sub_type\":\"admin\",\"tenant_ids\":[1,2]}";
        JSONObject jsonObject1=JSONObject.fromObject(str1);

        Response response1= TestConfig.postOrPutExecu("post", "/admin_users", jsonObject1);
        response1.then().
                assertThat().statusCode(200).
                body("message", Matchers.equalTo("对不起，您不具有相应权限"));

        //缺少参数
        String str2="{\"user_email\":\"swangtest3@163.com\",\"type\":\"SystemAdmin\",\"access_token\":\"26ff6a00c9a6fcdb67db048fb164184a\",\"sub_type\":\"admin\"}";
        JSONObject jsonObject2=JSONObject.fromObject(str2);
        Response response2= TestConfig.postOrPutExecu("post", "/admin_users", jsonObject2);
        response2.then().
                assertThat().statusCode(400).
                body("message", Matchers.equalTo("请输入access_token/user_email/type/sub_type/tenant_ids"));

        //type类型判断
        String str3="{\"user_email\":\"swangtest3@163.com\",\"type\":\"SystemAdmins\",\"access_token\":\"26ff6a00c9a6fcdb67db048fb164184a\",\"sub_type\":\"admin\",\"tenant_ids\":[1,2]}";
        JSONObject jsonObject3=JSONObject.fromObject(str3);
        Response response3= TestConfig.postOrPutExecu("post", "/admin_users", jsonObject3);
        response3.then().
                assertThat().statusCode(400).
                body("message", Matchers.equalTo("管理员类型不合法"));

        //sub_type:类型
        String str4="{\"user_email\":\"swangtest3@163.com\",\"type\":\"SystemAdmin\",\"access_token\":\""+token+"\",\"sub_type\":\"admins\",\"tenant_ids\":[1,2]}";
        JSONObject jsonObject4=JSONObject.fromObject(str4);
        Response response4= TestConfig.postOrPutExecu("post", "/admin_users", jsonObject4);
        response4.then().
                assertThat().statusCode(400).
                body("message", Matchers.equalTo("type和sub_type不一致"));

        String str5="{\"user_email\":\"swangtest3@163.com\",\"type\":\"SystemAdmin\",\"access_token\":\""+token+"\",\"sub_type\":\"admin\",\"tenant_ids\":[1,2]}";
        JSONObject jsonObject5=JSONObject.fromObject(str5);
        Response response5= TestConfig.postOrPutExecu("post", "/admin_users", jsonObject5);
        response5.then().
                assertThat().statusCode(200).
                body("data.user_name", Matchers.equalTo("王爽")).
                body("data.tenants.name", Matchers.hasItems("慧科教育","北京工业职业技术学院"));

         userId=String.valueOf(with(response5.body().asString()).get("data.user_id"));
    }
    @Test
    public void testDeleteAdminUsers(){
        //没有输入token
        Response response1= TestConfig.getOrDeleteExecu("delete", "/admin_users/0");
        response1.then().
                assertThat().statusCode(400).
                body("message", Matchers.equalTo("请输入access_token"));
        //删除
        Response response2= TestConfig.getOrDeleteExecu("delete", "/admin_users/"+userId+"?access_token="+token);
        response2.then().
                assertThat().statusCode(200).
                body("message", Matchers.equalTo("success"));
    }
    @Test
    public void testGetAdminUsers(){
        //没有输入token
        Response response1= TestConfig.getOrDeleteExecu("get", "/admin_users");
        response1.then().
                assertThat().statusCode(400).
                body("message", Matchers.equalTo("请输入access_token"));

        //无效的token
        Response response2= TestConfig.getOrDeleteExecu("get", "/admin_users?access_token=adasdfa234234");
        response2.then().
                assertThat().statusCode(401).
                body("message", Matchers.equalTo("无效的access_token"));

        //普通用户
        Response response3= TestConfig.getOrDeleteExecu("get", "/admin_users?access_token=26ff6a00c9a6fcdb67db048fb164184a");
        response3.then().
                assertThat().statusCode(200).
                body("data.type", Matchers.hasItems("SystemAdmin","TenantAdmin")).
                body("data.sub_type",Matchers.hasItems("super_admin",""));
    }
}
