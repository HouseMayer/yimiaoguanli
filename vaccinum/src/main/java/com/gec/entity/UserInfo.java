package com.gec.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author jerry
 * @since 2023-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id INPUT设置为手动输入id
     */
    @TableId(value = "user_id", type = IdType.INPUT)
    private Long userId;

    /**
     * username
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 身份证
     */
    private String code;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 职位
     */
    private String job;

    /**
     * 用户通行码-0绿码-1黄码-2红码
     */
    private Integer status;


    @TableField(exist = false)
    private User user;


}
