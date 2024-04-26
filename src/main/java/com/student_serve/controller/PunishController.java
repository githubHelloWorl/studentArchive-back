package com.student_serve.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.student_serve.common.BaseResponse;
import com.student_serve.common.ResultUtils;
import com.student_serve.model.entity.Punish;
import com.student_serve.service.PunishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("/punish")
@Slf4j
@RestController
public class PunishController {
    @Resource
    private PunishService punishService;

    @GetMapping("/getAllByFileid")
    public BaseResponse<Punish> getAllByFileid(@RequestParam("fileId") String fileId, HttpServletRequest request){
        log.info("{} 文件编号",fileId);
        QueryWrapper<Punish> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fileId", fileId);
        return ResultUtils.success(punishService.getOne(queryWrapper));
    }

}
