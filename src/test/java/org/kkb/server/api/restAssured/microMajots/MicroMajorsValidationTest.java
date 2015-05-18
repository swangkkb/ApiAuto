package org.kkb.server.api.restAssured.microMajots;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import net.sf.json.JSONObject;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

/**
 * 微专业 用户验证
 *
 * ws.wang
 */
@Test
public class MicroMajorsValidationTest {
    //token无效
    public  void testWithErrorToken(){
        String token=TestConfig.getToken("kauth/authorize?uid=812277&cid=www&tenant_id=0");
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("school_id","227");
        jsonObject.put("period_id","52");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .post("/micro_major/user_validate?access_token=asdfasd345asdf345");
        response.then().assertThat().statusCode(401);

    }
    //丢失参数--token
    public void testWithNoToken(){
        String token=TestConfig.getToken("kauth/authorize?uid=812277&cid=www&tenant_id=0");
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("period_id","52");
        jsonObject.put("period_id","52");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .post("/micro_major/user_validate");
        response.then().assertThat().statusCode(401);
    }
    //丢失参数
    public void testWithNoParam(){
        String token=TestConfig.getToken("kauth/authorize?uid=812277&cid=www&tenant_id=0");
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("period_id","52");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .post("/micro_major/user_validate?access_token="+token);
        response.then().assertThat().statusCode(400);

    }
    //已经激活过的用户
    public void testAlery(){
        String token=TestConfig.getToken("kauth/authorize?uid=812277&cid=www&tenant_id=0");
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("school_id","227");
        jsonObject.put("period_id","52");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .post("/micro_major/user_validate?access_token="+token);
        response.then().assertThat().statusCode(200).body("message", Matchers.equalTo("用户通过失败"));
    }



}
