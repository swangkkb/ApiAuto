package org.kkb.server.api.restAssured.cmsTenants.tenantsRoles;

import com.jayway.restassured.response.Response;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * ws.wang
 * 删除租户角色
 */
@Test
public class TenantsRolesZeleteTest {
    private static final String token = TestConfig.getToken("kauth/authorize?uid=239&cid=www&tenant_id=0");//9d1dc7e9d558677630fa610652708de3

    //错误的token
    public void testErrorToken(){
        String  rolesId=String.valueOf(TenantsRolesAddTest.rolesId);
        Response response = TestConfig.getOrDeleteExecu("delete", "/tenants/1/tenants_roles/"+rolesId+"?access_token=9d1dc7e9d558677630fa652708de3");
        response.then().
                assertThat().statusCode(401).
                body("message", equalTo("无效的access_token"));
    }
    //没有token
    public void testNoToken(){
        String  rolesId=String.valueOf(TenantsRolesAddTest.rolesId);
        Response response = TestConfig.getOrDeleteExecu("delete", "/tenants/1/tenants_roles/"+rolesId);
        response.then().
                assertThat().statusCode(400).
                body("message", equalTo("请输入access_token"));
    }
    //token权限不够
   /* public void testPfToken(){
        String  rolesId=String.valueOf(TenantsRolesAddTest.rolesId);
        Response response = TestConfig.getOrDeleteExecu("delete", "/tenants/1/tenants_roles/"+rolesId+"?access_token="+TestConfig.getToken("kauth/authorize?uid=934251&cid=www&tenant_id=0"));
        response.then().
                assertThat().statusCode(200).
                body("message", equalTo("access权限不够"));
    }*/
    //删除
    public void testZ(){
        String  rolesId=String.valueOf(TenantsRolesAddTest.rolesId);
        Response response = TestConfig.getOrDeleteExecu("delete", "/tenants/1/tenants_roles/"+rolesId+"?access_token="+token);
        response.then().
                assertThat().statusCode(200).
                body("message", equalTo("删除成功"));
    }
}
