package cn.com.agree.aweb.excel.mapper;

import cn.com.agree.aweb.common.base.mapper.BaseMapper;
import cn.com.agree.aweb.excel.entity.po.ExcelPO;
import cn.com.agree.aweb.excel.entity.vo.ExcelVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Excel使用示例：Mapper 用于PO和VO的互相转换
 */
@Mapper
public interface ExcelMapper extends BaseMapper<ExcelPO, ExcelVO> {

  ExcelMapper INSTANCE = Mappers.getMapper(ExcelMapper.class);

}
