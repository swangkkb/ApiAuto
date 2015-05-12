package org.kkb.server.api.restAssured.users;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;


/**
 * Created by pc on 15-5-12.
 */
@Test
public class UsersLoginTest {
    //缺少用户名
    public void testWithNoName(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("email","835721919@qq.com");
        jsonObject.put("password","1234256");

        Response response = TestConfig.postOrPutExecu("post", "/users/login", jsonObject);
        response.then().
                assertThat().statusCode(400).
                body("message", Matchers.equalTo("用户名不能为空"));
    }
    //缺少密码
    public  void testWithNoPass(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("username","wxs0523");
        jsonObject.put("email","835721919@qq.com");

        Response response = TestConfig.postOrPutExecu("post", "/users/login", jsonObject);
        response.then().
                assertThat().statusCode(400).
                body("message", Matchers.equalTo("密码不能为空"));

    }
    //缺少邮箱
    public void testWithNoEmail(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("username","wxs0523");
        jsonObject.put("password","123456");

        Response response = TestConfig.postOrPutExecu("post", "/users/login", jsonObject);
        response.then().
                assertThat().statusCode(200).
                body("status", Matchers.equalTo("active")).
                body("name", Matchers.equalTo("就这样吧"));


    }
    //用户名和密码不正确
    public  void testWithErrorLogin(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("username","wxs0523");
        jsonObject.put("email","835721919@qq.com");
        jsonObject.put("password","1234256");

        Response response = TestConfig.postOrPutExecu("post", "/users/login", jsonObject);
        response.then().
                assertThat().statusCode(400).
                body("message", Matchers.equalTo("用户名或密码错误"));
    }
    //成功登陆
    public void testSuc(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("username","wxs0523");
        jsonObject.put("email","835721919@qq.com");
        jsonObject.put("password","123456");

        Response response = TestConfig.postOrPutExecu("post", "/users/login", jsonObject);
        response.then().
                assertThat().statusCode(200).
                body("status", Matchers.equalTo("active")).
                body("name", Matchers.equalTo("就这样吧"));

    }

}
