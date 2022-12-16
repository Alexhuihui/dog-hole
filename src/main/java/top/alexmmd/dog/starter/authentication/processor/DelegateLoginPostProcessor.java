package top.alexmmd.dog.starter.authentication.processor;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import top.alexmmd.dog.enums.LoginTypeEnum;

/**
 * @author wangyonghui
 * @date 2022年12月15日 16:30:00
 */
public class DelegateLoginPostProcessor implements LoginPostProcessor {

    private final static Collection<LoginPostProcessor> LOGIN_POST_PROCESSOR_LIST;

    static {
        LOGIN_POST_PROCESSOR_LIST = SpringUtil.getBeansOfType(LoginPostProcessor.class).values();
    }

    @Override
    public LoginTypeEnum getLoginTypeEnum() {
        return null;
    }

    @Override
    public AbstractAuthenticationToken wrapperRequest(JSONObject request,
            LoginTypeEnum loginTypeEnum) {
        for (LoginPostProcessor loginPostProcessor : LOGIN_POST_PROCESSOR_LIST) {
            if (ObjectUtil.equals(loginTypeEnum, loginPostProcessor.getLoginTypeEnum())) {
                return loginPostProcessor.wrapperRequest(request, loginTypeEnum);
            }
        }
        return null;
    }
}
