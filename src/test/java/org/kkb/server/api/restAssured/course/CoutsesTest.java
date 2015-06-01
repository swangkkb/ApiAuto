package org.kkb.server.api.restAssured.course;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.kkb.server.api.util.PLSql;
import org.testng.annotations.Test;


import static com.jayway.restassured.path.json.JsonPath.with;

/**
 * 增加课程
 * ws.wang
 */
@Test
public class CoutsesTest {
    public static int courseId;
    //没有token
    public void testWithNoToken(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("category_id","2");
        jsonObject.put("type","InstructiveCourse");
        jsonObject.put("name","wsInstructiveCourse");

        Response response = TestConfig.postOrPutExecu("post", "/courses", jsonObject);
        response.then().
                assertThat().statusCode(400);
    }
    //token 失效
    public void testWithErrorToken(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("access_token","13413rasdfasdfasdf");
        jsonObject.put("category_id","2");
        jsonObject.put("type","InstructiveCourse");
        jsonObject.put("name","wsInstructiveCourse");

        Response response = TestConfig.postOrPutExecu("post", "/courses", jsonObject);
        response.then().
                assertThat().statusCode(401);
    }
    //参数 -category_id 不存在
    public void testWithErrorCategoryID(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("access_token","13413rasdfasdfasdf");
        jsonObject.put("type","InstructiveCourse");
        jsonObject.put("name","wsInstructiveCourse");

        Response response = TestConfig.postOrPutExecu("post", "/courses", jsonObject);
        response.then().
                assertThat().statusCode(401);
    }
    //创建成功
    public void testSuc(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("access_token",TestConfig.getToken("/kauth/authorize?uid=812277&cid=www&tenant_id=1"));
        jsonObject.put("category_id","2");
        jsonObject.put("type","CourseClass");
        jsonObject.put("name","wsInstructiveCourse");

        Response response = TestConfig.postOrPutExecu("post", "/courses", jsonObject);
        response.then().
                assertThat().statusCode(201).body("message", Matchers.equalTo("success"));
        courseId=with(response.body().asString()).get("data.id");

        PLSql.delete("delete from courses where id=" + String.valueOf(courseId), "lcms_production");
        PLSql.delete("delete from menus where course_id=" + String.valueOf(courseId), "lcms_production");
    }
}
