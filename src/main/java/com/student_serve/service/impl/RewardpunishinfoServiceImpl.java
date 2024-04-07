package com.student_serve.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student_serve.common.ErrorCode;
import com.student_serve.exception.BusinessException;
import com.student_serve.model.dto.rewardPunishInfo.PRInfoQueryRequest;
import com.student_serve.model.entity.Notice;
import com.student_serve.model.entity.Rewardpunishinfo;
import com.student_serve.model.vo.PRInfoVO;
import com.student_serve.service.RewardpunishinfoService;
import com.student_serve.mapper.RewardpunishinfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author yang99977
 * @description 针对表【rewardpunishinfo(奖惩信息表)】的数据库操作Service实现
 * @createDate 2024-03-25 19:12:41
 */
@Service
public class RewardpunishinfoServiceImpl extends ServiceImpl<RewardpunishinfoMapper, Rewardpunishinfo>
        implements RewardpunishinfoService {

    /**
     * 创建惩戒信息
     *
     * @param rewardpunishinfo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Rewardpunishinfo createPR(Rewardpunishinfo rewardpunishinfo) {

        // 设置证书编号
        Date date = new Date();
        long t = date.getTime(); // 13位毫秒数
        // 创建日期格式化对象,在获取格式化对象时可以指定风格
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); //时间格式
        String archiveId = df.format(date);
        archiveId += rewardpunishinfo.getSid().substring(rewardpunishinfo.getSid().length() - 3);
        rewardpunishinfo.setFileId(archiveId);

//        QueryWrapper<Rewardpunishinfo> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("sid", rewardpunishinfo.getSid())
//                .eq("tid", rewardpunishinfo.getTid())
//                .eq("fileId", rewardpunishinfo.getFileId())
//                .eq("fileName", rewardpunishinfo.getFileName())
//                .eq("state", rewardpunishinfo.getState())
//                .eq("fileInfo", rewardpunishinfo.getFileInfo())
//                .eq("fileUnit", rewardpunishinfo.getFileUnit());

        int result = this.baseMapper.insert(rewardpunishinfo);
        if (result == 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }

        return rewardpunishinfo;
    }

    /**
     * 更新
     *
     * @param rewardpunishinfo
     * @return
     */
    @Override
    public Rewardpunishinfo updatePR(Rewardpunishinfo rewardpunishinfo) {
        UpdateWrapper<Rewardpunishinfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("fileName", rewardpunishinfo.getFileName())
                .set("fileTime", rewardpunishinfo.getFileTime())
                .set("fileUnit", rewardpunishinfo.getFileUnit())
                .set("stime", rewardpunishinfo.getStime())
                .eq("fileId", rewardpunishinfo.getFileId());

        //
        int result = this.baseMapper.update(null, updateWrapper);
        if (result == 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }

        return rewardpunishinfo;
    }

    /**
     * @param fileInfo
     * @return
     */
    @Override
    public List<Rewardpunishinfo> queryPR(String fileInfo) {

        QueryWrapper<Rewardpunishinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fileInfo", fileInfo);
        List<Rewardpunishinfo> result = this.baseMapper.selectList(queryWrapper);
        return result;
    }

    /**
     * 得到数据
     */
    @Override
    public List<PRInfoVO> queryPRByTeacher(PRInfoQueryRequest prInfoQueryRequest) {
        //
        String state = prInfoQueryRequest.getState();
        String userAccount = prInfoQueryRequest.getUserAccount();
        String fileInfo = prInfoQueryRequest.getFileInfo();
        String classes = prInfoQueryRequest.getClasses();

        //
        QueryWrapper<Rewardpunishinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fileInfo", fileInfo);
        if (!StringUtils.isEmpty(state)) {
            queryWrapper.eq("state", state);
        }
        if (!StringUtils.isEmpty(userAccount)) {
            queryWrapper.like("userAccount", userAccount);
        }
        if (!StringUtils.isEmpty(classes)) {
            queryWrapper.like("classes", classes);
        }

        return this.baseMapper.queryPRByTeacher(prInfoQueryRequest);
    }

    /**
     * 设置证书状态 审核教师/审核时间
     *
     * @param rewardpunishinfo
     * @return
     */
    public Rewardpunishinfo setPRState(Rewardpunishinfo rewardpunishinfo) {
        // 设置时间
        Date date = new Date();
//        long t = date.getTime(); // 13位毫秒数
//        // 创建日期格式化对象,在获取格式化对象时可以指定风格
//        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); //时间格式
//        String archiveId = df.format(date);

        UpdateWrapper<Rewardpunishinfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("state", rewardpunishinfo.getState())
                .set("tid", rewardpunishinfo.getTid())
                .set("checkTime",date)
                .eq("fileId", rewardpunishinfo.getFileId());

        int result = this.baseMapper.update(null, updateWrapper);
        if (result == 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return rewardpunishinfo;
    }

    /**
     * 撤回处分
     *
     * @param rewardpunishinfo
     * @return
     */
    public Rewardpunishinfo deletePunish(Rewardpunishinfo rewardpunishinfo) {
        QueryWrapper<Rewardpunishinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fileId", rewardpunishinfo.getFileId());

        int result = this.baseMapper.delete(queryWrapper);
        if (result == 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }

        return rewardpunishinfo;
    }

    /**
     * 删除信息
     */
    public Rewardpunishinfo deletePR(String userAccount){
        QueryWrapper<Rewardpunishinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sid",userAccount);
        int result = this.baseMapper.delete(queryWrapper);
        // TODO 3 删除信息是否报错
//        if(result == 0){
//            throw new BusinessException(ErrorCode.OPERATION_ERROR);
//        }
        return null;
    }

    /**
     * 得到PR
     * @param userAccount
     * @return
     */
    @Override
    public List<Rewardpunishinfo> getPRByuserAccount(String userAccount){

        //
        QueryWrapper<Rewardpunishinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sid",userAccount);
        List<Rewardpunishinfo> list = this.baseMapper.selectList(queryWrapper);

        return list;
    }
}




