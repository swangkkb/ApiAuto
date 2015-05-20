package org.kkb.server.api.restAssured.course;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;


/**
 * 给课程添加公告
 *
 * ws.wang
 */
//@Test
public class CourseAnnouncementTest {
    /*//公告id不存在
    public void testErrorAnId(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("announcement_id","");
        jsonObject.put("title","");
        jsonObject.put("body","");

        String url="/courses/686/announcement?access_token="+TestConfig.getToken("/kauth/authorize?uid=812277&cid=www&tenant_id=1");
        Response response = TestConfig.postOrPutExecu("post", url, jsonObject);
        response.then().log().all().
                assertThat().statusCode(400);
    }

    //成功获取公告
    public void testSuc(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("announcement_id","3");
        jsonObject.put("title","wsTitle");
        jsonObject.put("body","wsBody");

        String url="/courses/686/announcement?access_token="+TestConfig.getToken("/kauth/authorize?uid=812277&cid=www&tenant_id=1");
        Response response = TestConfig.postOrPutExecu("post", url, jsonObject);
        response.then().
                assertThat().statusCode(200).body("message", Matchers.equalTo("操作成功"));

    }
*/
}
