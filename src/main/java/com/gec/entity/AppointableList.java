package com.gec.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
     * 接种疫苗
     */
    private Long vaccinumId;

    /**
     * 可接种数量（剩余）
     */
    private Integer nums;

    /**
     * 已经预约数量
     */
    private String appNum;

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


}
