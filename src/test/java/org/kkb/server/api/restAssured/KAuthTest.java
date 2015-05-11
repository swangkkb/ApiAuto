package org.kkb.server.api.restAssured;


import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.is;


/**
 * Created by pc on 15-3-16.
 * user:ws.wang
 */
public class KAuthTest {
    /**
     * 验证获取token
     * 正确获取
     * */
    @Test
    public void test() {

        RequestSpecification requestSpecification= TestConfig.requestSpecification();

        requestSpecification.when()
                .get("/kauth/authorize?uid=809684&cid=www&tenant_id=0").
        then()
                //.log().all()
                .assertThat().statusCode(200);
    }
    /**
     * 没有uid
     * */
    @Test
    public void testWithNoUid(){
        RequestSpecification requestSpecification= TestConfig.requestSpecification().contentType(ContentType.JSON);

        requestSpecification.when()
                .get("/kauth/authorize?cid=www&tenant_id=0").
        then()
                //.log().all()
                .assertThat().statusCode(400)
                .body("status", Matchers.equalTo(false))
                .body("message", is("请完善uid、cid"));
    }
    @Test
    public void testWithNoCid() {

        RequestSpecification requestSpecification= TestConfig.requestSpecification();

        requestSpecification.when()
                .get("/kauth/authorize?uid=809684&tenant_id=0").
        then()
                //.log().all()
                .assertThat().statusCode(400)
                .body("status", Matchers.equalTo(false))
                .body("message", is("请完善uid、cid"));
    }
    @Test
    public void testWithNoTenant_id() {

        RequestSpecification requestSpecification= TestConfig.requestSpecification();

        requestSpecification.when()
                .get("/kauth/authorize?uid=809684&cid=www").
        then()
                //.log().all()
                .assertThat().statusCode(200);
                /*.body("status", Matchers.equalTo(false))
                .body("message", is("请完善uid、cid"));*/
    }

}
