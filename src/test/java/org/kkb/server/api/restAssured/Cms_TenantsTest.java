package org.kkb.server.api.restAssured;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

/**
 *ws.wang
 *
 * 多租户
 */
public class Cms_TenantsTest {
    //6044e1f0adfc2001194c2088d98964fa
    private static final String token= TestConfig.getToken("kauth/authorize?uid=812277&cid=www&tenant_id=0");
    /**
     * 获取租户管理员
     * class：Cms_TenantResource:getTenantAdminUser
     * */
    @Test
    public void testAdminUser(){
        Response response=TestConfig.getOrDeleteExecu("get","tenants/1/tenant_admins?access_token=6044e1f0adfc2001194c");
        response.then().
                assertThat().statusCode(401).
                body("message",equalTo("无效的access_token"));

        Response response1=TestConfig.getOrDeleteExecu("get","tenants/1/tenant_admins");
        response1.then().
                assertThat().statusCode(400).
                body("message",equalTo("请输入access_token"));

        Response response2=TestConfig.getOrDeleteExecu("get","tenants/1/tenant_admins?access_token="+token);
        response2.then().
                assertThat().statusCode(200).
                body("message",equalTo("success")).
                body("data.user_name", hasItems("王成刚"));

    }
    /**
     * 获取租户角色
     * class:Cms_TenantResource.queryTenantsRoles
     * */
    @Test
    public void testTenantsRoles(){
        Response response=TestConfig.getOrDeleteExecu("get","tenants/1/tenants_roles?access_token=6044e1f0adfc2001194c");
        response.then().
                assertThat().statusCode(401).
                body("message",equalTo("无效的access_token"));

        Response response1=TestConfig.getOrDeleteExecu("get","tenants/1/tenants_roles");
        response1.then().
                assertThat().statusCode(400).
                body("message",equalTo("请输入access_token"));

        Response response2=TestConfig.getOrDeleteExecu("get","tenants/1/tenants_roles?access_token="+token);
        response2.then().log().all().
                assertThat().statusCode(200).
                body("message",equalTo("success")).
                body("data.name",hasItems("内容管理"));
               // body("data.name.tenants_permission_functions.name", hasItems("内容管理"));

    }

    /**
     *获取租户角色的功能权限
     * class:Cms_TenantResource.getTenantsRoleById
     * */
    @Test
    public void testTenantsRoleById(){
        Response response=TestConfig.getOrDeleteExecu("get","tenants/1/tenants_roles/1?access_token=6044e1f0adfc2001194c");
        response.then().
                assertThat().statusCode(401).
                body("message",equalTo("无效的access_token"));

        Response response1=TestConfig.getOrDeleteExecu("get","tenants/1/tenants_roles/1");
        response1.then().
                assertThat().statusCode(400).
                body("message",equalTo("请输入access_token"));

        Response response2=TestConfig.getOrDeleteExecu("get","tenants/1/tenants_roles/20000?access_token="+token);
        response2.then().
                assertThat().statusCode(200).
                body("message",equalTo("success"));
                //body("data",isEmptyString());

        Response response3=TestConfig.getOrDeleteExecu("get","tenants/1/tenants_roles/1?access_token="+token);
        response3.then().
                assertThat().statusCode(200).
                body("message",equalTo("success")).
                body("data.name",is("运营人员"));
    }
    /**
     * 获取租户
     * class:Cms_TenantResource.getTenants
     * */
    @Test
    public void testGetTenants(){
        Response response=TestConfig.getOrDeleteExecu("get","tenants?access_token=6044e1f0adfc2001194c");
        response.then().
                assertThat().statusCode(401).
                body("message",equalTo("无效的access_token"));

        Response response1=TestConfig.getOrDeleteExecu("get","tenants");
        response1.then().
                assertThat().statusCode(400).
                body("message",equalTo("请输入access_token"));

        Response response2=TestConfig.getOrDeleteExecu("get","tenants?access_token=6044e1f0adfc2001194c2088d98964fa");
        response2.then().
                assertThat().statusCode(200).
                body("message",equalTo("success")).
                body("total",is(240));
    }

    /**
     * 获取租户课程
     * class :Cms_TenantResource.getTenantAdminUser
     * */
    @Test
    public void testGetTenantsCourses(){
        Response response=TestConfig.getOrDeleteExecu("get","tenants/1/courses?access_token=6044e1f0adfc2001194c");
        response.then().
                assertThat().statusCode(401).
                body("message",equalTo("无效的access_token"));

        Response response1=TestConfig.getOrDeleteExecu("get","tenants/1/courses");
        response1.then().
                assertThat().statusCode(400).
                body("message",equalTo("请输入access_token"));

        Response response2=TestConfig.getOrDeleteExecu("get","tenants/1/courses?access_token=6044e1f0adfc2001194c2088d98964fa");
        response2.then().
                assertThat().statusCode(200).
                body("message", equalTo("success")).
                body("data.cms_courses.name", Matchers.hasItems("淘宝直通车系统化实战")).
                body("data.lcms_courses.name", Matchers.hasItems("响应式Web设计")).
                body("data.tenant_sale_courses.name", Matchers.hasItems("响应式Web设计"));

    }

    /**
     *获取租户的功能菜单权限
     * class :Cms_TenantResource.tenants_permission_functions
     * */
    @Test
    public void testGetTenantsPermissionFunctions(){
        Response response=TestConfig.getOrDeleteExecu("get","tenants/1/tenants_permission_functions?access_token=6044e1f0adfc2001194c");
        response.then().
                assertThat().statusCode(401).
                body("message",equalTo("无效的access_token"));

        Response response1=TestConfig.getOrDeleteExecu("get","tenants/1/tenants_permission_functions");
        response1.then().
                assertThat().statusCode(400).
                body("message",equalTo("请输入access_token"));

        Response response2=TestConfig.getOrDeleteExecu("get","tenants/1/tenants_permission_functions?access_token=6044e1f0adfc2001194c2088d98964fa");
        response2.then().
                assertThat().statusCode(200).
                body("message", equalTo("success")).
                body("data.name", Matchers.hasItems("内容管理", "课程建设", "数据分析", "数据分析"));
    }



}
