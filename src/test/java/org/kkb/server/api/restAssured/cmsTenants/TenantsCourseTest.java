package org.kkb.server.api.restAssured.cmsTenants;

import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * ws.wang
 *
 * 获取 租户的课程
 */
@Test
public class TenantsCourseTest {
    private static final String token = TestConfig.getToken("kauth/authorize?uid=239&cid=www&tenant_id=0");

    //无效token
    public void testWithErrorToken(){
        Response response = TestConfig.getOrDeleteExecu("get", "tenants/1/courses?access_token=6044e1f0adfc2001194c");
        response.then().
                assertThat().statusCode(401).
                body("message", equalTo("无效的access_token"));
    }

    //确实参数
    public void testWithNoParam(){
        Response response1 = TestConfig.getOrDeleteExecu("get", "tenants/1/courses");
        response1.then().
                assertThat().statusCode(400).
                body("message", equalTo("请输入access_token"));
    }
    //正常查找
    public void testSuc(){
        Response response2 = TestConfig.getOrDeleteExecu("get", "tenants/1/courses?access_token="+token);
        response2.then().
                assertThat().statusCode(200).
                body("message", equalTo("success")).
                body("data.cms_courses.name", Matchers.hasItems("淘宝直通车系统化实战")).
                body("data.lcms_courses.name", Matchers.hasItems("移动生产力概论-2U")).
                body("data.tenant_sale_courses.name", Matchers.hasItems("Word论文排版你能行"));
    }

}
