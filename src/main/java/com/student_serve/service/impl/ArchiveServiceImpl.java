package com.student_serve.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student_serve.common.ErrorCode;
import com.student_serve.exception.BusinessException;
import com.student_serve.model.dto.archive.ArchiveQueryRequest;
import com.student_serve.model.entity.Archive;
import com.student_serve.model.entity.User;
import com.student_serve.model.vo.ArchiveVO;
import com.student_serve.service.ArchiveService;
import com.student_serve.mapper.ArchiveMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author yang99977
 * @description 针对表【archive(档案)】的数据库操作Service实现
 * @createDate 2024-03-25 19:12:41
 */
@Service
public class ArchiveServiceImpl extends ServiceImpl<ArchiveMapper, Archive>
        implements ArchiveService {
//    @Resource
//    private UserService userService;

    /**
     * 创建档案
     *
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean createArchive(User user) {

        String userAccount = user.getUserAccount();

        // 设置编号
        Date date = new Date();
        long t = date.getTime(); // 13位毫秒数
        // 创建日期格式化对象,在获取格式化对象时可以指定风格
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); //时间格式
        String archiveId = df.format(date);  // 获得从 1977 某个时间的到此的毫秒数
//        archiveId += String.valueOf(t).substring(9);
        archiveId += userAccount.substring(userAccount.length() - 3);

        // 设置档案
        Archive archive = new Archive();
        archive.setSid(userAccount);
        archive.setArchiveId(archiveId);

        // 执行操作
        int result = this.baseMapper.insert(archive);

        return result == 0 ? false : true;
    }

    /**
     * 得到档案
     *
     * @param userAccount
     * @return
     */
    @Override
    public Archive getArchive(String userAccount) {
        QueryWrapper<Archive> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sid", userAccount);

        // 得到档案
        Archive archive = this.baseMapper.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(archive)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        return archive;
    }

    /**
     * 更新档案
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Archive updateArchive(Archive archive) {
        //
        UpdateWrapper<Archive> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("archiveId", archive.getArchiveId());
        // 更新
//        int result = this.baseMapper.updateById(archive);
        int result = this.baseMapper.update(archive, updateWrapper);
        if (result == 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return archive;
    }

    /**
     * 查询档案
     *
     * @param archiveQueryRequest
     * @return
     */
//    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<ArchiveVO> queryArchive(ArchiveQueryRequest archiveQueryRequest) {
        String userAccount = archiveQueryRequest.getUserAccount();
        String userName = archiveQueryRequest.getUserName();
        String cardId = archiveQueryRequest.getCardId();
        String phone = archiveQueryRequest.getPhone();

        List<ArchiveVO> result = this.baseMapper.queryArchive(archiveQueryRequest);

//        if(result == 0){
//            throw new BusinessException(ErrorCode.OPERATION_ERROR);
//        }
        return result;
    }

    /**
     * 删除档案
     */
    @Transactional( rollbackFor = Exception.class)
    @Override
    public Archive deleteArchive(String userAccount){
        QueryWrapper<Archive> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sid",userAccount);
        int result = this.baseMapper.delete(queryWrapper);
        if(result == 0){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"删除档案出错");
        }
        return null;
    }
}




