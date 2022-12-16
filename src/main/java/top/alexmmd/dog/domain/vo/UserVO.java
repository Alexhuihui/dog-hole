package top.alexmmd.dog.domain.vo;

import java.util.List;
import lombok.Data;

/**
 * @author wangyonghui
 * @date 2022年12月16日 10:42:00
 */
@Data
public class UserVO {

    /**
     * 自增主键
     */
    private Integer id;
    /**
     * 用户id
     */
    private Long uid;
    /**
     * 微信开放平台中的用户统一id
     */
    private String unionId;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 区县
     */
    private String country;
    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 账户
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 账户类型
     */
    private Integer accountType;
    /**
     * 所属应用
     */
    private String appCode;

    /**
     * 角色列表
     */
    private List<String> roleList;

}
