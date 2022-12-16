package top.alexmmd.dog.enums;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wangyonghui
 * @date 2022年12月12日 16:12:00
 */
@AllArgsConstructor
@Getter
public enum LoginTypeEnum {

    /**
     * 账号密码登录
     */
    PASSWORD(10, "账号密码登录"),

    SMS(20, "手机号验证码登录"),

    WECHAT(30, "微信小程序登录"),
    ;

    private final Integer value;

    private final String desc;

    public static LoginTypeEnum getByValue(Integer value) {
        LoginTypeEnum[] values = LoginTypeEnum.values();
        for (LoginTypeEnum loginTypeEnum : values) {
            if (ObjectUtil.equals(value, loginTypeEnum.getValue())) {
                return loginTypeEnum;
            }
        }
        return null;
    }

}
