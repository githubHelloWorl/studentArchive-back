package com.student_serve.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student_serve.model.entity.T;
import com.student_serve.model.mapper.LoginMapper;
import com.student_serve.model.mapper.LoginMapper2;
import com.student_serve.service.LoginService2;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl2 extends ServiceImpl<LoginMapper2, T>
        implements LoginService2 {
}
