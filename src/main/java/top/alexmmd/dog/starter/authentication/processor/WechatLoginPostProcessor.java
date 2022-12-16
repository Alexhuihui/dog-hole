package top.alexmmd.dog.starter.authentication.processor;

import cn.hutool.json.JSONObject;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.stereotype.Component;
import top.alexmmd.dog.enums.LoginTypeEnum;
import top.alexmmd.dog.starter.authentication.token.WechatAuthenticationToken;

/**
 * @author wangyonghui
 * @date 2022年12月15日 16:42:00
 */
@Component
public class WechatLoginPostProcessor implements LoginPostProcessor {

    @Override
    public LoginTypeEnum getLoginTypeEnum() {
        return LoginTypeEnum.WECHAT;
    }

    @Override
    public AbstractAuthenticationToken wrapperRequest(JSONObject request, LoginTypeEnum loginTypeEnum) {
        String wechatCode = obtainWechatCode(request);
        wechatCode = (wechatCode != null) ? wechatCode.trim() : "";
        return WechatAuthenticationToken.unauthenticated(wechatCode);
    }

    private String obtainWechatCode(JSONObject request) {
        return request.getStr("wechatCode");
    }
}
