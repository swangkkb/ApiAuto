package org.kkb.server.api.restAssured.student;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import net.sf.json.JSONObject;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

/**
 * 高校邦：学生验证
 *
 * ws.wang
 */
@Test
public class ValidationTest {
    //没有token
    public void testWithNoToken(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("school_id","217");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .post("/students/validation");
        response.then().assertThat().statusCode(422);

    }
    //token失效
    public void testWithErrorToken(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("access_token","9d1dcfa610652708de3");
        jsonObject.put("school_id","217");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .post("/students/validation");
        response.then().assertThat().statusCode(401);

    }
    //丢失参数--丢失 school_id
    public void testWithParamNoSchoolId(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("access_token","9d1dc7677630fa610652708de3");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .post("/students/validation");
        response.then().assertThat().statusCode(401);
    }
    //验证没有激活过的用户
    public void testA(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("access_token",TestConfig.getToken("kauth/authorize?uid=239&cid=www&tenant_id=0"));
        jsonObject.put("school_id","217");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .post("/students/validation");
        response.then().assertThat().statusCode(400).body("message", Matchers.equalTo("用户验证失败"));

    }
    //验证有激活过的用户
    public void testB(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("access_token",TestConfig.getToken("kauth/authorize?uid=929244&cid=www"));
        jsonObject.put("school_id","229");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .post("/students/validation");
        response.then().assertThat().statusCode(200).body("message", Matchers.equalTo("用户通过验证"));
    }
    //用户和学校不匹配
    public void testC(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("access_token",TestConfig.getToken("kauth/authorize?uid=929244&cid=www"));
        jsonObject.put("school_id","29");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .post("/students/validation");
        response.then().assertThat().statusCode(400).body("message", Matchers.equalTo("用户验证失败"));
    }
}
