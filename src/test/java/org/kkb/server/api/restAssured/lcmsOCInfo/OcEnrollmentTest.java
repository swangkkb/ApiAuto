package org.kkb.server.api.restAssured.lcmsOCInfo;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import net.sf.json.JSONObject;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * ws.wang
 * 加入含有傲思章节课程
 */
@Test
public class OcEnrollmentTest {
    //token不存在
    public void testWithNoToken(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("course_id","2");
        jsonObject.put("lms_course_id","2");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .post("/oc_infos/enrollment");
        response.then().assertThat().statusCode(422);
    }
    //token失效
    public void testWithErrorToken(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("access_token","9d1dc7677630fa610652708de3");
        jsonObject.put("course_id","2");
        jsonObject.put("lms_course_id","2");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .post("/oc_infos/enrollment");
        response.then().assertThat().statusCode(401);
    }
    //缺少参数
    public void testWithNoParam(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("access_token",TestConfig.getToken("kauth/authorize?uid=239&cid=www&tenant_id=0"));
        jsonObject.put("lms_course_id","2");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .post("/oc_infos/enrollment");
        response.then().assertThat().statusCode(422);
    }
    //测试已经加入过得课程
    public void testAleryCourse(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("access_token",TestConfig.getToken("kauth/authorize?uid=239&cid=www&tenant_id=0"));
        jsonObject.put("course_id","2");
        jsonObject.put("lms_course_id","2");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .post("/oc_infos/enrollment");
        response.then().assertThat().statusCode(200)
                .body("data",equalTo(-2));
    }
    //测试 没有这个班次
    public void testNullCourse(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("access_token",TestConfig.getToken("kauth/authorize?uid=239&cid=www&tenant_id=0"));
        jsonObject.put("course_id","100000");
        jsonObject.put("lms_course_id","100000");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .post("/oc_infos/enrollment");
        response.then().assertThat().statusCode(200)
                .body("data",equalTo(-1));
    }

}
