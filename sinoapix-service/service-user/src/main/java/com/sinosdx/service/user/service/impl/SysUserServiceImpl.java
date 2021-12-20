package com.sinosdx.service.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sinosdx.common.base.context.ThreadContext;
import com.sinosdx.common.base.result.R;
import com.sinosdx.service.user.constants.Constants;
import com.sinosdx.service.user.constants.SysConstant;
import com.sinosdx.service.user.consumer.AuthenticationServiceFeign;
import com.sinosdx.service.user.dao.entity.*;
import com.sinosdx.service.user.dao.mapper.*;
import com.sinosdx.service.user.result.ResultCodeEnum;
import com.sinosdx.service.user.service.SysRoleOrgService;
import com.sinosdx.service.user.service.SysRoleService;
import com.sinosdx.service.user.service.SysUserRoleService;
import com.sinosdx.service.user.service.SysUserService;
import com.sinosdx.service.user.service.dto.SysRegisterDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Wendy
 * @date 2021-6-18
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysLoginMapper sysLoginMapper;

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private AuthenticationServiceFeign authServiceFeign;

    @Resource
    private SysRoleDao sysRoleDao;

    @Resource
    private SysRoleOrgService sysRoleOrgService;

    @Resource
    private SysOrgMapper sysOrgMapper;

    @Resource
    private SysUserOrgDao sysUserOrgDao;

    @Resource
    private SysUserRoleDao sysUserRoleDao;

    @Autowired
    private SysRoleService sysRoleService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * 用户注册
     *
     * @param sysRegisterDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Object> userRegister(SysRegisterDTO sysRegisterDTO) {
        // 验证参数
//        if (StringUtils.isEmpty(sysRegisterDTO.getUsername())) {
//            return R.fail(ResultCodeEnum.USER_REGISTER_USERNAME_EMPTY);
//        }
        if (StringUtils.isEmpty(sysRegisterDTO.getMobile())) {
            return R.fail(ResultCodeEnum.USER_REGISTER_MOBILE_EMPTY);
        }
        if (StringUtils.isEmpty(sysRegisterDTO.getPassword())) {
            return R.fail(ResultCodeEnum.USER_REGISTER_PASSWORD_EMPTY);
        }
        if (StringUtils.isEmpty(sysRegisterDTO.getOrgName())) {
            return R.fail(ResultCodeEnum.ORGANIZATION_NAME_EMPTY);
        }

        // 查询存量用户
        SysUser sysUser = sysUserMapper.queryByMobile(sysRegisterDTO.getMobile());
        if (null != sysUser) {
            return R.fail(ResultCodeEnum.USER_HAS_EXISTED);
        }

        // 创建新组织（注册时填写的组织名不能已存在，注册时创建新组织）
        SysOrg sysOrg = sysOrgMapper.selectOne(new LambdaQueryWrapper<SysOrg>().eq(SysOrg::getName, sysRegisterDTO.getOrgName()));
        if (null != sysOrg) {
            return R.fail(ResultCodeEnum.ORGANIZATION_IS_EXIST);
        }
        sysOrg = new SysOrg();
        sysOrg.setName(sysRegisterDTO.getOrgName());
        sysOrg.setCreationDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
        sysOrgMapper.insert(sysOrg);

        // 保存用户
        sysUser = new SysUser();
        BeanUtils.copyProperties(sysRegisterDTO, sysUser);
        sysUser.setOrgId(sysOrg.getId());
        sysUserMapper.insert(sysUser);
        sysOrg.setCreationBy(sysUser.getId());
        sysOrgMapper.updateById(sysOrg);

        // 添加用户组织关联
        SysUserOrgEntity sysUserOrg = new SysUserOrgEntity();
        sysUserOrg.setUserId(sysUser.getId().longValue());
        sysUserOrg.setOrgId(sysOrg.getId().longValue());
        sysUserOrgDao.insert(sysUserOrg);

        // 添加组织默认角色
        sysRoleOrgService.addSysRoleOrgEntityForOrg(sysOrg.getId().longValue(), new Long[]{1L, 2L, 3L});

        // 对用户的密码进行加密
        String encode = new BCryptPasswordEncoder().encode(sysRegisterDTO.getPassword());
        // 保存用户登录信息
        SysLogin sysLogin = new SysLogin();
        sysLogin.setUserId(sysUser.getId());
        sysLogin.setAccount(sysUser.getAccount());
        sysLogin.setMobile(sysUser.getMobile());
        sysLogin.setPassword(encode);
        sysLogin.setCreationBy(sysUser.getId());
        sysLogin.setCreationDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
        sysLoginMapper.insert(sysLogin);

        // 授予用户默认组织管理员角色
        SysUserRoleEntity sysUserRole = new SysUserRoleEntity();
        sysUserRole.setUserId(sysUser.getId().longValue());
        SysRoleEntity sysRole = sysRoleDao.selectOne(new LambdaQueryWrapper<SysRoleEntity>()
                .eq(SysRoleEntity::getRoleName, SysConstant.ORG_MANAGER_ROLE_NAME).eq(SysRoleEntity::getParentId, 1L));
        sysUserRole.setRoleId(sysRole.getRoleId());
        sysUserRoleDao.insert(sysUserRole);

        return R.successDef(sysUser, "注册成功");
    }

    /**
     * 查询用户登录信息
     *
     * @param mobile
     * @return
     */
    @Override
    public R<Object> queryUserLoginInfo(String mobile) {
        List<Map<String, Object>> userLoginInfoList = sysUserMapper.queryUserLoginInfo(mobile);
        if (null == userLoginInfoList || userLoginInfoList.isEmpty()) {
            return R.fail(ResultCodeEnum.USER_NOT_EXIST);
        }

        Map<String, Object> userLoginInfo = userLoginInfoList.get(0);

        return R.success(userLoginInfo);
    }

    /**
     * 条件查询用户列表
     *
     * @param username
     * @param roleId
     * @param limit
     * @param offset
     * @return
     */
    @Override
    public R<Object> queryUserListByCondition(String username, Integer roleId, Integer limit, Integer offset) {
        if ((null != limit && limit < 0) || (null != offset && offset < 1)) {
            limit = null;
            offset = null;
        }
        List<Integer> userIdList = sysRoleService.queryAllUserIdListByRole(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
        List<Object> list = sysUserMapper.queryUserByCondition(username, roleId, limit, offset, userIdList);
        // 数据集合
        List<SysUser> userList = (List<SysUser>) list.get(0);
        // 数据总量
        Integer total = ((List<Integer>) list.get(1)).get(0);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userList", userList);
        resultMap.put("total", total);
        return R.success(resultMap);
    }

    /**
     * 根据用户id查询用户实体对象SysUser
     *
     * @param id
     * @return
     */
    @Override
    public R<Object> findUserById(Integer id) {
        return R.success(sysUserMapper.queryByUserId(id));
    }

    /**
     * 更新用户
     *
     * @param sysUser
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Object> updateUser(SysUser sysUser) {
        SysUser oldUser = sysUserMapper.selectById(sysUser.getId());
        if (null == oldUser) {
            return R.fail(ResultCodeEnum.USER_NOT_EXIST);
        }

        if (StringUtils.isNotEmpty(sysUser.getAccount())) {
            oldUser.setAccount(sysUser.getAccount());
        }
        if (StringUtils.isNotEmpty(sysUser.getUsername())) {
            oldUser.setUsername(sysUser.getUsername());
        }
        if (StringUtils.isNotEmpty(sysUser.getMobile())) {
            oldUser.setMobile(sysUser.getMobile());
        }
        if (StringUtils.isNotEmpty(sysUser.getEmail())) {
            oldUser.setEmail(sysUser.getEmail());
        }
        if (null != sysUser.getGender()) {
            oldUser.setGender(sysUser.getGender());
        }
        if (null != sysUser.getEnabled()) {
            oldUser.setEnabled(sysUser.getEnabled());
        }
        oldUser.setLastUpdatedBy(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
        oldUser.setLastUpdateDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
        sysUserMapper.updateById(oldUser);

        if (null != sysUser.getRoleId()) {
            sysUserRoleService.saveOrUpdate(sysUser.getId().longValue(), Collections.singletonList(sysUser.getRoleId().longValue()));
            oldUser.setRoleId(sysUser.getRoleId());
        }

        return R.success(oldUser);
    }

    /**
     * 根据姓名和手机号查询用户
     *
     * @param username
     * @param phone
     * @return
     */
    @Override
    public R<Object> selectUserByPhoneAndUsername(String username, String phone) {
        SysUser sysUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("mobile", phone));
        return R.success(sysUser);
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return baseMapper.queryAllMenuId(userId);
    }

    @Override
    public List<String> queryAllPerms(Long userId) {
        return baseMapper.queryAllPerms(userId);
    }

    /**
     * 通过csp营销门户登录的用户获取中台token
     *
     * @param mobile
     * @return
     */
    @Override
    public R<Object> cspUserLogin(String mobile) {
        boolean userExist = true;
        SysUser sysUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("mobile", mobile));
        if (null == sysUser) {
            // 用户不存在，需要跳转到api管理注册页面
            return R.success(Collections.singletonMap("userExist", false));
        }

        // 用户已存在，免密登录
        JSONObject loginJson = new JSONObject();
        loginJson.put("username", mobile);
        loginJson.put("loginType", "csp2");
        R<JSONObject> objectR = authServiceFeign.userLogin(loginJson);
        if (objectR.notSuccess()) {
            log.error("免密登录失败");
            return R.fail(ResultCodeEnum.CREATE_JWT_ERROR);
        }

        JSONObject data = objectR.getData();
        data.put("userExist", true);

        return R.success(data);
    }

    /**
     * 更新用户登录时间
     *
     * @param mobile
     * @param updateBy
     * @return
     */
    @Override
    public R<Object> updateSysLogin(String mobile, Integer updateBy) {
        SysLogin sysLogin = sysLoginMapper.selectOne(new LambdaQueryWrapper<SysLogin>().eq(SysLogin::getMobile, mobile));
        if (null == sysLogin) {
            return R.fail(ResultCodeEnum.DATA_IS_WRONG);
        }

        sysLogin.setLastUpdatedBy(updateBy);
        sysLogin.setLastUpdateDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
        sysLoginMapper.updateById(sysLogin);

        return R.success();
    }

    /**
     * 管理员添加用户
     *
     * @param sysUser
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Object> addUserByManager(SysUser sysUser) {
        if (StringUtils.isEmpty(sysUser.getMobile())) {
            return R.fail(ResultCodeEnum.USER_REGISTER_MOBILE_EMPTY);
        }

        // 判断用户是否已存在
        SysUser oldUser = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getMobile, sysUser.getMobile()));
        if (null != oldUser) {
            return R.fail(ResultCodeEnum.USER_HAS_EXISTED);
        }

        Integer managerId = ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID);
        sysUser.setCreationBy(managerId);
        sysUser.setCreationDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
        sysUserMapper.insert(sysUser);

        // 添加用户组织关联
        SysUserOrgEntity sysUserOrg = new SysUserOrgEntity();
        sysUserOrg.setUserId(sysUser.getId().longValue());
        sysUserOrg.setOrgId(sysUser.getOrgId().longValue());
        sysUserOrgDao.insert(sysUserOrg);

        // 授予用户默认开发者角色
        SysUserRoleEntity sysUserRole = new SysUserRoleEntity();
        sysUserRole.setUserId(sysUser.getId().longValue());
        SysRoleEntity sysRole = sysRoleDao.selectOne(new LambdaQueryWrapper<SysRoleEntity>()
                .eq(SysRoleEntity::getRoleName, SysConstant.DEVELOPER_ROLE_NAME).eq(SysRoleEntity::getParentId, 1L));
        sysUserRole.setRoleId(sysRole.getRoleId());
        sysUserRoleDao.insert(sysUserRole);

        // 对默认密码进行加密
        String encode = new BCryptPasswordEncoder().encode(Base64.getEncoder().encodeToString(Constants.DEFAULT_PASSWORD.getBytes(StandardCharsets.UTF_8)));
        // 保存用户登录信息
        SysLogin sysLogin = new SysLogin();
        sysLogin.setUserId(sysUser.getId());
        sysLogin.setAccount(sysUser.getAccount());
        sysLogin.setMobile(sysUser.getMobile());
        sysLogin.setPassword(encode);
        sysLogin.setCreationBy(managerId);
        sysLogin.setCreationDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
        sysLoginMapper.insert(sysLogin);

        return R.successDef(null, "添加用户成功，请告知用户默认密码");
    }

    /**
     * 用户修改密码
     *
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @Override
    public R<Object> modifyUserPassword(String oldPwd, String newPwd) {
        // 检查新旧密码是否相同
        if (oldPwd.equals(newPwd)) {
            return R.fail(ResultCodeEnum.PASSWORD_SAME_AS_OLD);
        }

        Integer userId = ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID);
        SysLogin sysLogin = sysLoginMapper.selectOne(new LambdaQueryWrapper<SysLogin>().eq(SysLogin::getUserId, userId));
        sysLogin.setPassword(encoder.encode(newPwd));
        sysLogin.setPasswordResetTime(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
        sysLogin.setLastUpdatedBy(userId);
        sysLogin.setLastUpdateDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
        sysLoginMapper.updateById(sysLogin);

        return R.successDef(null, "密码修改成功");
    }
}