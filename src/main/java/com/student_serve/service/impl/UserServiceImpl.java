package com.student_serve.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import static com.student_serve.constant.UserConstant.*;

import com.student_serve.common.ErrorCode;
import com.student_serve.exception.BusinessException;
import com.student_serve.model.dto.user.UserArchiveRequest;
import com.student_serve.model.dto.user.UserArchiveUpdateRequest;
import com.student_serve.model.dto.user.UserRegisterRequest;
import com.student_serve.model.dto.user.UserUpdatePassRequest;
import com.student_serve.model.entity.Archive;
import com.student_serve.model.entity.Rewardpunishinfo;
import com.student_serve.model.entity.User;
import com.student_serve.model.vo.UserArchiveVO;
import com.student_serve.service.ArchiveService;
import com.student_serve.service.RewardpunishinfoService;
import com.student_serve.service.UserService;
import com.student_serve.mapper.UserMapper;
import com.student_serve.utils.ConvertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author yang99977
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2024-03-25 19:12:41
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Resource
    private ArchiveService archiveService;

    @Resource
    private RewardpunishinfoService rewardpunishinfoService;


    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        System.out.println("userObj = " + userObj);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getUserRole().equals("ban")) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
//        long userId = currentUser.getId();
//        currentUser = this.getById(userId);
//        if (currentUser == null) {
//            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
//        }
        return currentUser;
    }

    /**
     * 进行登录
     */
    @Override
    public User userLogin(User user, HttpServletRequest request) {
        String userAccount = user.getUserAccount();
        String userPassword = user.getUserPassword();

        if (userPassword.length() < 8 || userPassword.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度必须在8-20字符");
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount).eq("userPassword", userPassword);

        User user1 = this.baseMapper.selectOne(queryWrapper);
        if (user1 == null || user1.getUserRole().equals("ban")) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "账号或密码错误");
        }

        // 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, user);

        return user1;
    }

    /**
     * 进行注册
     *
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public User userRegister(UserRegisterRequest user) {
        String userAccount = user.getUserAccount();
        String userName = user.getUserName();
        String checkRePassword = user.getCheckRePassword();
        String userPassword = user.getUserPassword();
        String cardId = user.getCardId();
        String userRole = user.getUserRole();
        String phone = user.getPhone();
        String department = user.getDepartment();
        String classes = user.getClasses();

        int len = 9;
        len += userRole.equals("student")?2:0;

        if (userAccount.length() != len) {
            if(len == 11){
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号格式错误,学生学号需要11位");
            }else{
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号格式错误,教师工号需要9位");
            }
        }
        if (userPassword.length() < 8 || userPassword.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度必须在8-20字符");
        }
        if (!userPassword.equals(checkRePassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码和确认密码不一致");
        }

        // 姓名
        if (userName.length() < 2 || userName.length() > 6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "姓名长度必须在2-6位中");
        }

        // 身份证号
        if(cardId.length() != 18){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "身份证号格式不正确,必须为18位");
        }
        // 手机号
        if(phone.length() != 11){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "手机号格式不正确,必须为11位");
        }

        // 查找是否存在账号
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        User user1 = this.baseMapper.selectOne(queryWrapper);
        if (user1 != null) {
            throw new BusinessException(ErrorCode.EXIST_DATA_ERROR, "该账号已经存在");
        }

        // 查找是否存在身份证号
        queryWrapper.clear();
        queryWrapper.eq("cardId",cardId);
        user1 = this.baseMapper.selectOne(queryWrapper);
        if (user1 != null) {
            throw new BusinessException(ErrorCode.EXIST_DATA_ERROR, "该身份证号已经存在");
        }

        // 查找是否存在手机号
        queryWrapper.clear();
        queryWrapper.eq("phone",phone);
        user1 = this.baseMapper.selectOne(queryWrapper);
        if (user1 != null) {
            throw new BusinessException(ErrorCode.EXIST_DATA_ERROR, "该手机号已经存在");
        }

        User user2 = ConvertUtil.VoToEntity(user, User.class);

//        queryWrapper.eq("userPassword",userPassword)
//                .eq("cardId",cardId)
//                .eq("userRole",userRole)
//                .eq("phone",phone)
//                .eq("department",department)
//                .eq("classes",classes);
        // 进行注册
        int result = this.baseMapper.insert(user2);
        Boolean flag = false;

        // TODO 1 是否创建档案
        // 创建档案
//        if (result != 0 && userRole.equals("student")) {
//            flag = archiveService.createArchive(user2);
//        }
        if (result != 0 ) {
            flag = true;
        }
        if(userRole.equals("student")){
            flag = archiveService.createArchive(user2);
        }


        return flag == false ? null : user2;
    }

    /**
     * 更新密码
     *
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public User updatePass(UserRegisterRequest user) {
        String checkRePassword = user.getCheckRePassword();
        String userPassword = user.getUserPassword();
        String cardId = user.getCardId();

        if (cardId.length() != 18) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "身份证号格式错误,必须为18位");
        }
        if (userPassword.length() < 8 || userPassword.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度必须在8-20字符");
        }
        if (!userPassword.equals(checkRePassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码和确认密码不一致");
        }

        // 查找是否存在账号
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",user.getUserAccount())
                .eq("userName",user.getUserName())
                .eq("cardId",user.getCardId());
        User user1 = this.baseMapper.selectOne(queryWrapper);
        if (user1 == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"没有此用户");
        }

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("userPassword", userPassword).eq("userAccount", user.getUserAccount());
        int result = this.baseMapper.update(null, updateWrapper);

        return result == 0 ? null : ConvertUtil.VoToEntity(user, User.class);
    }

    /**
     * 更新信息
     *
     * @return
     */
    @Override
    public User updateUser(User user) {

        // 手机号
        if(user.getPhone().length() != 11){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "手机号格式不正确,必须为11位");
        }
        //
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("department", user.getDepartment())
                .set("classes", user.getClasses())
                .set("phone", user.getPhone())
                .set("job", user.getJob())
                .set("duty", user.getDuty())
                .set("unity", user.getUnity())
                .eq("userAccount", user.getUserAccount());

        int result = this.baseMapper.update(null, updateWrapper);
        if (result == 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return user;
    }

    /**
     * 得到学号根据班级
     *
     * @param classes
     * @return
     */
    @Override
    public List<String> getUserAccountByClasses(String classes) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("classes", classes).eq("userRole", "student");
        List<User> li = this.baseMapper.selectList(queryWrapper);

        // 将学号储存进去
        List<String> result = new ArrayList<String>();
        for (User u : li) {
            result.add(u.getUserAccount());
        }

        return result;
    }

    /**
     * 得到用户信息通过身份
     *
     * @param userRole
     * @return
     */
    @Override
    public List<User> getUserByRole(String userRole) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userRole", userRole);

        List<User> result = this.baseMapper.selectList(queryWrapper);
        return result;
    }

    /**
     * 得到学生
     *
     * @param user
     * @return
     */
    @Override
    public List<User> queryPostStudent(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userRole", "student");

        List<User> result = this.baseMapper.selectList(queryWrapper);

        return result;
    }

    /**
     * 得到用户
     *
     * @param user
     * @return
     */
    @Override
    public List<User> queryUser(User user) {
        String userAccount = user.getUserAccount();
        String userName = user.getUserName();
        String cardId = user.getCardId();
        String userRole = user.getUserRole();
        String phone = user.getPhone();
        String classes = user.getClasses();

        String job = user.getJob();
        String duty = user.getDuty();
        String unity = user.getUnity();

        //
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(userAccount)) {
            queryWrapper.like("userAccount", userAccount);
        }
        if (!StringUtils.isEmpty(userName)) {
            queryWrapper.like("userName", userName);
        }
        if (!StringUtils.isEmpty(cardId)) {
            queryWrapper.like("cardId", cardId);
        }
        if (!StringUtils.isEmpty(userRole)) {
            queryWrapper.eq("userRole", userRole);
        }
        if (!StringUtils.isEmpty(phone)) {
            queryWrapper.like("phone", phone);
        }
        if (!StringUtils.isEmpty(classes)) {
            queryWrapper.like("classes", classes);
        }

        return this.baseMapper.selectList(queryWrapper);
    }



    /**
     * 删除用户
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public User deleteUser(User user) {
        // 得到数据
        String userAccount = user.getUserAccount();

        // TODO 2 是否删除档案
        // 删除档案
//        archiveService.deleteArchive(userAccount);
        if(user.getUserRole().equals("student")){
            archiveService.deleteArchive(userAccount);
        }

        // 删除PR
        rewardpunishinfoService.deletePR(userAccount);

        // 删除学生
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        int result = this.baseMapper.delete(queryWrapper);
        if (result == 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"删除失败");
        }
        return user;
    }

    /**
     * 导入用户
     * @param userList
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean importData(List<User> userList){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userRole","student");
        queryWrapper.select("MAX(userAccount) as maxAccount");
        Map<String, Object> result = this.baseMapper.selectMaps(queryWrapper).get(0);
        String maxAccountStr = (String) result.get("maxAccount");


        Long maxAccount = Long.valueOf(maxAccountStr);

        System.out.println(maxAccount);

        int len = userList.size();
        for(int i = 0; i<len; ++i){
            String userName = userList.get(i).getUserName();
            String cardId = userList.get(i).getCardId();
            String phone = userList.get(i).getPhone();

            // 姓名
            if (userName.length() < 2 || userName.length() > 6) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "姓名长度必须在2-6位中");
            }
            // 身份证号
            if(cardId.length() != 18){
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "身份证号格式不正确,必须为18位");
            }
            System.out.println(phone);
            // 手机号
            if(!StringUtils.isEmpty(phone) && phone.length() != 11){
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "手机号格式不正确,必须为11位");
            }

            //
            String userAccount = String.valueOf((maxAccount + i + 1));
            String userPassword = cardId.substring(cardId.length() - 8);

            User u = new User();
            u.setUserAccount(userAccount);
            u.setUserName(userName);
            u.setUserPassword(userPassword);
            u.setCardId(cardId);
            u.setPhone(phone);
            u.setUserRole("student");
            u.setDepartment("");
            u.setClasses("");

            try{
                this.baseMapper.insert(u);
                archiveService.createArchive(u);
            }catch(Exception e){
                throw new BusinessException(ErrorCode.OPERATION_ERROR,"导入失败,可能是身份证号重复或者系统错误,请重新检查");
            }
        }
        return true;
    }

    /**
     * 内部更新密码
     * @return
     */
    @Override
    public User updatePassIn(UserUpdatePassRequest userUpdatePassRequest){
        String userAccount = userUpdatePassRequest.getUserAccount();
        String userPassword = userUpdatePassRequest.getUserPassword();
        String newPassword = userUpdatePassRequest.getNewPassword();
        String checkRePassword = userUpdatePassRequest.getCheckRePassword();

        if(userPassword.length() < 8 || userPassword.length() > 20){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码长度必须为8-20位");
        }
        if(newPassword.length() < 8 || newPassword.length() > 20){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"新密码长度必须为8-20位");
        }
        if(!newPassword.equals(checkRePassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"新密码与确认密码不一致");
        }
        if(newPassword.equals(userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"新密码与原密码一致");
        }

        // 查找
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount)
                .eq("userPassword",userPassword);
        User user = this.baseMapper.selectOne(queryWrapper);
        if(user == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"原密码不正确");
        }

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("userPassword",newPassword).eq("userAccount",userAccount);
        int result = this.baseMapper.update(null,updateWrapper);
        if(result == 0){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"操作失败");
        }
        return user;
    }

    /**
     * 得到用户
     *
     * @param user
     * @return
     */
    @Override
    public List<UserArchiveVO> queryUserArchive(User user) {

        return this.baseMapper.queryUserArchive(user);
    }

    /**
     * 更新学生信息档案
     * @param userArchiveRequest
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserArchive(UserArchiveUpdateRequest userArchiveUpdateRequest){
        String userAccount = userArchiveUpdateRequest.getUserAccount();
        String userName = userArchiveUpdateRequest.getUserName();
        String cardId = userArchiveUpdateRequest.getCardId();
        String phone = userArchiveUpdateRequest.getPhone();
        String department = userArchiveUpdateRequest.getDepartment();
        String classes = userArchiveUpdateRequest.getClasses();
        String archiveId = userArchiveUpdateRequest.getArchiveId();
        String sex = userArchiveUpdateRequest.getSex();
        String address = userArchiveUpdateRequest.getAddress();
        String health = userArchiveUpdateRequest.getHealth();
        String origin = userArchiveUpdateRequest.getOrigin();
        String nation = userArchiveUpdateRequest.getNation();
        Date createTime = userArchiveUpdateRequest.getCreateTime();


        if (userAccount.length() != 11) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号格式错误,学生学号需要11位");
        }
        // 姓名
        if (userName.length() < 2 || userName.length() > 6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "姓名长度必须在2-6位中");
        }
        // 身份证号
        if(cardId.length() != 18){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "身份证号格式不正确,必须为18位");
        }
        // 手机号
        if(phone.length() != 11){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "手机号格式不正确,必须为11位");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        // 查找是否存在身份证号
//        queryWrapper.eq("cardId",cardId);
//        User user1 = this.baseMapper.selectOne(queryWrapper);
//        if (user1 != null) {
//            throw new BusinessException(ErrorCode.EXIST_DATA_ERROR, "该身份证号已经存在");
//        }

        // 查找是否存在手机号
//        queryWrapper.clear();
//        queryWrapper.eq("phone",phone);
//        user1 = this.baseMapper.selectOne(queryWrapper);
//        if (user1 != null) {
//            throw new BusinessException(ErrorCode.EXIST_DATA_ERROR, "该手机号已经存在");
//        }

        // 更新学生信息
        UpdateWrapper<User> updateWrapper1 = new UpdateWrapper<>();
        updateWrapper1.set("userName",userName)
                .set("department",department)
                .set("classes",classes)
                .set("cardId",cardId)
                .set("phone",phone)
                .eq("userAccount",userAccount);
        this.baseMapper.update(null,updateWrapper1);

        // 更新档案
        Archive archive = new Archive();
        UpdateWrapper<Archive> updateWrapper2 = new UpdateWrapper<>();
        updateWrapper2.set("sex",sex)
                .set("address",address)
                .set("health",health)
                .set("origin",origin)
                .set("nation",nation)
                .set("createTime",createTime)
                .eq("archiveId",archiveId);
        archiveService.update(null,updateWrapper2);
        return true;
    }
}




