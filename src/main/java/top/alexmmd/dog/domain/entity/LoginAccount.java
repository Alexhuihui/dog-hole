package top.alexmmd.dog.domain.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;

/**
 * (LoginAccount)实体类
 *
 * @author makejava
 * @since 2022-10-11 08:48:44
 */
@Data
public class LoginAccount implements Serializable {
    private static final long serialVersionUID = 592421031463279303L;
    /**
     * 自增主键
     */
    private Integer id;
    /**
     * 用户id
     */
    private Long uid;
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
     * 状态，1有效0无效
     */
    private Integer status;
    
    private Date createTime;
    
    private String createUser;
    
    private Date updateTime;
    
    private String updateUser;

}

