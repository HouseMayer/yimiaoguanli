package com.gec.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 健康情况
 * </p>
 *
 * @author jerry
 * @since 2023-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HealthInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 症状
     */
    private String symptom;

    /**
     * 体温
     */
    private Float temperature;

    /**
     * 备注
     */
    private String remark;


}
