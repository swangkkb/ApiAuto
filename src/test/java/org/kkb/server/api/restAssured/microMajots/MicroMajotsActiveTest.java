package org.kkb.server.api.restAssured.microMajots;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import net.sf.json.JSONObject;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.kkb.server.api.util.PLSql;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * 微专业 学生用户激活
 *
 * ws.wang
 */
@Test
public class MicroMajotsActiveTest {
    //token 失效
    public void testWithErrorToken(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("access_token", "123412efsadf13241234");
        jsonObject.put("active_ticket","123456");
        jsonObject.put("mciro_major_period_id","52");
        jsonObject.put("school_id","227");
        jsonObject.put("true_name","swang");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .post("/micro_major/user_active");
        response.then().log().all().assertThat().statusCode(401);

    }
    //参数丢失--token
    public void testWithNoToken(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("active_ticket","123456");
        jsonObject.put("mciro_major_period_id","52");
        jsonObject.put("school_id","227");
        jsonObject.put("true_name","swang");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .post("/micro_major/user_active");
        response.then().log().all().assertThat().statusCode(422);
    }
    //参数丢失
    public void testWithNoParam(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("access_token", TestConfig.getToken("kauth/authorize?uid=239&cid=www&tenant_id=0"));
        jsonObject.put("active_ticket","123456");
        jsonObject.put("mciro_major_period_id","52");
        jsonObject.put("school_id","227");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .post("/micro_major/user_active");
        response.then().log().all().assertThat().statusCode(200).body("data.status",Matchers.equalTo(false));

    }
    //激活--学号
    public void testWithNo(){
        PLSql.add("insert into micro_majors_sale_students (micro_major_id,school_id,micro_majors_period_id,no,name) values(2,227,52,'123456','swang')", "cms_production");

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("access_token", TestConfig.getToken("kauth/authorize?uid=239&cid=www&tenant_id=0"));
        jsonObject.put("active_ticket","123456");
        jsonObject.put("mciro_major_period_id","52");
        jsonObject.put("school_id","227");
        jsonObject.put("true_name","swang");

        test(jsonObject);
    }
    //激活--邮箱
    public void testWithEmail(){
        PLSql.add("insert into micro_majors_sale_students (micro_major_id,school_id,micro_majors_period_id,no,name) values(2,227,52,'swangtest1@163.com','swang')", "cms_production");

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("access_token", TestConfig.getToken("kauth/authorize?uid=239&cid=www&tenant_id=0"));
        jsonObject.put("active_ticket","swangtest1@163.com");
        jsonObject.put("mciro_major_period_id","52");
        jsonObject.put("school_id","227");
        jsonObject.put("true_name","swang");

        test(jsonObject);


    }
    //激活--手机
    public void testWithMobil(){
        PLSql.add("insert into micro_majors_sale_students (micro_major_id,school_id,micro_majors_period_id,no,name) values(2,227,52,'18801308505','swang')", "cms_production");

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("access_token", TestConfig.getToken("kauth/authorize?uid=239&cid=www&tenant_id=0"));
        jsonObject.put("active_ticket","18801308505");
        jsonObject.put("mciro_major_period_id","52");
        jsonObject.put("school_id","227");
        jsonObject.put("true_name","swang");

        test(jsonObject);
    }
    private static void test(JSONObject jsonObject){
        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .post("/micro_major/user_active");
        response.then().log().all().assertThat().statusCode(200).
                body("message", Matchers.equalTo("用户激活成功")).body("data.status",Matchers.equalTo(true));
        //验证
        String token=TestConfig.getToken("kauth/authorize?uid=239&cid=www&tenant_id=0");
        JSONObject jsonObject1=new JSONObject();
        jsonObject1.put("school_id","227");
        jsonObject1.put("period_id","52");

        RequestSpecification requestSpecification1= TestConfig.requestSpecification();
        requestSpecification1 .contentType(ContentType.JSON).body(jsonObject1);
        Response response1=requestSpecification1.when()
                .post("/micro_major/user_validate?access_token="+token);
        response1.then().assertThat().statusCode(200).body("message", Matchers.equalTo("用户通过验证"));

        lcmsEnrollmentDelete();
        PLSql.delete("delete from micro_majors_enrollments where user_id=239 and school_id=227 and micro_majors_period_id=52 and micro_major_id=2","cms_production");
        PLSql.delete("delete from micro_majors_students where user_id=239 and school_id=227 and micro_majors_period_id=52 and micro_major_id=2","cms_production");
        PLSql.delete("delete from micro_majors_sale_students where user_id=239 and school_id=227 and micro_majors_period_id=52 and micro_major_id=2","cms_production");

    }

   private static void lcmsEnrollmentDelete (){

        try {
            ResultSet rs=PLSql.select("select * from micro_majors_enrollments where user_id=239 and school_id=227 and micro_majors_period_id=52 and micro_major_id=2", "cms_production");
            while (rs.next())
            {
                PLSql.delete("delete from enrollments where user_id=239 and course_id="+rs.getString("micro_major_class_id" ),"lcms_production");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
