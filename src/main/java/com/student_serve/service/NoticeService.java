package com.student_serve.service;

import com.student_serve.model.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author yang99977
* @description 针对表【notice(公告表)】的数据库操作Service
* @createDate 2024-03-25 19:12:40
*/
public interface NoticeService extends IService<Notice> {

    /**
     * 创建通知
     * @param notice
     * @return
     */
    Notice createNotice(Notice notice);

    /**
     * 检索通知
     * @param notice
     * @return
     */
    List<Notice> queryNotice(Notice notice);

    /**
     *
     */
    Notice updateNotice(Notice notice);

    /**
     *
     */
    Notice deleteNotice(Notice notice);
}
