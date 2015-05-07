package org.kkb.server.api.restAssured;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import net.sf.json.JSONObject;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import static com.jayway.restassured.path.json.JsonPath.with;
import static org.hamcrest.Matchers.*;

/**
 * ws.wang
 * <p/>
 * 多租户
 */
public class Cms_TenantsTest {
    //6044e1f0adfc2001194c2088d98964fa
    private static final String token = TestConfig.getToken("kauth/authorize?uid=239&cid=www&tenant_id=0");
    private String userId;
    private String tenantsAdminUserId;

    /**
     * 获取租户管理员
     * class：Cms_TenantResource:getTenantAdminUser
     */
    @Test
    public void testAdminUser() {
        Response response = TestConfig.getOrDeleteExecu("get", "tenants/1/tenant_admins?access_token=6044e1f0adfc2001194c");
        response.then().
                assertThat().statusCode(401).
                body("message", equalTo("无效的access_token"));

        Response response1 = TestConfig.getOrDeleteExecu("get", "tenants/1/tenant_admins");
        response1.then().
                assertThat().statusCode(400).
                body("message", equalTo("请输入access_token"));

        Response response2 = TestConfig.getOrDeleteExecu("get", "tenants/1/tenant_admins?access_token=" + token);
        response2.then().
                assertThat().statusCode(200).
                body("message", equalTo("success")).
                body("data.user_name", hasItems("王成刚"));

    }

    /**
     * 获取租户角色
     * class:Cms_TenantResource.queryTenantsRoles
     */
    @Test
    public void testTenantsRoles() {
        Response response = TestConfig.getOrDeleteExecu("get", "tenants/1/tenants_roles?access_token=6044e1f0adfc2001194c");
        response.then().
                assertThat().statusCode(401).
                body("message", equalTo("无效的access_token"));

        Response response1 = TestConfig.getOrDeleteExecu("get", "tenants/1/tenants_roles");
        response1.then().
                assertThat().statusCode(400).
                body("message", equalTo("请输入access_token"));

        Response response2 = TestConfig.getOrDeleteExecu("get", "tenants/1/tenants_roles?access_token=" + token);
        response2.then().log().all().
                assertThat().statusCode(200).
                body("message", equalTo("success")).
                body("data.name", hasItems("内容管理"));
        // body("data.name.tenants_permission_functions.name", hasItems("内容管理"));

    }

    /**
     * 获取租户角色的功能权限
     * class:Cms_TenantResource.getTenantsRoleById
     */
    @Test
    public void testTenantsRoleById() {
        Response response = TestConfig.getOrDeleteExecu("get", "tenants/1/tenants_roles/1?access_token=6044e1f0adfc2001194c");
        response.then().
                assertThat().statusCode(401).
                body("message", equalTo("无效的access_token"));

        Response response1 = TestConfig.getOrDeleteExecu("get", "tenants/1/tenants_roles/1");
        response1.then().
                assertThat().statusCode(400).
                body("message", equalTo("请输入access_token"));

        Response response2 = TestConfig.getOrDeleteExecu("get", "tenants/1/tenants_roles/20000?access_token=" + token);
        response2.then().
                assertThat().statusCode(200).
                body("message", equalTo("success"));
        //body("data",isEmptyString());

        Response response3 = TestConfig.getOrDeleteExecu("get", "tenants/1/tenants_roles/1?access_token=" + token);
        response3.then().
                assertThat().statusCode(200).
                body("message", equalTo("success")).
                body("data.name", is("运营人员"));
    }

    /**
     * 获取租户
     * class:Cms_TenantResource.getTenants
     */
    @Test
    public void testGetTenants() {
        Response response = TestConfig.getOrDeleteExecu("get", "tenants?access_token=6044e1f0adfc2001194c");
        response.then().
                assertThat().statusCode(401).
                body("message", equalTo("无效的access_token"));

        Response response1 = TestConfig.getOrDeleteExecu("get", "tenants");
        response1.then().
                assertThat().statusCode(400).
                body("message", equalTo("请输入access_token"));

        Response response2 = TestConfig.getOrDeleteExecu("get", "tenants?access_token=6044e1f0adfc2001194c2088d98964fa");
        response2.then().
                assertThat().statusCode(200).
                body("message", equalTo("success")).
                body("total", is(240));
    }

    /**
     * 获取租户课程
     * class :Cms_TenantResource.getTenantAdminUser
     */
    @Test
    public void testGetTenantsCourses() {
        Response response = TestConfig.getOrDeleteExecu("get", "tenants/1/courses?access_token=6044e1f0adfc2001194c");
        response.then().
                assertThat().statusCode(401).
                body("message", equalTo("无效的access_token"));

        Response response1 = TestConfig.getOrDeleteExecu("get", "tenants/1/courses");
        response1.then().
                assertThat().statusCode(400).
                body("message", equalTo("请输入access_token"));

        Response response2 = TestConfig.getOrDeleteExecu("get", "tenants/1/courses?access_token=6044e1f0adfc2001194c2088d98964fa");
        response2.then().
                assertThat().statusCode(200).
                body("message", equalTo("success")).
                body("data.cms_courses.name", Matchers.hasItems("淘宝直通车系统化实战")).
                body("data.lcms_courses.name", Matchers.hasItems("响应式Web设计")).
                body("data.tenant_sale_courses.name", Matchers.hasItems("响应式Web设计"));

    }

