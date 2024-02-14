package com.student_serve.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student_serve.model.entity.S;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper extends BaseMapper<S> {
}
