package top.alexmmd.dog.dao;

import org.apache.ibatis.annotations.Param;
import top.alexmmd.dog.domain.entity.WechatAppInfo;

/**
 * 微信应用信息管理(WechatAppInfo)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-11 08:50:43
 */
public interface WechatAppInfoDao {


    WechatAppInfo queryByAppCode(@Param("appCode") String appCode);
}

