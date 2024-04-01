package com.student_serve.mapper;

import com.student_serve.model.dto.rewardPunishInfo.PRInfoQueryRequest;
import com.student_serve.model.entity.Rewardpunishinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student_serve.model.vo.PRInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author yang99977
* @description 针对表【rewardpunishinfo(奖惩信息表)】的数据库操作Mapper
* @createDate 2024-03-25 19:12:41
* @Entity com.student_serve.model.entity.Rewardpunishinfo
*/
@Mapper
public interface RewardpunishinfoMapper extends BaseMapper<Rewardpunishinfo> {

    public List<PRInfoVO> queryPRByTeacher(@Param("form")PRInfoQueryRequest prInfoQueryRequest);

}




