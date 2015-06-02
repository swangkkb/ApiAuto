package org.kkb.server.api.restAssured;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;
import net.sf.json.JSONObject;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * Created by pc on 15-3-18.
 * user:ws.wang
 * 找座
 */
public class ZhaoZuoTest {
    @Test
    public void alogin(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("username","wxs0523");
        jsonObject.put("email","835721919@qq.com");
        jsonObject.put("password","123456");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();

        requestSpecification
                .contentType(ContentType.JSON).body(jsonObject).
        when()
                .post("/users/login").
        then().log().all()
                .assertThat().statusCode(200)
                .body("name",equalTo("就这样吧"));
    }
    @Test
    public void blosedCourse(){
        RequestSpecification requestSpecification= TestConfig.requestSpecification();

        requestSpecification.
        when()
                .get("/courses/concluded_enrollment_courses?username=835721919@qq.com&password=123456").
        then()
                .assertThat().statusCode(200)
                //.body("name", hasItems("网站设计技术", "响应式Web设计", "响应式Web设计", "微信营销入门", "高级数据库管理", "HTML5 开发技术", "编程入门基础", "MySQL入门到精通（下）", "MySQL入门到精通（下）"));
                .body("name", Matchers.hasItems("高级编程语言"));

    }
    @Test
    public void courseInfo(){
        RequestSpecification requestSpecification= TestConfig.requestSpecification();

        requestSpecification.
        when()
                .get("/courses/26/course_intro").
        then()
                .assertThat().statusCode(200)
                .body("course_name",equalTo("互联网创业创新理论"))
                .body("category_name",equalTo("创新创业"));

    }


}
