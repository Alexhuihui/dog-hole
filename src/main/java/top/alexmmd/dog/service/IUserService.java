package top.alexmmd.dog.service;

import top.alexmmd.dog.domain.dto.WechatRegisterDTO;
import top.alexmmd.dog.domain.dto.WechatUserInfoDTO;
import top.alexmmd.dog.domain.entity.User;
import top.alexmmd.dog.domain.vo.UserVO;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2022-10-11 09:02:02
 */
public interface IUserService {

    /**
     * 根据账户查询用户对象
     *
     * @param account 账户
     * @return 用户信息
     */
    UserVO queryUserByAccount(String account);

    void createUser(User user);

    void updateUser(User user);

    void deleteUser(String username);

    void changePassword(String oldPassword, String newPassword);

    boolean userExists(String username);

    User loadUserByUsername(String username);

    Long wechatRegister(WechatRegisterDTO wechatRegisterDTO);
}
