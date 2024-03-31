package com.student_serve.controller;

import com.student_serve.common.BaseResponse;
import com.student_serve.common.ErrorCode;
import com.student_serve.common.ResultUtils;
import com.student_serve.exception.BusinessException;
import com.student_serve.model.dto.user.UserRegisterRequest;
import com.student_serve.model.entity.User;
import com.student_serve.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import static com.student_serve.constant.UserConstant.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@Slf4j
@RestController
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 用户登录
     *
     * @param user
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody User user, HttpServletRequest request) {
        log.info("{} 用户登录信息", user);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 得到数据

        // 如果账号 密码
        if (StringUtils.isEmpty(user.getUserAccount())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号不能为空");
        }
        if (StringUtils.isAnyBlank(user.getUserPassword())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码不能为空");
        }

        User user1 = userService.userLogin(user, request);

        return ResultUtils.success(user1);
    }

    @GetMapping("/logout")
    public BaseResponse<User> userLogout(HttpServletRequest request) {
        log.info("进行退出");

        if (ObjectUtils.isEmpty(request.getSession().getAttribute(USER_LOGIN_STATE))) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        request.getSession().setAttribute(USER_LOGIN_STATE, null);
        return ResultUtils.success(null);
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public BaseResponse<User> userRegister(@RequestBody UserRegisterRequest user, HttpServletRequest request) {
        log.info("{} 进行注册", user);

        // 验证
        if (StringUtils.isAnyBlank(user.getUserAccount(), user.getUserPassword(), user.getCheckRePassword(), user.getCardId(), user.getUserRole(), user.getPhone(), user.getDepartment(), user.getClasses())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        User user1 = userService.userRegister(user);
        if (ObjectUtils.isEmpty(user1)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户注册失败");
        }

        return ResultUtils.success(user1);
    }


    /**
     * 更新密码
     *
     * @param user
     * @param request
     * @return
     */
    @PostMapping("/updatePass")
    public BaseResponse<User> updatePass(@RequestBody UserRegisterRequest user, HttpServletRequest request) {
        log.info("{} 进行修改密码", user);

        // 验证
        if (StringUtils.isAnyBlank(user.getUserPassword(), user.getCheckRePassword(), user.getCardId())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        User user1 = userService.updatePass(user);
        if (ObjectUtils.isEmpty(user1)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "修改密码失败");
        }

        return ResultUtils.success(user1);
    }

    /**
     * 得到学号根据身份
     *
     * @param classes
     * @return
     */
    @GetMapping("/getUserAccountByClasses")
    public BaseResponse<List<String>> getUserAccountByClasses(@RequestParam("classes") String classes) {
        log.info("{} 使用班级", classes);

        if (StringUtils.isEmpty(classes)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //
        List<String> result = userService.getUserAccountByClasses(classes);

        return ResultUtils.success(result);
    }

    /**
     * 得到用户根据身份
     *
     * @param userRole
     * @return
     */
    @GetMapping("/queryUserByRole")
    public BaseResponse<List> queryUserByRole(@RequestParam("userRole") String userRole) {
        log.info("{} 身份", userRole);

        if (StringUtils.isEmpty(userRole)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        return ResultUtils.success(userService.getUserByRole(userRole));
    }

    /**
     * 得到学生
     *
     * @param user
     * @return
     */
    @PostMapping("/queryPostStudent")
    public BaseResponse<List> queryUserByRole(@RequestBody User user) {
        log.info("{} 身份", user);

//        if(StringUtils.isAnyBlank(user.getUserRole(),user.getClasses())){
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }

        return ResultUtils.success(userService.queryPostStudent(user));
    }

    /**
     * 更新信息
     *
     * @param user
     * @return
     */
    @PostMapping("/updateUser")
    public BaseResponse<User> updateUser(@RequestBody User user, HttpServletRequest request) {
        log.info("{} 更新信息", user);

        String department = user.getDepartment();
        String classes = user.getClasses();
        String phone = user.getPhone();

        if (StringUtils.isAnyBlank(department, classes, phone)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        return ResultUtils.success(userService.updateUser(user));
    }

    @PostMapping("/deleteUser")
    public BaseResponse<User> deleteUser(@RequestBody User user) {
        log.info("{} 删除角色", user);

        // 如果身份是空
        if (StringUtils.isAnyBlank(user.getUserAccount())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        return ResultUtils.success(userService.deleteUser(user));
    }

    /**
     * 得到用户列表
     *
     * @param user
     * @return
     */
    @PostMapping("/queryUser")
    public BaseResponse<List> queryUser(@RequestBody User user) {
        log.info("{} 身份", user);

        // 如果身份是空
        if (StringUtils.isAnyBlank(user.getUserRole())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        return ResultUtils.success(userService.queryUser(user));
    }

    @GetMapping("/getDC")
    public BaseResponse<Map<String, List>> getDC() {
        return ResultUtils.success(departAndClass);
    }

    @GetMapping("/getDuty")
    public BaseResponse<List> getDuty() {
        return ResultUtils.success(DUTY);
    }
}
