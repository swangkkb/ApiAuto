package org.kkb.server.api.restAssured;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * Created by pc on 15-4-23.
 * ws.wang
 *
 * 分类--测试
 */
public class CategoryTest {
    private static final String token= TestConfig.getToken("kauth/authorize?uid=812277&cid=www&tenant_id=0");
    /**
     * 无效的token
     * */
    @Test
    public void testWithInvalid(){
        Response response=execu("/categories?access_token=1234567898");
        response.then().
                assertThat().statusCode(401).
                body("message",equalTo("无效的access_token"));

    }
    /**
     * 没有传递token
     * */
    @Test
     public void testWithNoToken(){
        Response response=execu("/categories");
        response.then().
                assertThat().statusCode(400).
                body("message",equalTo("请输入access_token"));
    }
    /**
     * 正确获取token
     * */
    @Test
     public  void testGetCategory(){
        Response response=execu("/categories?access_token="+token);
        response.then().
                assertThat().statusCode(200).
                body("message",equalTo("success"));
    }


     private static Response execu(String url){
        RequestSpecification requestSpecification= TestConfig.requestSpecification();

        Response response=requestSpecification.when()
                .get(url);
        return response;
    }
}
