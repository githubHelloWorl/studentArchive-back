package com.student_serve.mapper;

import com.student_serve.model.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yang99977
* @description 针对表【notice(公告表)】的数据库操作Mapper
* @createDate 2024-03-25 19:12:40
* @Entity com.student_serve.model.entity.Notice
*/
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

}




