package cn.com.agree.aweb.entity.mapper;

import cn.com.agree.aweb.common.base.mapper.BaseMapper;
import cn.com.agree.aweb.entity.po.RolePO;
import cn.com.agree.aweb.entity.vo.RoleVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper extends BaseMapper<RolePO,RoleVO> {

  RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

}
