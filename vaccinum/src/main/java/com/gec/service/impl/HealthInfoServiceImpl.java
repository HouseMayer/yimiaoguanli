package com.gec.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.entity.HealthInfo;
import com.gec.mapper.HealthInfoMapper;
import com.gec.service.IHealthInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 健康情况 服务实现类
 * </p>
 *
 * @author jerry
 * @since 2023-04-17
 */
@Service
public class HealthInfoServiceImpl extends ServiceImpl<HealthInfoMapper, HealthInfo> implements IHealthInfoService {

}
