package top.alexmmd.dog.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import top.alexmmd.dog.dao.WechatAppInfoDao;
import top.alexmmd.dog.service.WechatAppInfoService;

/**
 * 微信应用信息管理(WechatAppInfo)表服务实现类
 *
 * @author makejava
 * @since 2022-10-11 09:01:59
 */
@Service
public class WechatAppInfoServiceImpl implements WechatAppInfoService {

    @Resource
    private WechatAppInfoDao wechatAppInfoDao;

}
