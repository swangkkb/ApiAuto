package org.kkb.server.api;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.path.json.JsonPath.with;

/**
 * ws.wang
 */
public class TestConfig {

    public static final String path="http://feature2-api-w1.kaikeba.cn";

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

    public static String getToken(String path){
        ///kauth/authorize?uid=812277&cid=www&tenant_id=0
        RequestSpecification requestSpecification= TestConfig.requestSpecification();

        Response response=requestSpecification.when()
                .get(path);

        String token=with(response.body().asString()).get("access_token");
        return token;
    }

    public static Response getOrDeleteExecu(String type,String url){
        if(type.equalsIgnoreCase("get")){
            RequestSpecification requestSpecification= TestConfig.requestSpecification().contentType(ContentType.JSON);

            Response response=requestSpecification.when()
                    .get(url);
            return response;
        }else{
            RequestSpecification requestSpecification= TestConfig.requestSpecification().contentType(ContentType.JSON);

            Response response=requestSpecification.when()
                    .delete(url);
            return response;
        }
    }

    public static Response postOrPutExecu(String type,String url,JSONObject jsonObject){
        if(type.equalsIgnoreCase("post")){
            RequestSpecification requestSpecification= TestConfig.requestSpecification().contentType(ContentType.JSON).body(jsonObject);

            Response response=requestSpecification.when()
                    .post(url);
            return response;
        }else{
            RequestSpecification requestSpecification= TestConfig.requestSpecification().contentType(ContentType.JSON).body(jsonObject);

            Response response=requestSpecification.when()
                    .put(url);
            return response;
        }
    }


}
