package com.gec.entity;

import java.time.LocalDate;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    private LocalDate createTime;


}
