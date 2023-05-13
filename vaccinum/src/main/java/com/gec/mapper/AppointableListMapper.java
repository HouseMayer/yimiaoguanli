package com.gec.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gec.entity.AppointableList;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 可预约疫苗列表 Mapper 接口
 * </p>
 *
 * @author jerry
 * @since 2023-04-17
 */
@Mapper
public interface AppointableListMapper extends BaseMapper<AppointableList> {

}
