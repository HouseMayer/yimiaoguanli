package com.gec.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
     * 预约人
     */
    private Long userId;

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
    private LocalDate appDate;

    /**
     * 创建时间
     */
    private LocalDateTime postTime;

    /**
     * 接种状态
     */
    private Integer status;

    /**
     * 医护人员id
     */
    private Long docId;


}
