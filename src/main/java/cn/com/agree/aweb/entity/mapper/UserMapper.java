package cn.com.agree.aweb.entity.mapper;

import cn.com.agree.aweb.common.base.mapper.BaseMapper;
import cn.com.agree.aweb.entity.po.UserPO;
import cn.com.agree.aweb.entity.vo.UserVO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageImpl;

@Mapper
public interface UserMapper extends BaseMapper<UserPO, UserVO> {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @Override
  @Mappings({
      @Mapping(target = "password", ignore = true)
  })
  UserVO poToVO(UserPO po);

//  @Override
//  @Mappings({
//      @Mapping(target = "password", ignore = true)
//  })
//  List<UserVO> poToVO(List<UserPO> po);

}
