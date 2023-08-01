package top.alexmmd.dog.service.impl;

import static top.alexmmd.dog.enums.AccountTypeEnum.EMAIL;
import static top.alexmmd.dog.enums.AccountTypeEnum.WECHAT;
import static top.alexmmd.dog.enums.AppCodeEnum.DOG_HOLE;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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
import top.alexmmd.dog.service.IEmailService;
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
    private IEmailService emailService;

    @Resource
    private IWechatService wechatService;

    @Resource
    private ILoginAccountService loginAccountService;

    @Resource
    private UserDao userDao;

    // 缓存邮箱和验证码的映射关系
    private Map<String, String> verificationCodeCache = new HashMap<>();

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

    @Override
    public Long emailRegister(String email) {
        Long uid = IdUtil.getSnowflakeNextId();
        this.createUser(User.builder()
                .nickName(email)
                .uid(uid)
                .status(VALID)
                .createTime(DateUtil.date())
                .build());
        loginAccountService.createLoginAccount(LoginAccount.builder()
                .uid(uid)
                .accountId(IdUtil.getSnowflakeNextId())
                .account(email)
                .accountType(EMAIL.getValue())
                .appCode(DOG_HOLE.name())
                .status(VALID)
                .createTime(DateUtil.date())
                .build());
        userDao.insertRole(Role.builder()
                .uid(uid)
                .name(ROLE_USER)
                .build());
        return uid;
    }

    @Override
    public void sendVerificationCode(String toEmail) {
        String subject = "登录验证码";
        String verificationCode = generateVerificationCode();
        String body = generateEmailBody(verificationCode);
        // 缓存邮箱和验证码的映射关系，可以使用数据库、内存缓存或分布式缓存等
        verificationCodeCache.put(toEmail, verificationCode);
        emailService.sendEmail(toEmail, subject, body);
    }

    @Override
    public boolean verifyEmail(String email, String inputCode) {
        String cachedCode = verificationCodeCache.get(email);
        return cachedCode != null && cachedCode.equals(inputCode);
    }

    private String generateEmailBody(String code) {
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("尊敬的用户，您好！\n\n");
        emailContent.append("欢迎使用我们的服务。\n");
        emailContent.append("您的登录验证码是：").append(code).append("\n");
        emailContent.append("请在页面输入此验证码完成登录。\n\n");
        emailContent.append("感谢您的使用！\n\n");
        emailContent.append("祝您有个愉快的一天！\n\n");
        emailContent.append("此邮件为系统自动发送，请勿回复。\n");
        return emailContent.toString();
    }

    // 生成随机验证码
    private String generateVerificationCode() {
        int length = 6; // 验证码长度
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // 生成0到9之间的随机数字
            sb.append(digit);
        }

        return sb.toString();
    }

}
