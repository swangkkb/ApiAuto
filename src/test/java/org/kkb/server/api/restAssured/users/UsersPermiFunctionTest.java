package org.kkb.server.api.restAssured.users;

import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * ws.wang
 * 获取用户的功能权限
 */
@Test
public class UsersPermiFunctionTest {

    //没有token
    public void testWithNoToken(){
        Response response = TestConfig.getOrDeleteExecu("get", "/users/permission_functions");
        response.then().
                assertThat().statusCode(400);
    }
    //token失效
    public void testWithErrorToken(){
        Response response = TestConfig.getOrDeleteExecu("get", "/users/permission_functions?access_token=9d1dc7e9d55fa610652708de3");
        response.then().
                assertThat().statusCode(401);

    }
    //token权限不够
    public void testWithTokenPF(){
        Response response = TestConfig.getOrDeleteExecu("get", "/users/permission_functions?access_token="+TestConfig.getToken("/kauth/authorize?uid=643458&cid=www&tenant_id=0"));
        response.then().
                assertThat().statusCode(200).body("message",Matchers.equalTo("该管理员不存在"));

    }
    //获取成功
    public void testSuc(){
        Response response = TestConfig.getOrDeleteExecu("get", "/users/permission_functions?access_token="+TestConfig.getToken("/kauth/authorize?uid=239&cid=www&tenant_id=0"));
        response.then().
                assertThat().statusCode(200).
                body("data.name", Matchers.hasItems("内容管理","课程建设","数据分析","权限管理")).
                body("data.children.name.get(0)",Matchers.hasItems("高校管理","课程中心","微专业中心","用户中心","证书管理中心")).
                body("data.children.path.get(0)",Matchers.hasItems("/schools")).
                body("data.children.name.get(3)",Matchers.hasItems("内部系统权限","租户系统权限"));
    }
}
