package com.student_serve.controller;

import com.student_serve.common.BaseResponse;
import com.student_serve.common.ErrorCode;
import com.student_serve.common.ResultUtils;
import com.student_serve.exception.BusinessException;
import com.student_serve.model.dto.user.UserRegisterRequest;
import com.student_serve.model.entity.Notice;
import com.student_serve.model.entity.User;
import com.student_serve.service.NoticeService;
import com.student_serve.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static com.student_serve.constant.UserConstant.DUTY;
import static com.student_serve.constant.UserConstant.departAndClass;

@RequestMapping("/notice")
@Slf4j
@RestController
public class NoticeController {
    @Resource
    private NoticeService noticeService;

    /**
     * 创建通知
     *
     * @param Notice
     * @param request
     * @return
     */
    @PostMapping("/createNotice")
    public BaseResponse<Notice> createNotice(@RequestBody Notice notice, HttpServletRequest request) {
        log.info("{} 通知创建信息", notice);
        if (notice == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 得到数据

        // 如果标题 内容
        if (StringUtils.isEmpty(notice.getNoticeTitle())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "标题不能为空");
        }
        if (StringUtils.isEmpty(notice.getNoticeContent())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容不能为空");
        }

        // 操作
        Notice notice1 = noticeService.createNotice(notice);

        return ResultUtils.success(notice1);
    }

    /**
     * 检索通知 (标题 日期)-都可以为空
     *
     * @param notice
     * @return
     */
    @PostMapping("/queryNotice")
    public BaseResponse<List<Notice>> queryNotice(@RequestBody Notice notice, HttpServletRequest request) {
        log.info("{} 检索标题", notice);

        return ResultUtils.success(noticeService.queryNotice(notice));
    }

    @PostMapping("/updateNotice")
    public BaseResponse<Notice> updateNotice(@RequestBody Notice notice, HttpServletRequest request) {
        log.info("{} 修改通知", notice);

        return ResultUtils.success(noticeService.updateNotice(notice));
    }

    /**
     * 删除通知
     *
     * @param notice
     * @return
     */
    @PostMapping("/deleteNotice")
    public BaseResponse<Notice> delteNotice(@RequestBody Notice notice) {
        log.info("{} 删除通知", notice);

        return ResultUtils.success(noticeService.deleteNotice(notice));
    }
}
