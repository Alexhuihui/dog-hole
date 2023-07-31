package top.alexmmd.dog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wangyonghui
 * @date 2022年12月28日 09:52:00
 */
@AllArgsConstructor
@Getter
public enum AccountTypeEnum {

    /**
     * 微信
     */
    WECHAT(10, "微信"),

    EMAIL(20, "微信"),
    ;

    private final Integer value;

    private final String desc;
}
