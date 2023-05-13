package com.gec.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 可预约疫苗列表
 * </p>
 *
 * @author jerry
 * @since 2023-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AppointableList implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */

    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 接种疫苗
     */
    private Long vaccinumId;

    /**
     * 接种疫苗名称
     */
    @TableField(exist = false)
    private String vaccinumName;


    /**
     * 可接种数量（剩余）
     */
    private Integer nums;

    /**
     * 已经预约数量
     */
    private Integer appNum;

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




}
