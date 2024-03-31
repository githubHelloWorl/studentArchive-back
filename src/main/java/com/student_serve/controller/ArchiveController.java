package com.student_serve.controller;

import com.student_serve.common.BaseResponse;
import com.student_serve.common.ErrorCode;
import com.student_serve.common.ResultUtils;
import com.student_serve.exception.BusinessException;
import com.student_serve.model.dto.archive.ArchiveQueryRequest;
import com.student_serve.model.dto.user.UserRegisterRequest;
import com.student_serve.model.entity.Archive;
import com.student_serve.model.entity.User;
import com.student_serve.model.vo.ArchiveVO;
import com.student_serve.service.ArchiveService;
import com.student_serve.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.student_serve.constant.UserConstant.DUTY;
import static com.student_serve.constant.UserConstant.departAndClass;

@RequestMapping("/archive")
@Slf4j
@RestController
public class ArchiveController {
    @Resource
    private ArchiveService archiveService;

    /**
     * 得到档案
     *
     * @param userAccount
     * @return
     */
    @GetMapping("/getArchive")
    public BaseResponse<Archive> getArchive(@RequestParam("userAccount") String userAccount) {
        log.info("{} 查找档案", userAccount);

        // 判断
        if (StringUtils.isEmpty(userAccount)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 查找
        Archive archive = archiveService.getArchive(userAccount);

        return ResultUtils.success(archive);
    }

    /**
     * 更新档案
     * @param archive
     * @return
     */
    @PostMapping("/updateArchive")
    public BaseResponse<Archive> updateArchive(@RequestBody Archive archive){
        log.info("{} 更新档案",archive);

        // 判断
        String sex = archive.getSex();
        String address = archive.getAddress();
        String health = archive.getHealth();
        String origin = archive.getOrigin();
        String nation = archive.getNation();
        Date createTime = archive.getCreateTime();

        if(StringUtils.isAnyBlank(sex,address,health,origin,nation)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if(ObjectUtils.isEmpty(createTime)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        return ResultUtils.success(archiveService.updateArchive(archive));
    }

    /**
     * 查询档案
     * @param archiveQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/queryArchive")
    public BaseResponse<List<ArchiveVO>> queryArchive(@RequestBody ArchiveQueryRequest archiveQueryRequest, HttpServletRequest request){
        log.info("{} 查询档案",archiveQueryRequest);

        return ResultUtils.success(archiveService.queryArchive(archiveQueryRequest));
    }

    /**
     * 查询档案 仅管理员
     * @param request
     * @return
     */
//    @GetMapping("/queryArchive")
//    public BaseResponse<List<ArchiveVO>> queryArchive1(HttpServletRequest request){
//        log.info("查询档案");
//
//        return ResultUtils.success(archiveService.queryArchive1());
//    }
}
