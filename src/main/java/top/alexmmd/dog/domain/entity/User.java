package top.alexmmd.dog.domain.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2022-10-11 08:48:44
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 283792280296544817L;
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
     * 状态，1有效0无效
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 更新人
     */
    private String updateUser;

}

