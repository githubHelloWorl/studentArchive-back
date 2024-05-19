package com.student_serve.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student_serve.model.entity.Punish;
import com.student_serve.service.PunishService;
import com.student_serve.mapper.PunishMapper;
import org.springframework.stereotype.Service;

/**
* @author yang99977
* @description 针对表【punish(奖惩信息表)】的数据库操作Service实现
* @createDate 2024-04-25 14:37:10
*/
@Service
public class PunishServiceImpl extends ServiceImpl<PunishMapper, Punish>
    implements PunishService{

}




