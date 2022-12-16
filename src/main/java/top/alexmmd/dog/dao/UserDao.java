package top.alexmmd.dog.dao;

import org.apache.ibatis.annotations.Param;
import top.alexmmd.dog.domain.dto.WechatUserInfoDTO;
import top.alexmmd.dog.domain.vo.UserVO;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-11 08:50:43
 */
public interface UserDao {

    /**
     * 根据微信用户信息查询本系统用户信息
     *
     * @param account 账户
     * @return
     */
    UserVO queryUserByAccount(@Param("query") String account);
}

