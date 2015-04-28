package org.kkb.server.api;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.path.json.JsonPath.with;

/**
 * Created by pc on 15-4-20.
 */
public class TestConfig {

    public static final String path="http://w.api-f3.kaikeba.cn";

    public static RequestSpecification requestSpecification(){
        if(path.contains("cn")){
            return given()
                    .baseUri(path).and()
                    .header("ContentType","application/json")
                    .port(80);

        }else {

            return given()
                    .baseUri(path).and()
                    .header("ContentType","application/json")
                    .port(443);
        }
    }

    public static String getToken(){
        RequestSpecification requestSpecification= TestConfig.requestSpecification();

        Response response=requestSpecification.when()
                .get("/kauth/authorize?uid=812277&cid=www&tenant_id=0");

        String token=with(response.body().asString()).get("access_token");
        return token;
    }

    public static void main(String[] args){
        String token=getToken();
        System.out.println();
    }
}
