package org.kkb.server.api.restAssured.microMajots;

import org.testng.annotations.Test;

/**
 * 获取班次解锁状态
 * ws.wang
 */
//@Test
public class MicroMajotsProgressTest {
    //token 失效
    public void testWithErrorToken(){

    }
    //参数丢失--token
    public void testWithNoToken(){

    }
    //class id不存在
    public void testWithErrorClassId(){

    }
    //用户和class id 不匹配
    public void testWithUidAndClassId(){

    }
    //测试已经解锁的课程
    public void testSucA(){

    }
    //测试没有解锁的课程
    public void  testSucB(){

    }
    //修改 课程解锁时间，从没解锁，修改到已经解锁，验证是否可以解锁成功
    public void testSucC(){

    }
}
