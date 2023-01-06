package top.alexmmd.dog.service.impl;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.alexmmd.dog.dao.LoginAccountDao;
import top.alexmmd.dog.domain.entity.LoginAccount;
import top.alexmmd.dog.service.ILoginAccountService;

/**
 * (LoginAccount)表服务实现类
 *
 * @author makejava
 * @since 2022-10-11 09:02:02
 */
@Service
@Slf4j
public class LoginAccountServiceImpl implements ILoginAccountService {

    @Resource
    private LoginAccountDao loginAccountDao;

    @Override
    public void createLoginAccount(LoginAccount loginAccount) {
        loginAccountDao.insert(loginAccount);
    }
}
