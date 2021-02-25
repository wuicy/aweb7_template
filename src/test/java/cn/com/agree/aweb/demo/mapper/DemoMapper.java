package cn.com.agree.aweb.demo.mapper;

import cn.com.agree.aweb.common.base.mapper.BaseMapper;
import cn.com.agree.aweb.demo.entity.po.DemoPO;
import cn.com.agree.aweb.demo.entity.vo.DemoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DemoMapper extends BaseMapper<DemoPO, DemoVO> {

  DemoMapper INSTANCE = Mappers.getMapper(DemoMapper.class);

}
