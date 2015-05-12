package org.kkb.server.api.restAssured.cmsTenants.tenantsRoles;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;


/**
 * ws.wang
 * 修改租户角色
 */
@Test
public class TenantsRolesUpdateTest {

    private static final String token = TestConfig.getToken("kauth/authorize?uid=239&cid=www&tenant_id=0");//9d1dc7e9d558677630fa610652708de3
    /**
     * 没有token
     * */

    public void testNotoken(){
        String  rolesId=String.valueOf(TenantsRolesAddTest.rolesId);
        String str="{\"name\":\"wsTestAddRoles\",\"tenants_permission_function_ids\":[2]}";
        JSONObject jsonObject = JSONObject.fromObject(str);
        Response response = TestConfig.postOrPutExecu("put", "/tenants/1/tenants_roles/"+rolesId, jsonObject);
        response.then().
                assertThat().statusCode(400).body("message", Matchers.is("请完善name/tenants_permission_function_ids/access_token"));
    }
    /**
     * token失效
     * */

     public void testErrorToken(){
         String  rolesId=String.valueOf(TenantsRolesAddTest.rolesId);
        String str="{\"access_token\":\"9d1dc7e9d55630fa610652708de3\",\"name\":\"wsTestAddRoles\",\"tenants_permission_function_ids\":[2]}";
        JSONObject jsonObject = JSONObject.fromObject(str);
        Response response = TestConfig.postOrPutExecu("put", "/tenants/1/tenants_roles/"+rolesId, jsonObject);
        response.then().
                assertThat().statusCode(401).body("message", Matchers.is("无效的access_token"));
    }
    /**
     *参数传递--Pf
     * */
    public void testParamWithNoPF(){
        String  rolesId=String.valueOf(TenantsRolesAddTest.rolesId);
        String str="{\"access_token\":\""+token+"\",\"name\":\"wsTestAddRoles\"}";

        JSONObject jsonObject = JSONObject.fromObject(str);
        Response response = TestConfig.postOrPutExecu("put", "/tenants/1/tenants_roles/"+rolesId, jsonObject);
        response.then().
                assertThat().statusCode(400).body("message", Matchers.is("请完善name/tenants_permission_function_ids/access_token"));

    }
    public void testSuc(){
        String  rolesId=String.valueOf(TenantsRolesAddTest.rolesId);
        String str="{\"access_token\":\""+token+"\",\"name\":\"wsTestAddRoles\",\"tenants_permission_function_ids\":[111]}";
        JSONObject jsonObject = JSONObject.fromObject(str);
        Response response = TestConfig.postOrPutExecu("put", "/tenants/1/tenants_roles/"+rolesId, jsonObject);
        response.then().
                assertThat().statusCode(200).body("data.tenants_permission_functions.id", Matchers.hasItems(111));
    }


}
