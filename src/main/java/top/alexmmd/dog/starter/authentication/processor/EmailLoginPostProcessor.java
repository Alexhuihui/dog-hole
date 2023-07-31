package top.alexmmd.dog.starter.authentication.processor;

import cn.hutool.json.JSONObject;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import top.alexmmd.dog.enums.LoginTypeEnum;
import top.alexmmd.dog.starter.authentication.token.EmailAuthenticationToken;

/**
 * @author wangyonghui
 * @date 2022年12月15日 16:41:00
 */
@Component
public class EmailLoginPostProcessor implements LoginPostProcessor{

    @Override
    public LoginTypeEnum getLoginTypeEnum() {
        return LoginTypeEnum.EMAIL;
    }

    @Override
    public AbstractAuthenticationToken wrapperRequest(JSONObject request, LoginTypeEnum loginTypeEnum) {
        String email = obtainEmail(request);
        email = (email != null) ? email.trim() : "";
        String code = obtainCode(request);
        code = (code != null) ? code : "";
        return EmailAuthenticationToken.unauthenticated(email, code);
    }

    private String obtainCode(JSONObject request) {
        return request.getStr("code");
    }

    private String obtainEmail(JSONObject request) {
        return request.getStr("email");
    }
}
