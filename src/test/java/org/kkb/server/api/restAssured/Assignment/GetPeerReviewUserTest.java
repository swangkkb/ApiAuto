package org.kkb.server.api.restAssured.Assignment;

import org.testng.annotations.Test;

/**
 * 获取互评信息
 * ws.wang
 *****************************************************************************
 * token=null
 * token 失效
 * assignmentId=null
 * assignmentId 不存在的情况
 *
 * 当token是老师的时候
 *     1：这个老师没有任何的enrollment
 *     2：这个老师enrollment的课程没有学生加入过
 *     3：老师可以获取到 学生的信息
 *
 * 当token是学生的时候
 *     1：这个学生没有任何的enrollment
 *     2：这个学生没有互评的
 *     3：可以正常获取信息
 * */


public class GetPeerReviewUserTest {

 }
