package com.student_serve.controller;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.student_serve.model.entity.S;
import com.student_serve.model.entity.T;
import com.student_serve.model.mapper.LoginMapper;
import com.student_serve.model.mapper.LoginMapper2;
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

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private LoginMapper2 loginMapper2;

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

        /*
        QueryWrapper<S> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sid", s.getSid());
        S s1 = loginService.getOne(queryWrapper);
        if (s1 != null) {
            log.info("{} 已有角色", s1);
            return Result.error();
        }
         */

        // 进行插入
        boolean result = loginService.save(s);
        if (result) {
            QueryWrapper<S> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("sid").orderByDesc("sid").last("LIMIT 1");
            S s1 = loginMapper.selectOne(queryWrapper);
            log.info("{} 注册信息", s1);
            return Result.success(1, "success", s1.getSid());
        }

        return Result.error("系统错误");
    }

    @PostMapping("/register2")
    public Result register2(@RequestBody T t, HttpServletRequest request) {
        log.info("{} 进行注册", t);

        /*
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tid", t.getTid());
        T t1 = loginService2.getOne(queryWrapper);
        if (t1 != null) {
            log.info("{} 已经存在", t1);
            return Result.error();
        }
         */

        // 进行插入
        boolean result = loginService2.save(t);
        if (result) {
            QueryWrapper<T> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("tid").orderByDesc("tid").last("LIMIT 1");
            T t1 = loginMapper2.selectOne(queryWrapper);
            log.info("{} 注册信息", t1);
            return Result.success(1, "success", t1.getTid());
        }

        return Result.error("系统错误");
    }

    @GetMapping("/getStudentInfo")
    public Result getStudentInfo(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("LoginSuccess");

        if (user == null) {
            return Result.error();
        }

        QueryWrapper<S> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sid", user.getId());
        S s = loginMapper.selectOne(queryWrapper);
        if (s == null) {
            return Result.error();
        }
        return Result.success(1, "success", s);
    }

    @GetMapping("/getTeacherInfo")
    public Result getTeacherInfo(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("LoginSuccess");

        if (user == null) {
            return Result.error();
        }

        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tid", user.getId());
        T t = loginMapper2.selectOne(queryWrapper);
        if (t == null) {
            return Result.error();
        }
        return Result.success(1, "success", t);
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
