package com.gec.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.entity.VaccinumType;
import com.gec.mapper.VaccinumTypeMapper;
import com.gec.service.IVaccinumTypeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 疫苗类型 服务实现类
 * </p>
 *
 * @author jerry
 * @since 2023-04-17
 */
@Service
public class VaccinumTypeServiceImpl extends ServiceImpl<VaccinumTypeMapper, VaccinumType> implements IVaccinumTypeService {

}
