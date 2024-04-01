package com.student_serve.service;

import com.student_serve.model.dto.archive.ArchiveQueryRequest;
import com.student_serve.model.entity.Archive;
import com.baomidou.mybatisplus.extension.service.IService;
import com.student_serve.model.entity.User;
import com.student_serve.model.vo.ArchiveVO;

import java.io.Serializable;
import java.util.List;

/**
* @description 针对表【archive(档案)】的数据库操作Service
* @createDate 2024-03-25 19:12:41
*/
public interface ArchiveService extends IService<Archive> {
    /**
     * 创建档案
     * @param user
     * @return
     */
    Boolean createArchive(User user);

    /**
     * 得到档案
     * @param userAccount
     * @return
     */
    Archive getArchive(String userAccount);

    /**
     * 更新档案
     * @param archive
     * @return
     */
    Archive updateArchive(Archive archive);

    /**
     * 查询档案
     * @param archiveQueryRequest
     * @return
     */
    List<ArchiveVO> queryArchive(ArchiveQueryRequest archiveQueryRequest);

    /**
     * 删除档案
     * @return
     */
    Archive deleteArchive(String userAccount);
}
