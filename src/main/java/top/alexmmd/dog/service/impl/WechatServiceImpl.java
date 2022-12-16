package top.alexmmd.dog.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.alexmmd.dog.domain.dto.WechatUserInfoDTO;
import top.alexmmd.dog.service.IWechatService;

/**
 * @author wangyonghui
 * @date 2022年12月16日 14:49:00
 */
@Service
@Slf4j
public class WechatServiceImpl implements IWechatService {

    public static final String WECHAT_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";

    @Value("${wechat.appId}")
    private String appId;

    @Value("${wechat.appSecret}")
    private String appSecret;

    @Override
    public WechatUserInfoDTO wechatLogin(String code) {
        HttpRequest request = HttpUtil.createGet(WECHAT_LOGIN_URL);
        request.form("appid", appId);
        request.form("secret", appSecret);
        request.form("js_code", code);
        request.form("grant_type", "authorization_code");
        HttpResponse httpResponse = request.execute();
        String body = httpResponse.body();
        log.info("微信登录返回结果：{}", body);
        JSONObject jsonObject = new JSONObject(body);
        if (StrUtil.isNotBlank(jsonObject.getStr("openid"))) {
            return WechatUserInfoDTO.builder()
                    .openId(jsonObject.getStr("openid"))
                    .unionId(jsonObject.getStr("unionid"))
                    .build();
        }
        return null;
    }
}
