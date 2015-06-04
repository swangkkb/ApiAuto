package org.kkb.server.api.restAssured.users;

import com.jayway.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

/**
 * 根据token获取用户信息.
 */
@Test
public class GetUsersInfo {
    public void getInfo(){
        RequestSpecification requestSpecification= TestConfig.requestSpecification();

        requestSpecification.when()
                .get("/users?access_token="+TestConfig.getToken("kauth/authorize?uid=239&cid=www&tenant_id=0")).
                then()
                        //.log().all()
                .assertThat().statusCode(200).body("data.name", Matchers.equalTo("开课吧"));
    }

}
