package com.gec.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gec.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jerry
 * @since 2023-04-16
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
