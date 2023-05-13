package com.gec.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 核酸
 * </p>
 *
 * @author jerry
 * @since 2023-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Nuclein implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 类型-0大规模-常规
     */
    private Integer type;

    /**
     * 测试地点
     */
    private String address;

    /**
     * 测试机构
     */
    private String mechanism;

    /**
     * 测试时间
     */
    private LocalDateTime createTime;

    /**
     * 测试结果-0阴性-1阳性
     */
    private Integer result;

    /**
     * 结果时间
     */
    private LocalDateTime resultTime;


}
