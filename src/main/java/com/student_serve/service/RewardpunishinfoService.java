package com.student_serve.service;

import com.student_serve.model.dto.rewardPunishInfo.PRInfoQueryRequest;
import com.student_serve.model.entity.Rewardpunishinfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.student_serve.model.vo.PRInfoVO;

import java.util.List;
/**
* @author yang99977
* @description 针对表【rewardpunishinfo(奖惩信息表)】的数据库操作Service
* @createDate 2024-03-25 19:12:41
*/
public interface RewardpunishinfoService extends IService<Rewardpunishinfo> {
    /**
     * 创建惩戒
     * @param rewardpunishinfo
     * @return
     */
    Rewardpunishinfo createPR(Rewardpunishinfo rewardpunishinfo);

    /**
     *
     * @param fileInfo
     * @return
     */
    List<Rewardpunishinfo> queryPR(String fileInfo);

    /**
     *
     * @param rewardpunishinfo
     * @return
     */
    List<PRInfoVO> queryPRByTeacher(PRInfoQueryRequest rewardpunishinfo);

    Rewardpunishinfo setPRState(Rewardpunishinfo rewardpunishinfo);

    /**
     * 撤回处分
     */
    Rewardpunishinfo deletePunish(Rewardpunishinfo rewardpunishinfo);

    /**
     * 更新
     * @param rewardpunishinfo
     * @return
     */
    Rewardpunishinfo updatePR(Rewardpunishinfo rewardpunishinfo);

    /**
     * 通过删除学生 - 删除信息
     */
    Rewardpunishinfo deletePR(String userAccount);

    /**
     * 得到PR
     * @param userAccount
     * @return
     */
    List<Rewardpunishinfo> getPRByuserAccount(String userAccount);
}
