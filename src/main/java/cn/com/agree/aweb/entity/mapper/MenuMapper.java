package cn.com.agree.aweb.entity.mapper;

import cn.com.agree.aweb.common.base.mapper.BaseMapper;
import cn.com.agree.aweb.entity.po.MenuPO;
import cn.com.agree.aweb.entity.vo.MenuVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MenuMapper extends BaseMapper<MenuPO, MenuVO> {

  MenuMapper INSTANCE = Mappers.getMapper(MenuMapper.class);

}
