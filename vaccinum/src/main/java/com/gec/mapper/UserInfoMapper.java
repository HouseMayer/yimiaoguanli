package com.gec.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gec.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户信息 Mapper 接口
 * </p>
 *
 * @author jerry
 * @since 2023-04-17
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}
