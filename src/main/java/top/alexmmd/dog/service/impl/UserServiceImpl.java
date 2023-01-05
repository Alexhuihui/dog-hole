package top.alexmmd.dog.service.impl;

import static top.alexmmd.dog.enums.AccountTypeEnum.WECHAT;
import static top.alexmmd.dog.enums.AppCodeEnum.DOG_HOLE;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.alexmmd.dog.dao.UserDao;
import top.alexmmd.dog.domain.dto.WechatRegisterDTO;
import top.alexmmd.dog.domain.dto.WechatUserInfoDTO;
import top.alexmmd.dog.domain.entity.LoginAccount;
import top.alexmmd.dog.domain.entity.Role;
import top.alexmmd.dog.domain.entity.User;
import top.alexmmd.dog.domain.vo.UserVO;
import top.alexmmd.dog.service.ILoginAccountService;
import top.alexmmd.dog.service.IUserService;
import top.alexmmd.dog.service.IWechatService;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2022-10-11 09:02:02
 */
@Service
public class UserServiceImpl implements IUserService {

    public static final int VALID = 1;
    public static final String ROLE_USER = "ROLE_USER";
    @Resource
    private IWechatService wechatService;

    @Resource
    private ILoginAccountService loginAccountService;

    @Resource
    private UserDao userDao;

    @Override
    public UserVO queryUserByAccount(String account) {
        return this.userDao.queryUserByAccount(account);
    }

    @Override
    public void createUser(User user) {
        userDao.insert(user);
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    @Override
    public User loadUserByUsername(String username) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long wechatRegister(WechatRegisterDTO wechatRegisterDTO) {
        WechatUserInfoDTO wechatUserInfoDTO = wechatService.wechatLogin(
                wechatRegisterDTO.getCode());
        Assert.notNull(wechatUserInfoDTO, "微信code解析失败");
        Long uid = IdUtil.getSnowflakeNextId();
        this.createUser(User.builder()
                .avatarUrl(wechatRegisterDTO.getAvatarUrl())
                .nickName(wechatRegisterDTO.getNickName())
                .unionId(wechatUserInfoDTO.getUnionId())
                .uid(uid)
                .status(VALID)
                .build());
        loginAccountService.createLoginAccount(LoginAccount.builder()
                .uid(uid)
                .accountId(IdUtil.getSnowflakeNextId())
                .account(wechatUserInfoDTO.getOpenId())
                .accountType(WECHAT.getValue())
                .appCode(DOG_HOLE.name())
                .status(VALID)
                .build());
        userDao.insertRole(Role.builder()
                .uid(uid)
                .name(ROLE_USER)
                .build());
        return uid;
    }
}