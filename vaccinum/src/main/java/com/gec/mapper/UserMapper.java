package com.gec.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gec.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author jerry
 * @since 2023-04-17
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
