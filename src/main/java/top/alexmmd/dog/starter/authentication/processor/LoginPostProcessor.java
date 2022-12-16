package top.alexmmd.dog.starter.authentication.processor;

import cn.hutool.json.JSONObject;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import top.alexmmd.dog.enums.LoginTypeEnum;

/**
 * @author wangyonghui
 * @date 2022年12月12日 16:25:00
 */
public interface LoginPostProcessor {

    /**
     * 获取登录类型
     *
     * @return 登录类型枚举
     */
    LoginTypeEnum getLoginTypeEnum();

    /**
     * 包装不同类型的请求
     *
     * @param request       登录请求参数
     * @param loginTypeEnum 认证类型
     * @return 待认证对象
     */
    AbstractAuthenticationToken wrapperRequest(JSONObject request, LoginTypeEnum loginTypeEnum);
}
