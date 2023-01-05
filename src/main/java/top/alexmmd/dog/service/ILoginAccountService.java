package top.alexmmd.dog.service;

import top.alexmmd.dog.domain.entity.LoginAccount;

/**
 * (LoginAccount)表服务接口
 *
 * @author makejava
 * @since 2022-10-11 09:02:02
 */
public interface ILoginAccountService {

    void createLoginAccount(LoginAccount loginAccount);
}
