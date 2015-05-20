package org.kkb.server.api.restAssured.student;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import net.sf.json.JSONObject;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.kkb.server.api.util.PLSql;
import org.testng.annotations.Test;

/**
 * 高校邦 激活
 * ws.wang
 */
@Test
public class ActivateTest {

    //token失效
    public void testWithErrorToken(){
        String jsonStr="{\"access_token\":\"2341234132rqsdfasder13453r\",\"name\":\"swang\",\"no\":\"835721919@qq.com\",\"school_id\":7,\"course_id\":164,\"lms_course_id\":384,\"lms_type\":\"kaikeba\",\"validate_type\": \"credit\"}";
        JSONObject jsonObject=JSONObject.fromObject(jsonStr);
        Response response = TestConfig.postOrPutExecu("post", "/students/activate", jsonObject);
        response.then().
                assertThat().statusCode(401);
    }
    //缺少token
    public void testWithNoToken(){
        String jsonStr="{\"name\":\"swang\",\"no\":\"835721919@qq.com\",\"school_id\":7,\"course_id\":164,\"lms_course_id\":384,\"lms_type\":\"kaikeba\",\"validate_type\": \"credit\"}";
        JSONObject jsonObject=JSONObject.fromObject(jsonStr);
        Response response = TestConfig.postOrPutExecu("post", "/students/activate", jsonObject);
        response.then().
                assertThat().statusCode(422);

    }
    //缺少参数
    public void testWithNoParam(){
        String jsonStr="{\"access_token\":\""+TestConfig.getToken("kauth/authorize?uid=929244&cid=www")+"\" ,\"school_id\":7,\"course_id\":164,\"lms_course_id\":384,\"lms_type\":\"kaikeba\",\"validate_type\": \"credit\"}";
        JSONObject jsonObject=JSONObject.fromObject(jsonStr);
        Response response = TestConfig.postOrPutExecu("post", "/students/activate", jsonObject);
        response.then().
                assertThat().statusCode(400).
                body("message", Matchers.equalTo("学号/邮箱/手机号码必须填一种"));

    }
    //邮箱激活
    public void testWithEmail(){
        PLSql.add("insert into sale_students(school_id,name,no) values (7,'swang','835721919@qq.com')","cms_production");
        PLSql.add("insert into credit_students (school_id,course_id,lms_course_id,lms_type,no,name) values(7,164,384,'kaikeba','835721919@qq.com','swang')","cms_production");

        String jsonStr="{\"access_token\":\""+TestConfig.getToken("kauth/authorize?uid=929244&cid=www")+"\",\"name\":\"swang\",\"no\":\"835721919@qq.com\",\"school_id\":7,\"course_id\":164,\"lms_course_id\":384,\"lms_type\":\"kaikeba\",\"validate_type\": \"credit\"}";
        JSONObject jsonObject=JSONObject.fromObject(jsonStr);
        Response response = TestConfig.postOrPutExecu("post", "/students/activate", jsonObject);
        response.then().
                assertThat().statusCode(200).
                body("user_id", Matchers.equalTo(929244));
       /* PLSql.delete("delete from students where user_id=929244 and school_id=7 and no='835721919@qq.com' and name='swang'","cms_production");
        PLSql.delete("delete from  credit_students where user_id=929244 and school_id=7 and no='835721919@qq.com' and name='swang'","cms_production");
        PLSql.delete("delete from sale_students where user_id=929244 and school_id=7 and no='835721919@qq.com' and name='swang'","cms_production");*/

        //验证该用户是否真的激活
        JSONObject jsonObject1=new JSONObject();
        jsonObject1.put("access_token",TestConfig.getToken("kauth/authorize?uid=929244&cid=www"));
        jsonObject1.put("school_id","7");

        RequestSpecification requestSpecification1= TestConfig.requestSpecification();
        requestSpecification1 .contentType(ContentType.JSON).body(jsonObject1);
        Response response1=requestSpecification1.when()
                .post("/students/validation");
        response1.then().assertThat().log().all().statusCode(200).body("message", Matchers.equalTo("用户通过验证"));

        PLSql.delete("delete from students where user_id=929244 and school_id=7 and no='835721919@qq.com' and name='swang'","cms_production");
        PLSql.delete("delete from  credit_students where user_id=929244 and school_id=7 and no='835721919@qq.com' and name='swang'","cms_production");
        PLSql.delete("delete from sale_students where user_id=929244 and school_id=7 and no='835721919@qq.com' and name='swang'","cms_production");
    }
    //学号激活
    public void testWithNum(){
        PLSql.add("insert into sale_students(school_id,name,no) values (7,'swang','123456')","cms_production");
        PLSql.add("insert into credit_students (school_id,course_id,lms_course_id,lms_type,no,name) values(7,164,384,'kaikeba','123456','swang')","cms_production");

        String jsonStr="{\"access_token\":\""+TestConfig.getToken("kauth/authorize?uid=929244&cid=www")+"\",\"name\":\"swang\",\"no\":\"123456\",\"school_id\":7,\"course_id\":164,\"lms_course_id\":384,\"lms_type\":\"kaikeba\",\"validate_type\": \"credit\"}";
        JSONObject jsonObject=JSONObject.fromObject(jsonStr);
        Response response = TestConfig.postOrPutExecu("post", "/students/activate", jsonObject);
        response.then().
                assertThat().statusCode(200).
                body("user_id", Matchers.equalTo(929244));

        /*PLSql.delete("delete from students where user_id=929244 and school_id=7 and no='123456' and name='swang'","cms_production");
        PLSql.delete("delete from  credit_students where user_id=929244 and school_id=7 and no='123456' and name='swang'","cms_production");
        PLSql.delete("delete from sale_students where user_id=929244 and school_id=7 and no='123456' and name='swang'","cms_production");
*/
        //验证该用户是否真的激活
        JSONObject jsonObject1=new JSONObject();
        jsonObject1.put("access_token",TestConfig.getToken("kauth/authorize?uid=929244&cid=www"));
        jsonObject1.put("school_id","7");

        RequestSpecification requestSpecification1= TestConfig.requestSpecification();
        requestSpecification1 .contentType(ContentType.JSON).body(jsonObject1);
        Response response1=requestSpecification1.when()
                .post("/students/validation");
        response1.then().assertThat().statusCode(200).body("message", Matchers.equalTo("用户通过验证"));
        PLSql.delete("delete from students where user_id=929244 and school_id=7 and no='123456' and name='swang'","cms_production");
        PLSql.delete("delete from  credit_students where user_id=929244 and school_id=7 and no='123456' and name='swang'","cms_production");
        PLSql.delete("delete from sale_students where user_id=929244 and school_id=7 and no='123456' and name='swang'","cms_production");

    }
    //手机号激活
    public void testWithMobile(){
        PLSql.add("insert into sale_students(school_id,name,no) values (7,'swang','18801308505')","cms_production");
        PLSql.add("insert into credit_students (school_id,course_id,lms_course_id,lms_type,no,name) values(7,164,384,'kaikeba','18801308505','swang')","cms_production");

        String jsonStr="{\"access_token\":\""+TestConfig.getToken("kauth/authorize?uid=929244&cid=www")+"\",\"name\":\"swang\",\"no\":\"18801308505\",\"school_id\":7,\"course_id\":164,\"lms_course_id\":384,\"lms_type\":\"kaikeba\",\"validate_type\": \"credit\"}";
        JSONObject jsonObject=JSONObject.fromObject(jsonStr);
        Response response = TestConfig.postOrPutExecu("post", "/students/activate", jsonObject);
        response.then().
                assertThat().statusCode(200).
                body("user_id", Matchers.equalTo(929244));
        /*PLSql.delete("delete from students where user_id=929244 and school_id=7 and no='18801308505' and name='swang'","cms_production");
        PLSql.delete("delete from  credit_students where user_id=929244 and school_id=7 and no='18801308505' and name='swang'","cms_production");
        PLSql.delete("delete from sale_students where user_id=929244 and school_id=7 and no='18801308505' and name='swang'","cms_production");*/

        //验证该用户是否真的激活
        JSONObject jsonObject1=new JSONObject();
        jsonObject1.put("access_token",TestConfig.getToken("kauth/authorize?uid=929244&cid=www"));
        jsonObject1.put("school_id","7");

        RequestSpecification requestSpecification1= TestConfig.requestSpecification();
        requestSpecification1 .contentType(ContentType.JSON).body(jsonObject1);
        Response response1=requestSpecification1.when()
                .post("/students/validation");
        response1.then().assertThat().statusCode(200).body("message", Matchers.equalTo("用户通过验证"));
        PLSql.delete("delete from students where user_id=929244 and school_id=7 and no='18801308505' and name='swang'","cms_production");
        PLSql.delete("delete from  credit_students where user_id=929244 and school_id=7 and no='18801308505' and name='swang'","cms_production");
        PLSql.delete("delete from sale_students where user_id=929244 and school_id=7 and no='18801308505' and name='swang'","cms_production");
    }
}
