package cn.com.agree.aweb.common.base.mapper;

import java.util.List;

public interface BaseMapper<P, V> {

  P voToPO(V vo);

  List<P> voToPO(List<V> voList);

  V poToVO(P po);

  List<V> poToVO(List<P> poList);
}
