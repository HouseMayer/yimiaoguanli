package com.gec.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 疫苗类型
 * </p>
 *
 * @author jerry
 * @since 2023-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class VaccinumType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 疫苗类型
     */
    private String name;

    /**
     * 描述
     */
    private String remark;


}
