package com.student_serve.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student_serve.model.entity.S;
import com.student_serve.model.mapper.LoginMapper;
import com.student_serve.service.LoginService;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, S>
        implements LoginService {
}
