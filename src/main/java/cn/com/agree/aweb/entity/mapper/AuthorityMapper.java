package cn.com.agree.aweb.entity.mapper;

import cn.com.agree.aweb.common.base.mapper.BaseMapper;
import cn.com.agree.aweb.entity.po.AuthorityPO;
import cn.com.agree.aweb.entity.vo.AuthorityVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorityMapper extends BaseMapper<AuthorityPO,AuthorityVO> {

  AuthorityMapper INSTANCE = Mappers.getMapper(AuthorityMapper.class);

}
