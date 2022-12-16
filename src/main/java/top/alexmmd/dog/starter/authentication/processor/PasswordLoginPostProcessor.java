package top.alexmmd.dog.starter.authentication.processor;

import cn.hutool.json.JSONObject;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import top.alexmmd.dog.enums.LoginTypeEnum;

/**
 * @author wangyonghui
 * @date 2022年12月15日 16:41:00
 */
@Component
public class PasswordLoginPostProcessor implements LoginPostProcessor{

    @Override
    public LoginTypeEnum getLoginTypeEnum() {
        return LoginTypeEnum.PASSWORD;
    }

    @Override
    public AbstractAuthenticationToken wrapperRequest(JSONObject request, LoginTypeEnum loginTypeEnum) {
        String username = obtainUsername(request);
        username = (username != null) ? username.trim() : "";
        String password = obtainPassword(request);
        password = (password != null) ? password : "";
        return UsernamePasswordAuthenticationToken.unauthenticated(username, password);
    }

    private String obtainPassword(JSONObject request) {
        return request.getStr("password");
    }

    private String obtainUsername(JSONObject request) {
        return request.getStr("username");
    }
}
