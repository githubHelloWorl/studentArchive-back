package com.student_serve.mapper;

import com.student_serve.model.dto.archive.ArchiveQueryRequest;
import com.student_serve.model.entity.Archive;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student_serve.model.vo.ArchiveVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author yang99977
* @description 针对表【archive(档案)】的数据库操作Mapper
* @createDate 2024-03-25 19:12:41
* @Entity com.student_serve.model.entity.Archive
*/
@Mapper
public interface ArchiveMapper extends BaseMapper<Archive> {
    public List<ArchiveVO> queryArchive(@Param("form")ArchiveQueryRequest archiveQueryRequest);
}




