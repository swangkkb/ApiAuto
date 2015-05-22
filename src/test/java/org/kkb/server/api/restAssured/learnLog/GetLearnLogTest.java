package org.kkb.server.api.restAssured.learnLog;

import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * 查询单个课程的学习记录
 * ws.wang
 */
@Test
public class GetLearnLogTest {
    private final String token= TestConfig.getToken("kauth/authorize?uid=812277&cid=www&tenant_id=0");
    //token不正确
    public void testWithErrorToken(){
        //token不正确
        Response response= TestConfig.getOrDeleteExecu("get", "/learn_log/a47987dc32088b73ea0249a37333b/567");
        response.then().
                assertThat().statusCode(401).
                body("message",equalTo("access_token以超时或不存在"));
    }
    //查看所有的学习记录（当classId没有传递或是传递错误+school_code没有传递或是传递错误，则默认查询所有的记录）
    public void testWithAllLog(){
        Response response1=TestConfig.getOrDeleteExecu("get","/learn_log/"+token+"?school_code=buaaa");
        response1.then().
                assertThat().statusCode(200).
                body("data.course_name", Matchers.hasItems("编程入门基础")).
                body("data.size", Matchers.lessThan(5));
    }
    //当school_code正确，查询该高校的记录
    public void testSuc(){
        Response response2=TestConfig.getOrDeleteExecu("get","/learn_log/"+token+"?school_code=buaa");
        response2.then().
                assertThat().statusCode(200).
                body("data.course_name", Matchers.hasItems("编程入门基础")).
                body("data.size", Matchers.lessThan(5));

    }
}
