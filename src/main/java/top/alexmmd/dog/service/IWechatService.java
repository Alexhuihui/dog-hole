package top.alexmmd.dog.service;

import top.alexmmd.dog.domain.dto.WechatUserInfoDTO;

/**
 * @author wangyonghui
 * @date 2022年12月16日 10:14:00
 */
public interface IWechatService {

    WechatUserInfoDTO wechatLogin(String code);

}
