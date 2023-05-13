package com.gec.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gec.entity.Appointment;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 疫苗预约 Mapper 接口
 * </p>
 *
 * @author jerry
 * @since 2023-04-17
 */
@Mapper
public interface AppointmentMapper extends BaseMapper<Appointment> {

}
