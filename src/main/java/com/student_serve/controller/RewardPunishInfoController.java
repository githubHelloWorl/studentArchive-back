package com.student_serve.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.student_serve.common.BaseResponse;
import com.student_serve.common.ErrorCode;
import com.student_serve.common.ResultUtils;
import com.student_serve.exception.BusinessException;
import com.student_serve.model.dto.rewardPunishInfo.PRInfoQueryRequest;
import com.student_serve.model.entity.Notice;
import com.student_serve.model.entity.Rewardpunishinfo;
import com.student_serve.service.NoticeService;
import com.student_serve.service.RewardpunishinfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RequestMapping("/PR")
@Slf4j
@RestController
public class RewardPunishInfoController {
    @Resource
    private RewardpunishinfoService rewardpunishinfoService;

    /**
     * 更新信息
     *
     * @param
     * @param request
     * @return
     */
    @PostMapping("/createPR")
    public BaseResponse<Rewardpunishinfo> createPR(@RequestBody Rewardpunishinfo rewardpunishinfo, HttpServletRequest request) {
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
        String stime = rewardpunishinfo.getStime();

        // 如果标题 学号 工号 证书名称 信息
        if (StringUtils.isAnyBlank(sid, fileName, fileInfo, stime)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 操作
        return ResultUtils.success(rewardpunishinfoService.createPR(rewardpunishinfo));
    }

    /**
     * 更新信息
     *
     * @param
     * @param request
     * @return
     */
    @PostMapping("/updatePR")
    public BaseResponse<Rewardpunishinfo> updatePR(@RequestBody Rewardpunishinfo rewardpunishinfo, HttpServletRequest request) {
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
}
