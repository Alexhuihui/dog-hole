package top.alexmmd.dog.domain.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * 微信应用信息管理(WechatAppInfo)实体类
 *
 * @author makejava
 * @since 2022-10-11 08:48:40
 */
@Data
public class WechatAppInfo implements Serializable {

    private static final long serialVersionUID = -25237632722496310L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 应用代码;系统生成的一个应用的编码
     */
    private String appCode;
    /**
     * 应用id;微信小程序或公众号的APPID
     */
    private String appId;
    /**
     * 应用密钥
     */
    private String appSecret;
    /**
     * 应用平台码;1=小程序；2=公众号；3=开放平台
     */
    private Integer appPlatformCode;
    /**
     * 备注
     */
    private String remarks;

}

