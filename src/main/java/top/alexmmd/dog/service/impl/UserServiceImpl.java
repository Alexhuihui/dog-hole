package top.alexmmd.dog.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import top.alexmmd.dog.dao.UserDao;
import top.alexmmd.dog.domain.dto.WechatUserInfoDTO;
import top.alexmmd.dog.domain.entity.User;
import top.alexmmd.dog.domain.vo.UserVO;
import top.alexmmd.dog.service.IUserService;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2022-10-11 09:02:02
 */
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserDao userDao;

    @Override
    public UserVO queryUserByAccount(WechatUserInfoDTO account) {
        return this.userDao.queryUserByAccount(account.getOpenId());
    }

    @Override
    public void createUser(User user) {

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
}
