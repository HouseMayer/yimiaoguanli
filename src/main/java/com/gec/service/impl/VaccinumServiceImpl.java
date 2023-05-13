package com.gec.service.impl;

import com.gec.entity.Vaccinum;
import com.gec.mapper.VaccinumMapper;
import com.gec.service.IVaccinumService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 疫苗 服务实现类
 * </p>
 *
 * @author jerry
 * @since 2023-04-17
 */
@Service
public class VaccinumServiceImpl extends ServiceImpl<VaccinumMapper, Vaccinum> implements IVaccinumService {

}