    /**
     * 获取租户的功能菜单权限
     * class :Cms_TenantResource.tenants_permission_functions
     */
    @Test
    public void testGetTenantsPermissionFunctions() {
        Response response = TestConfig.getOrDeleteExecu("get", "tenants/1/tenants_permission_functions?access_token=6044e1f0adfc2001194c");
        response.then().
                assertThat().statusCode(401).
                body("message", equalTo("无效的access_token"));

        Response response1 = TestConfig.getOrDeleteExecu("get", "tenants/1/tenants_permission_functions");
        response1.then().
                assertThat().statusCode(400).
                body("message", equalTo("请输入access_token"));

        Response response2 = TestConfig.getOrDeleteExecu("get", "tenants/1/tenants_permission_functions?access_token=6044e1f0adfc2001194c2088d98964fa");
        response2.then().
                assertThat().statusCode(200).
                body("message", equalTo("success")).
                body("data.name", Matchers.hasItems("内容管理", "课程建设", "数据分析", "数据分析"));
    }


    /********************************************租户管理员***************************************************************************/

    /**
     * 添加租户管理员
     * class :Cms_TenantResource.insertTenantAdminUser
     */
    //@Test
    public void testAddTenantsAdminUser() {
        //token失效
        String str1 = "{\"user_email\":\"swangtest1@163.com\",\"access_token\":\"7f005b1f6b2cd905f41eb0705ad2e\",\"role_ids\":[1,2]}";
        JSONObject jsonObject1 = JSONObject.fromObject(str1);
        Response response1 = TestConfig.postOrPutExecu("post", "/tenants/1/tenant_admins", jsonObject1);
        response1.then().
                assertThat().statusCode(401);

        //user_email 缺少
        String str2 = "{\"access_token\":\"7f005b1f6b2cd905f41eb0705ad2e\",\"role_ids\":[1,2]}";
        JSONObject jsonObject2 = JSONObject.fromObject(str2);
        Response response2 = TestConfig.postOrPutExecu("post", "/tenants/1/tenant_admins", jsonObject2);
        response2.then().
                assertThat().statusCode(400).body("message", Matchers.is("请完善用户唯一标示/access_token"));

        //正常插入，没有角色
        String str3 = "{\"user_email\":\"swangtest1@163.com\",\"access_token\":\"7f005b1f6b2cd905f41eb0705ad2e\"}";
        JSONObject jsonObject3 = JSONObject.fromObject(str3);
        Response response3 = TestConfig.postOrPutExecu("post", "/tenants/1/tenant_admins", jsonObject3);
        response3.then().
                assertThat().statusCode(200).body("message", Matchers.is("success")).
                body("data.user_username", Matchers.is("lswaixz")).
                body("data.user_name", Matchers.is("王爽"));


        tenantsAdminUserId = with(response3.body().asString()).get("data.id");
    }

    /**
     * 修改添加的 租户管理员
     * class :Cms_TenantResource.updateTenantAdminUser 394
     */
   // @Test
    public void testUpdateTenantAdminUser() {
        String str1 = "{\"user_email\":\"swangtest1@163.com\",\"access_token\":\"7f005b1f6b2cd905f41eb0705ad2e\",\"role_ids\":[1,2]}";
        JSONObject jsonObject1 = JSONObject.fromObject(str1);
        Response response1 = TestConfig.postOrPutExecu("post", "/tenants/1/tenant_admins", jsonObject1);
        response1.then().
                assertThat().statusCode(401);
    }

    /**
     *删除 租户管理员
     * class :Cms_TenantResource.deleteTenantAdminUser 394
     * */
    @Test
    public void testDeleteTenantsAdminUsers(){
        Response response = TestConfig.getOrDeleteExecu("delete", "tenants/1/tenant_admins/" + tenantsAdminUserId + "?access_token=6044e1f0adfc2001194c");
        response.then().
                assertThat().statusCode(401);

        Response response1 = TestConfig.getOrDeleteExecu("delete", "tenants/1/tenant_admins/" + tenantsAdminUserId + "?access_token="+token);
        response1.then().
                assertThat().statusCode(200).
                body("message", equalTo("success"));
    }


    /**
     *增加  租户角色
     * class :Cms_TenantResource.createTenantsRoles
     * */
    @Test
    public void testAddTenantsRoles(){

    }
  }
