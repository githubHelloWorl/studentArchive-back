package com.student_serve.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.student_serve.common.BaseResponse;
import com.student_serve.common.ErrorCode;
import com.student_serve.common.ResultUtils;
import com.student_serve.exception.BusinessException;
import com.student_serve.model.dto.rewardPunishInfo.PRInfoQueryRequest;
import com.student_serve.model.entity.Notice;
import com.student_serve.model.entity.Punish;
import com.student_serve.model.entity.Rewardpunishinfo;
import com.student_serve.service.NoticeService;
import com.student_serve.service.PunishService;
import com.student_serve.service.RewardpunishinfoService;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONObject;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RequestMapping("/PR")
@Slf4j
@RestController
public class RewardPunishInfoController {
    @Resource
    private RewardpunishinfoService rewardpunishinfoService;

    @Resource
    private PunishService punishService;

    @Value("${imgUrl}")
    private String imgUrl;

    /**
     * 更新信息
     *
     * @param
     * @param request
     * @return
     */
    @PostMapping("/createPR")
    @ResponseBody
    public BaseResponse<Rewardpunishinfo> createPR(@RequestParam("reward") String reward, @RequestParam(name = "file", required = false) MultipartFile file,@RequestParam(name = "punishInfoRequest", required = false) String punishInfoRequest, HttpServletRequest request) {
        // 接受前端数据
        Rewardpunishinfo rewardpunishinfo = JSONObject.parseObject(reward, Rewardpunishinfo.class);
        Punish punish = JSONObject.parseObject(punishInfoRequest, Punish.class);

        log.info("{} 更新奖惩信息, {} 上传文件", rewardpunishinfo,file);
        System.out.println(file);
        if (rewardpunishinfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 得到数据
        String sid = rewardpunishinfo.getSid();
        String tid = rewardpunishinfo.getTid();
        String fileName = rewardpunishinfo.getFileName();
        String state = rewardpunishinfo.getState();
        String fileInfo = rewardpunishinfo.getFileInfo();
        String filePath = rewardpunishinfo.getFilePath();
        String fileUnit = rewardpunishinfo.getFileUnit();
        String tname = rewardpunishinfo.getTname();
        Date fileTime = rewardpunishinfo.getFileTime();
        Date submitTime = rewardpunishinfo.getSubmitTime();
        Date checkTime = rewardpunishinfo.getCheckTime();
        String stime = rewardpunishinfo.getStime();

        // 如果标题 学号 工号 证书名称 信息
        if (StringUtils.isAnyBlank(sid, fileName, fileInfo, stime)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 得到数据
        String filereason = punish.getFilereason();
        String type = punish.getType();
        String level = punish.getLevel();
        String circle = punish.getCircle();

        // 如果原因 类型 级别 周期
        if (StringUtils.isAnyBlank(filereason, type, level, circle)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 设置文件
        if(file != null){
            try {
                // 获得文件原始名称
                String originalFilename = file.getOriginalFilename();
                System.out.println("originalFilename = " + originalFilename);
                String[] backUrl = originalFilename.split("\\.");
                System.out.println(Arrays.toString(backUrl));
                String back = backUrl[backUrl.length - 1];
                // 设置日期
                Date date = new Date();
                // 获得毫秒数
                long t = date.getTime();

                // 创建日期格式化对象,在获取格式化对象时可以指定风格
                DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                String str = df.format(date);
                str += Long.toString(t);
                str += ".";
                str += back;
                // 将文件储存在这个位置，并重新命名
                file.transferTo(new File(imgUrl + str));
                rewardpunishinfo.setFilePath(str);
            } catch (Exception e) {
                log.info("出错了");
                throw new BusinessException(ErrorCode.OPERATION_ERROR);
            }
        }
        else{
            System.out.println("文件是空的");
        }


        // 操作
        Rewardpunishinfo rewardpunishinfo1 = rewardpunishinfoService.createPR(rewardpunishinfo);

        // 操作 punish
        punish.setFileid(rewardpunishinfo1.getFileId());
        punishService.save(punish);

        // 操作
        return ResultUtils.success(rewardpunishinfo1);
    }

    /**
     * 更新信息
     *
     * @param
     * @param request
     * @return
     */
    @PostMapping("/updatePR")
    public BaseResponse<Rewardpunishinfo> updatePR(@RequestParam("PR") String PR, @RequestParam(name = "file", required = false) MultipartFile file,@RequestParam(name = "punishInfoRequest", required = false) String punishInfoRequest, HttpServletRequest request) {
        Rewardpunishinfo rewardpunishinfo = JSONObject.parseObject(PR,Rewardpunishinfo.class);
        Punish punish = JSONObject.parseObject(punishInfoRequest, Punish.class);

        log.info("{} 更新奖惩信息", rewardpunishinfo);
        if (rewardpunishinfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 得到数据
        String sid = rewardpunishinfo.getSid();
        String tid = rewardpunishinfo.getTid();
        String fileName = rewardpunishinfo.getFileName();
        String state = rewardpunishinfo.getState();
        String fileInfo = rewardpunishinfo.getFileInfo();
        String filePath = rewardpunishinfo.getFilePath();
        String fileUnit = rewardpunishinfo.getFileUnit();
        String tname = rewardpunishinfo.getTname();
        Date fileTime = rewardpunishinfo.getFileTime();
        Date submitTime = rewardpunishinfo.getSubmitTime();
        Date checkTime = rewardpunishinfo.getCheckTime();
//        Date stime = rewardpunishinfo.getStime();


        // 如果标题 学号 工号 证书名称 信息
        if (StringUtils.isAnyBlank(sid, fileName, fileInfo)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 设置文件
        if(file != null){
            try {
                // 获得文件原始名称
                String originalFilename = file.getOriginalFilename();
                System.out.println("originalFilename = " + originalFilename);
                String[] backUrl = originalFilename.split("\\.");
                System.out.println(Arrays.toString(backUrl));
                String back = backUrl[backUrl.length - 1];
                // 设置日期
                Date date = new Date();
                // 获得毫秒数
                long t = date.getTime();

                // 创建日期格式化对象,在获取格式化对象时可以指定风格
                DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                String str = df.format(date);
                str += Long.toString(t);
                str += ".";
                str += back;
                System.out.println(str);

                // 将文件储存在这个位置，并重新命名
                file.transferTo(new File(imgUrl + str));
                // 睡眠1秒
                rewardpunishinfo.setFilePath(str);
            } catch (Exception e) {
                log.info("出错了");
                throw new BusinessException(ErrorCode.OPERATION_ERROR);
            }
        }
        else{
            System.out.println("文件是空的");
        }

        // 操作
        if(!ObjectUtils.isEmpty(punish)){
            punishService.updateById(punish);
        }

        // 操作
        return ResultUtils.success(rewardpunishinfoService.updatePR(rewardpunishinfo));
    }


    /**
     * 得到文件信息
     *
     * @param fileInfo
     * @param request
     * @return
     */
    @GetMapping("/queryPR")
    public BaseResponse<List> queryPR(String fileInfo, HttpServletRequest request) {
        log.info("{} 文件类型", fileInfo);

        if (StringUtils.isEmpty(fileInfo)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        PageHelper.startPage(1, 10);

        return ResultUtils.success(rewardpunishinfoService.queryPR(fileInfo));
    }

    /**
     * dedao1
     *
     * @param rewardpunishinfo
     * @param request
     * @return
     */
    @PostMapping("/queryPRByTeacher")
    public BaseResponse<List> queryPRByTeacher(@RequestBody PRInfoQueryRequest rewardpunishinfo, HttpServletRequest request) {
        log.info("{} 查找文件", rewardpunishinfo);

        //
        String state = rewardpunishinfo.getState();
        String userAccount = rewardpunishinfo.getUserAccount();
        String fileInfo = rewardpunishinfo.getFileInfo();
        String classes = rewardpunishinfo.getClasses();

        if (StringUtils.isAnyBlank(fileInfo)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        return ResultUtils.success(rewardpunishinfoService.queryPRByTeacher(rewardpunishinfo));
    }

    /**
     * 设置证书状态 审核教师
     * @param rewardpunishinfo
     * @param request
     * @return
     */
    @PostMapping("/setFileState")
    public BaseResponse<Rewardpunishinfo> queryPRByTeacher(@RequestBody Rewardpunishinfo rewardpunishinfo, HttpServletRequest request) {
        log.info("{} 修改文件类型", rewardpunishinfo);

        //
//        String state = rewardpunishinfo.getState();
//        String userAccount = rewardpunishinfo.getUserAccount();
//        String fileInfo = rewardpunishinfo.getFileInfo();
//        String classes = rewardpunishinfo.getClasses();
        String fileId = rewardpunishinfo.getFileId();
        String state = rewardpunishinfo.getState();
        String tid = rewardpunishinfo.getTid();

        if (StringUtils.isAnyBlank(fileId, state)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        return ResultUtils.success(rewardpunishinfoService.setPRState(rewardpunishinfo));
    }

    /**
     * 撤回证书
     */
    @PostMapping("/deleteReward")
    public BaseResponse<Rewardpunishinfo> deleteReward(@RequestBody Rewardpunishinfo rewardpunishinfo, HttpServletRequest request) {
        log.info("{} 撤回证书", rewardpunishinfo);

        String fileId = rewardpunishinfo.getFileId();

        if (StringUtils.isAnyBlank(fileId)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        return ResultUtils.success(rewardpunishinfoService.deletePunish(rewardpunishinfo));
    }

    /**
     * 撤回处分
     */
    @PostMapping("/deletePunish")
    public BaseResponse<Rewardpunishinfo> deletePunish(@RequestBody Rewardpunishinfo rewardpunishinfo, HttpServletRequest request) {
        log.info("{} 撤回处分", rewardpunishinfo);

        String fileId = rewardpunishinfo.getFileId();

        if (StringUtils.isAnyBlank(fileId)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        return ResultUtils.success(rewardpunishinfoService.deletePunish(rewardpunishinfo));
    }

    @GetMapping("/getPRByuserAccount")
    public BaseResponse<List> getPRByuserAccount(@RequestParam("userAccount") String userAccount,HttpServletRequest request){
        log.info("{} 身份进行查询PR");

        if(userAccount.length() != 11){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"学生学号错误");
        }
        return ResultUtils.success(rewardpunishinfoService.getPRByuserAccount(userAccount));
    }

    @GetMapping("/getPRByAccountState")
    public BaseResponse<List> getPRByAccountState(@RequestParam("userAccount") String userAccount,HttpServletRequest request){
        log.info("{} 身份进行查询PR 查询通过的");

        if(userAccount.length() != 11){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"学生学号错误");
        }
        return ResultUtils.success(rewardpunishinfoService.getPRByAccountState(userAccount));
    }
}
