package com.student_serve.constant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户常量
 */
public interface UserConstant {

    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "user_login";

    //  region 权限

    /**
     * 学生角色
     */
    String STUDENT_ROLE = "student";

    /**
     * 教师角色
     */
    String TEACHER_ROLE = "teacher";

    /**
     * 管理员角色
     */
    String ADMIN_ROLE = "admin";

    /**
     * 被封号
     */
    String BAN_ROLE = "ban";

    /**
     * 职称
     */
    List<String> DUTY = Arrays.asList("助教","讲师","副教授","教授");

    /**
     * 奖惩信息状态  未审核-0/通过-1/不通过-2
     */
    List<Integer> rewardPunishState = Arrays.asList(0,1,2);

    /**
     * 院系 和 班级
     */
    Map<String,List> departAndClass = new HashMap<>(){{
        put("理学院",Arrays.asList("信息与与计算科学","数学与应用科学","物理学"));
        put("商学院",Arrays.asList("市场营销","会计学","金融学","工商管理"));
        put("信息与控制学院",Arrays.asList("电气工程及其自动化","人工智能","通信工程","电子信息工程","软件工程","计算机科学与技术"));
    }};

    /**
     * 允许进入的路径
     */
    List<String> allowURL = Arrays.asList("login","register","getDC","getDuty","updatePass","getArchive","queryUserByRole");
    // endregion
}
