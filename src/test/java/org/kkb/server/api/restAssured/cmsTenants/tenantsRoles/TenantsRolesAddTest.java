package org.kkb.server.api.restAssured.cmsTenants.tenantsRoles;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import static com.jayway.restassured.path.json.JsonPath.with;


/**
 * ws.wang
 * 添加租户角色
 */
@Test
public class TenantsRolesAddTest {
    private static final String token = TestConfig.getToken("kauth/authorize?uid=239&cid=www&tenant_id=0");//9d1dc7e9d558677630fa610652708de3
    public static int rolesId;

    /**
     * 没有token
     */

    public void testNotoken() {
        String str = "{\"name\":\"wsTestAddRoles\",\"tenants_permission_function_ids\":[2]}";
        JSONObject jsonObject = JSONObject.fromObject(str);
        Response response = TestConfig.postOrPutExecu("post", "/tenants/1/tenants_roles", jsonObject);
        response.then().
                assertThat().statusCode(400).body("message", Matchers.is("请完善name/tenants_permission_function_ids/access_token"));

    }

    /**
     * token失效
     */
    public void testErrorToken() {
        String str = "{\"access_token\":\"9d1dc7e9d558677630fa610652708\",\"name\":\"wsTestAddRoles\",\"tenants_permission_function_ids\":[2]}";
        JSONObject jsonObject = JSONObject.fromObject(str);
        Response response = TestConfig.postOrPutExecu("post", "/tenants/1/tenants_roles", jsonObject);
        response.then().
                assertThat().statusCode(401).body("message", Matchers.is("无效的access_token"));

    }

    /**
     * 参数传递--name
     */
    public void testParamWithName() {
        String str = "{\"access_token\":\"" + token + "\",\"tenants_permission_function_ids\":[2]}";
        JSONObject jsonObject = JSONObject.fromObject(str);
        Response response = TestConfig.postOrPutExecu("post", "/tenants/1/tenants_roles", jsonObject);
        response.then().
                assertThat().statusCode(400).body("message", Matchers.is("请完善name/tenants_permission_function_ids/access_token"));
    }

    /**
     * 参数传递--tenants_permission_function_ids
     */
    public void testParamWithPF() {
        String str = "{\"access_token\":\"" + token + "\",\"name\":\"wsTestAddRoles\"}";
        JSONObject jsonObject = JSONObject.fromObject(str);
        Response response = TestConfig.postOrPutExecu("post", "/tenants/1/tenants_roles", jsonObject);
        response.then().
                assertThat().statusCode(400).body("message", Matchers.is("请完善name/tenants_permission_function_ids/access_token"));
    }

    /**
     * 参数传递--没有该功能
     * 插入成功
     */
    public void testParamErrorPF() {
        String str = "{\"access_token\":\"" + token + "\",\"name\":\"wsTestAddRoles\",\"tenants_permission_function_ids\":[300000000000000]}";
        JSONObject jsonObject = JSONObject.fromObject(str);
        Response response = TestConfig.postOrPutExecu("post", "/tenants/1/tenants_roles", jsonObject);
        response.then().
                assertThat().statusCode(200).body("data.tenants_permission_functions", Matchers.empty()).body("data.name", Matchers.is("wsTestAddRoles"));

        rolesId = with(response.body().asString()).get("data.id");
    }
}
