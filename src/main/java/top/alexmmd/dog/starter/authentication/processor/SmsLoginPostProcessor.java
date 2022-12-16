package top.alexmmd.dog.starter.authentication.processor;

import cn.hutool.json.JSONObject;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.stereotype.Component;
import top.alexmmd.dog.enums.LoginTypeEnum;

/**
 * @author wangyonghui
 * @date 2022年12月15日 16:41:00
 */
@Component
public class SmsLoginPostProcessor implements LoginPostProcessor{

    @Override
    public LoginTypeEnum getLoginTypeEnum() {
        return LoginTypeEnum.SMS;
    }

    @Override
    public AbstractAuthenticationToken wrapperRequest(JSONObject request, LoginTypeEnum loginTypeEnum) {
        return null;
    }
}
