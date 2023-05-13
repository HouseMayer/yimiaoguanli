package com.gec.service.impl;

import com.gec.entity.AppointableList;
import com.gec.mapper.AppointableListMapper;
import com.gec.service.IAppointableListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 可预约疫苗列表 服务实现类
 * </p>
 *
 * @author jerry
 * @since 2023-04-17
 */
@Service
public class AppointableListServiceImpl extends ServiceImpl<AppointableListMapper, AppointableList> implements IAppointableListService {

}
