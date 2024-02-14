package com.student_serve.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.student_serve.model.entity.S;
import com.student_serve.model.entity.T;
import com.student_serve.model.pojo.Result;
import com.student_serve.service.LoginService;
import com.student_serve.service.LoginService2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private LoginService2 loginService2;

    @PostMapping("login1")
    public Result login1(@RequestBody S s, HttpServletRequest request){
        log.info("{} 进行登录",s);

        QueryWrapper<S> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sid",s.getSid()).eq("password",s.getPassword());
        S s1 = loginService.getOne(queryWrapper);

        if(s1 != null){
            log.info("{} 登陆成功",s1);

            request.getSession().setAttribute("LoginSuccess",s1);
            return Result.success();
        }

        return Result.error();
    }

    @PostMapping("login2")
    public Result login2(@RequestBody T t, HttpServletRequest request){
        log.info("{} 进行登录",t);

        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tid",t.getTid()).eq("password",t.getPassword());
        T t1 = loginService2.getOne(queryWrapper);

        if(t1 != null){
            log.info("{} 登陆成功",t1);

            request.getSession().setAttribute("LoginSuccess",t1);
            return Result.success();
        }

        return Result.error();
    }
}
