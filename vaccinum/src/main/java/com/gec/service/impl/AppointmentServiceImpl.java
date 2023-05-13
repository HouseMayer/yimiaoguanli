package com.gec.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.entity.Appointment;
import com.gec.mapper.AppointmentMapper;
import com.gec.service.IAppointmentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 疫苗预约 服务实现类
 * </p>
 *
 * @author jerry
 * @since 2023-04-17
 */
@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements IAppointmentService {

}
