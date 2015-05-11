package org.kkb.server.api.restAssured.cmsTenants;

import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;


import static org.hamcrest.Matchers.equalTo;

/**
 * ws.wang
 * 测试获取 租户功能权限菜单
 */
@Test
public class TenantsPerFunctionTest {
    //无效的token
    public void testWithErrorToken(){
        Response response = TestConfig.getOrDeleteExecu("get", "tenants/1/tenants_permission_functions?access_token=6044e1f0adfc2001194c");
        response.then().
                assertThat().statusCode(401).
                body("message", equalTo("无效的access_token"));
    }
    //参数不够
    public void testWithNoParam(){
        Response response1 = TestConfig.getOrDeleteExecu("get", "tenants/1/tenants_permission_functions");
        response1.then().
                assertThat().statusCode(400).
                body("message", equalTo("请输入access_token"));
    }

    //god_amdin
    public void testGodAdmin(){
        String token=TestConfig.getToken("kauth/authorize?uid=239&cid=www&tenant_id=0");
        Response response = TestConfig.getOrDeleteExecu("get", "tenants/1/tenants_permission_functions?access_token="+token);
        response.then().
                assertThat().statusCode(200).
                body("data.name", Matchers.hasItems("内容管理", "课程建设", "数据分析", "权限管理")).
                body("data.children.name.get(0)", Matchers.hasItems("高校管理", "课程中心")).
                body("data.children.name.get(1)", Matchers.hasItems("通用库","自建导学课")).
                body("data.children.name.get(2)", Matchers.hasItems("概览")).
                body("data.children.name.get(3)", Matchers.hasItems("租户系统权限"));

    }
    //system_admin（super_admin）
    public void testSysAdmin(){
        String token=TestConfig.getToken("kauth/authorize?uid=798598&cid=www&tenant_id=0");
        Response response = TestConfig.getOrDeleteExecu("get", "tenants/1/tenants_permission_functions?access_token="+token);
        response.then().
                assertThat().statusCode(200).
                body("data.name", Matchers.hasItems("内容管理", "课程建设", "数据分析", "权限管理")).
                body("data.children.name.get(0)", Matchers.hasItems("高校管理", "课程中心")).
                body("data.children.name.get(1)", Matchers.hasItems("通用库","自建导学课")).
                body("data.children.name.get(2)", Matchers.hasItems("概览")).
                body("data.children.name.get(3)", Matchers.hasItems("租户系统权限"));

    }
    //system(admin)
    public void testSuperAdmin(){
        String token=TestConfig.getToken("kauth/authorize?uid=181&cid=www&tenant_id=0");
        Response response = TestConfig.getOrDeleteExecu("get", "tenants/1/tenants_permission_functions?access_token="+token);
        response.then().
                assertThat().statusCode(200).
                body("data.name", Matchers.hasItems("内容管理", "课程建设", "数据分析", "权限管理")).
                body("data.children.name.get(0)", Matchers.hasItems("高校管理", "课程中心")).
                body("data.children.name.get(1)", Matchers.hasItems("通用库","自建导学课")).
                body("data.children.name.get(2)", Matchers.hasItems("概览")).
                body("data.children.name.get(3)", Matchers.hasItems("租户系统权限"));


    }
    //tenants_admin(super)
    public void testTenantsAdmin(){
        String token=TestConfig.getToken("kauth/authorize?uid=6790&cid=www&tenant_id=0");
        Response response = TestConfig.getOrDeleteExecu("get", "tenants/1/tenants_permission_functions?access_token="+token);
        response.then().
                assertThat().statusCode(200).
                body("data.name", Matchers.hasItems("内容管理", "课程建设", "数据分析", "权限管理")).
                body("data.children.name.get(0)", Matchers.hasItems("高校管理", "课程中心")).
                body("data.children.name.get(1)", Matchers.hasItems("通用库", "自建导学课")).
                body("data.children.name.get(2)", Matchers.hasItems("概览")).
                body("data.children.name.get(3)", Matchers.hasItems("租户系统权限"));
    }
    //admin
    public void testAdmin(){
        String token=TestConfig.getToken("kauth/authorize?uid=442131&cid=www&tenant_id=0");
        Response response = TestConfig.getOrDeleteExecu("get", "tenants/1/tenants_permission_functions?access_token="+token);
        response.then().
                assertThat().statusCode(200).
                body("data.name", Matchers.hasItems("内容管理", "课程建设", "数据分析", "权限管理")).
                body("data.children.name.size",Matchers.is(4)).
                body("data.children.name.get(0)", Matchers.hasItems("高校管理", "课程中心"));
        /*List list=with(response.body().asString()).get("data.children.name.get(0)");
        System.out.println();*/

    }
}
