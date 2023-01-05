package top.alexmmd.dog.dao;

import top.alexmmd.dog.domain.entity.LoginAccount;

/**
 * (LoginAccount)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-11 08:50:43
 */
public interface LoginAccountDao {

    int insert(LoginAccount loginAccount);
}

