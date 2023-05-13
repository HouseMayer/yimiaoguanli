package com.gec.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 疫苗预约
 * </p>
 *
 * @author jerry
 * @since 2023-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */

    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 预约人
     */
    private Long userId;

    /**
     * 预约人
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 接种疫苗
     */
    private Long vaccinumId;

    /**
     * 接种地点
     */
    private String address;

    /**
     * 预约日期
     */
    private String appDate;

    /**
     * 创建时间
     */
    private String postTime;

    /**
     * 接种状态
     */
    private Integer status;

    /**
     * 医护人员id
     */
    private Long docId;


}
