package com.student_serve.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.student_serve.model.entity.S;
import com.student_serve.model.entity.T;
import com.student_serve.model.pojo.Result;
import com.student_serve.model.vo.User;
import com.student_serve.service.LoginService;
import com.student_serve.service.LoginService2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private LoginService2 loginService2;

    @PostMapping("/login1")
    public Result login1(@RequestBody S s, HttpServletRequest request) {
        log.info("{} 进行登录", s);

        QueryWrapper<S> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sid", s.getSid()).eq("password", s.getPassword());
        S s1 = loginService.getOne(queryWrapper);

        if (s1 != null) {
            log.info("{} 登陆成功", s1);
            User user = s1.toUser();

            request.getSession().setAttribute("LoginSuccess", user);
            return Result.success(1, "登录成功", user);
        }

        return Result.error("登录失败,账号或密码不对");
    }

    @PostMapping("/login2")
    public Result login2(@RequestBody T t, HttpServletRequest request) {
        log.info("{} 进行登录", t);

        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tid", t.getTid()).eq("password", t.getPassword());
        T t1 = loginService2.getOne(queryWrapper);

        if (t1 != null) {
            log.info("{} 登陆成功", t1);
            User user = t1.toUser();

            request.getSession().setAttribute("state", "teacher");
            request.getSession().setAttribute("LoginSuccess", user);
            return Result.success(1, "登陆成功", user);
        }

        return Result.error("登录失败,账号或密码不对");
    }

    @PostMapping("/register1")
    public Result register1(@RequestBody S s, HttpServletRequest request) {
        log.info("{} 进行登录", s);

        QueryWrapper<S> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sid", s.getSid());
        S s1 = loginService.getOne(queryWrapper);

        if (s1 != null) {
            log.info("{} 已有角色", s1);
            return Result.error();
        }

        // 进行插入
        loginService.save(s);

        return Result.error();
    }

    @PostMapping("/register2")
    public Result register2(@RequestBody T t, HttpServletRequest request) {
        log.info("{} 进行注册", t);

        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tid", t.getTid());
        T t1 = loginService2.getOne(queryWrapper);

        if (t1 != null) {
            log.info("{} 已经存在", t1);
            return Result.error();
        }

        // 进行插入
        loginService2.save(t);

        return Result.success();
    }

    @GetMapping("/getState")
    public Result getState(HttpServletRequest request) {
        if (request.getSession().getAttribute("state") == null) {
            return Result.error();
        }
        return Result.success((String) request.getSession().getAttribute("state"));
    }

    @GetMapping("/getLogin")
    public Result getLogin(HttpServletRequest request) {
        log.info("得到登录信息: {}", request.getSession().getAttribute("LoginSuccess"));
        if (request.getSession().getAttribute("LoginSuccess") == null) {
            return Result.error();
        }
        return Result.success(1, "success", request.getSession().getAttribute("LoginSuccess"));
    }
}
