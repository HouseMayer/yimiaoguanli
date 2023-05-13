package com.gec.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 疫苗
 * </p>
 *
 * @author jerry
 * @since 2023-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Vaccinum implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */

    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 疫苗名称
     */
    private String name;

    /**
     * 疫苗类型
     */
    private Integer type;

    /**
     * 目标人群
     */
    private String target;

    /**
     * 作用
     */
    private String effect;

    /**
     * 不良反应
     */
    private String advReactions;

    /**
     * 生产厂家
     */
    private String producer;

    /**
     * 生产日期
     */
    private String createTime;

    /**
     * 分类名称
     */
    @TableField(exist = false)
    private String typeName;


}
