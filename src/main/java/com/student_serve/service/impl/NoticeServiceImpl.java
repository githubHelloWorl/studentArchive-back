package com.student_serve.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student_serve.common.ErrorCode;
import com.student_serve.exception.BusinessException;
import com.student_serve.model.entity.Notice;
import com.student_serve.service.NoticeService;
import com.student_serve.mapper.NoticeMapper;
import com.sun.security.auth.UnixPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @author yang99977
 * @description 针对表【notice(公告表)】的数据库操作Service实现
 * @createDate 2024-03-25 19:12:40
 */
@Slf4j
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice>
        implements NoticeService {

    /**
     * 创建通知
     *
     * @param notice
     * @return
     */
    @Override
    public Notice createNotice(Notice notice) {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("noticeTitle", notice.getNoticeTitle());

        Notice notice1 = this.baseMapper.selectOne(queryWrapper);
        if (notice1 != null) {
            throw new BusinessException(ErrorCode.EXIST_DATA_ERROR, "该标题已经存在");
        }

        //
        int result = this.baseMapper.insert(notice);
        if (result == 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        return notice;
    }

    /**
     * 检索通知
     *
     * @param noticeTitle
     * @return
     */
    @Override
    public List<Notice> queryNotice(Notice notice) {
        String noticeTitle = notice.getNoticeTitle();
        Date noticeTime = notice.getNoticeTime();

        //
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(noticeTitle)) {
            queryWrapper.like("noticeTitle", noticeTitle);
        }

        if(!ObjectUtils.isEmpty(noticeTime)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 使用java.text.SimpleDateFormat类
            String dateString = sdf.format(noticeTime); // 使用java.text.SimpleDateFormat类
            queryWrapper.like("noticeTime",dateString);
        }

        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 更新通知
     * @param notice
     * @return
     */
    @Override
    public Notice updateNotice(Notice notice){
        UpdateWrapper<Notice> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("noticeContent",notice.getNoticeContent())
                .eq("noticeTitle",notice.getNoticeTitle());

        //
        int result = this.baseMapper.update(null,updateWrapper);
        if (result == 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        return notice;
    }

    /**
     * 删除通知
     * @param notice
     * @return
     */
    @Override
    public Notice deleteNotice(Notice notice){
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("noticeTitle",notice.getNoticeTitle());

        //
        int result = this.baseMapper.delete(queryWrapper);
        if (result == 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        return notice;
    }
}




