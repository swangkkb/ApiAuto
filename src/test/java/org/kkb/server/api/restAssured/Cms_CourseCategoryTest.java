package org.kkb.server.api.restAssured;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

/**
 *高校邦 门户改造
 * ws.wang
 *
 */
//@Test
public class Cms_CourseCategoryTest {
    /**
     *获取租户的所拥有的分类信息
     * */
    public void testCategory(){

        RequestSpecification requestSpecification=given()
                .baseUri("http://w.api-f3.kaikeba.cn").and()
                .header("ContentType","application/json")
                .port(80);

       Response response= requestSpecification.when()
                .get("/courses/school/1/category");

        response.then().assertThat().statusCode(200).
                body("message", Matchers.is("success")).
                body("data.name", Matchers.hasItems("互联网", "大数据", "互联网营销"));
               // body("data.size", Matchers.is(8));
    }

    /**
     * 获取分类下的课程
     * */
    public void testCategoyCourse(){
        RequestSpecification requestSpecification=given()
                .baseUri("http://release-api.kaikeba.cn").and()
                .header("ContentType","application/json")
                .port(80);

        Response response= requestSpecification.when()
                .get("/courses/school/1/category/1");

        response.then().assertThat().statusCode(200).
                body("message", Matchers.is("success"));
               // body("data.course_name", Matchers.empty());
    }

    /**
     * 获取开课吧推荐课程
     * */

    public void testTrend(){
        RequestSpecification requestSpecification=given()
                .baseUri("http://w.api-f3.kaikeba.cn").and()
                .header("ContentType","application/json")
                .port(80);

        Response response= requestSpecification.when()
                .get("/courses/school/navigation/trend/");

        response.then().assertThat().statusCode(200).
                body("message", Matchers.is("success"));
              //  body("data.course_name", Matchers.hasItems("全媒体营销与品牌管理","Spark实战演练")).
               // body("data.size", Matchers.is(2));
    }

    /***
     *高校邦课程搜索
     * 当传递tenantId 的时候，返回租户的课程信息
     * 当不传递tenantId的时候，返回开课吧的课程信息
     * */
    public void testSearch(){
        //当tenantId不存在的时候，查询开课吧的信息
        RequestSpecification requestSpecification=given()
                .baseUri("http://release-api.kaikeba.cn").and()
                .header("ContentType","application/json")
                .port(80);

        Response response= requestSpecification.when()
                .get("/courses/school/search?keyword=新媒体营销");

        response.then().assertThat().statusCode(200).
                body("message", Matchers.is("success")).
                body("data.tenant_courses", Matchers.empty());



        //当tenantId存在的时候，查询租户的课程信息
        Response response1= requestSpecification.when()
                .get("courses/school/search?tenant_id=1&keyword=新媒体营销");

        response1.then().assertThat().statusCode(200).
                body("message", Matchers.is("success")).
                body("data.kaikeba_courses", Matchers.empty());

    }


    /**
     * 高校邦推荐课程
     * */
    public void testRec(){
        RequestSpecification requestSpecification=given()
                .baseUri("http://release-api.kaikeba.cn").and()
                .header("ContentType","application/json")
                .port(80);

        Response response= requestSpecification.when()
                .get("/courses/schoolrecommend");

        response.then().assertThat().statusCode(400).
                body("message", Matchers.is("success"));


        Response response1= requestSpecification.when()
                .get("/courses/schoolrecommend?tenant_id=1");

        response1.then().assertThat().statusCode(200).
                body("message", Matchers.is("success"));
    }

    /**
     * 高校邦门户改造：校园风光
     * */
    public void testSchool(){
        RequestSpecification requestSpecification=given()
                .baseUri("http://release-api.kaikeba.cn").and()
                .header("ContentType","application/json")
                .port(80);

        Response response= requestSpecification.when()
                .get("/school");

        response.then().assertThat().statusCode(400);


        Response response1= requestSpecification.when()
                .get("/school?tenant_id=1");

        response1.then().assertThat().statusCode(200).
                body("message", Matchers.is("success"));
    }

    /**
     * 获取租户的基本信息
     * */
    public void testTenantsInfo(){
        RequestSpecification requestSpecification=given()
                .baseUri("http://release-api.kaikeba.cn").and()
                .header("ContentType","application/json")
                .port(80);

        Response response= requestSpecification.when()
                .get("/tenants/tenantIntro");

        response.then().assertThat().statusCode(400);


        Response response1= requestSpecification.when()
                .get("/tenants/tenantIntro?tenant_id=1");

        response1.then().assertThat().statusCode(200).
                body("message", Matchers.is("success")).
                body("data.tenant_name", Matchers.is("慧科教育"));
    }
}
