package com.gec.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
     * 疫苗类型
     */
    private String name;

    /**
     * 描述
     */
    private String remark;


}
