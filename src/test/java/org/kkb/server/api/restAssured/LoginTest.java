package org.kkb.server.api.restAssured;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import net.sf.json.JSONObject;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * Created by pc on 15-4-2.
 * users ws.wang
 *
 * 登录
 */
public class LoginTest {
    /**
     * 登录
     * */
   @Test
    public void login(){
       JSONObject jsonObject=new JSONObject();
       jsonObject.put("username","wxs0523");
       jsonObject.put("email","835721919@qq.com");
       jsonObject.put("password","123456");

       RequestSpecification requestSpecification= TestConfig.requestSpecification();

       requestSpecification .contentType(ContentType.JSON).body(jsonObject);

       Response response=requestSpecification.when()
                .post("/users/login");
       response.then().assertThat().statusCode(200)
               .body("name",equalTo("就这样吧"));
   }
    /**
     * 验证错误的用户名
     * */
   @Test
    public void loginWithErrorUserName(){
       JSONObject jsonObject=new JSONObject();
       jsonObject.put("username","wxs05");
       jsonObject.put("email","835721919@qq.com");
       jsonObject.put("password","123456");

       RequestSpecification requestSpecification= TestConfig.requestSpecification();

       requestSpecification .contentType(ContentType.JSON).body(jsonObject);

       Response response=requestSpecification.when()
               .post("/users/login");
       response.then().assertThat().statusCode(400);
   }

    /**
     * 参数为null
     * */
    @Test
    public void loginWithNullUserName(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("username","");
        jsonObject.put("password","123456");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();

        requestSpecification .contentType(ContentType.JSON).body(jsonObject);

        Response response=requestSpecification.when()
                .post("/users/login");
        response.then().assertThat().statusCode(400);
    }
    @Test
    public void loginWithNullPassword(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("username","wxs0523");
        jsonObject.put("password","");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();

        requestSpecification .contentType(ContentType.JSON).body(jsonObject);

        Response response=requestSpecification.when()
                .post("/users/login");
        response.then().assertThat().statusCode(400);
    }
}
