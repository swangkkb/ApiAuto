package org.kkb.server.api.restAssured.lcmsOCInfo;

import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;


/**
 * ws.wang
 *
 * 获取傲思资源包及资源媒体列表
 */
@Test
public class GetPackagesAndContentsTest {
    public void testSuc(){
        Response response= TestConfig.getOrDeleteExecu("get", "/oc_infos");
        response.then().
                assertThat().statusCode(200).
                body("data.pack_name", Matchers.hasItems("Word 2013基础互动教程", "Excel 2013基础互动教程", "PowerPoint 2013进阶互动教程", "PowerPoint 2013进阶互动教程", "Word 2013进阶互动教程")).
                body("data.oc_content.get(0).size()",Matchers.is(33)).
                body("data.oc_content.get(2).size()",Matchers.is(22)).
                body("data.oc_content.get(4).size()",Matchers.is(37));
    }
}
