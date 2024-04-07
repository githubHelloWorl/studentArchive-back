package com.student_serve.mapper;

import com.student_serve.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student_serve.model.vo.UserArchiveVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author yang99977
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {
    public List<UserArchiveVO> queryUserArchive(@Param("user") User user);
}




