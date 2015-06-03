package org.kkb.server.api.restAssured.courseVersion;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

/**
 * 复制模板课
 * ws.wang
 */
@Test
public class CopyCourseTest {
    //token 不存在
    public void testWithNoToken(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("id",1);
        jsonObject.put("name","wstest");

        Response response = TestConfig.postOrPutExecu("post", "/course_versions/courses", jsonObject);
        response.then().
                assertThat().statusCode(400).body("message",Matchers.equalTo("请输入access_token"));
    }
    //token 失效
    public void testWithErrorToken(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("access_token","asdfwerq1435asdf");
        jsonObject.put("id",31);
        jsonObject.put("name","wstest");

        Response response = TestConfig.postOrPutExecu("post", "/course_versions/courses", jsonObject);
        response.then().
                assertThat().statusCode(401).body("message",Matchers.equalTo("无效的access_token"));

    }
   /* //token 没有权限
    public void testWithNoPerToken(){

    }*/
    //参数--id （数据库不存在）
    public void testWithParamId(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("access_token",TestConfig.getToken("/kauth/authorize?uid=812277&cid=www&tenant_id=1"));
        jsonObject.put("id",0);
        jsonObject.put("name","wstest");

        Response response = TestConfig.postOrPutExecu("post", "/course_versions/courses", jsonObject);
        response.then().
                assertThat().statusCode(500);
    }
    //参数Id----丢失
    public void testWithNoParamId(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("access_token",TestConfig.getToken("/kauth/authorize?uid=812277&cid=www&tenant_id=1"));
        jsonObject.put("name","wstest");

        Response response = TestConfig.postOrPutExecu("post", "/course_versions/courses", jsonObject);
        response.then().
                assertThat().statusCode(500);

    }
    //参数name--丢失
    public void testWithNoParamName(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("access_token",TestConfig.getToken("/kauth/authorize?uid=812277&cid=www&tenant_id=1"));
        jsonObject.put("id",31);

        Response response = TestConfig.postOrPutExecu("post", "/course_versions/courses", jsonObject);
        response.then().
                assertThat().statusCode(400);
    }
    //复制成功
    public void testSuc(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("access_token",TestConfig.getToken("/kauth/authorize?uid=812277&cid=www&tenant_id=1"));
        jsonObject.put("id",31);
        jsonObject.put("name","wstest");

        Response response = TestConfig.postOrPutExecu("post", "/course_versions/courses", jsonObject);
        response.then().
                assertThat().statusCode(201).
                body("data.type", Matchers.equalTo("edit")).
                body("message", Matchers.equalTo("success"));
    }

}
