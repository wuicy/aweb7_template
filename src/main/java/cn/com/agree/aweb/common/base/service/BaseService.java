package cn.com.agree.aweb.common.base.service;

import cn.com.agree.aweb.common.base.dao.Range;
import cn.com.agree.aweb.common.base.mapper.BaseMapper;
import cn.com.agree.aweb.entity.query.QueryEntity;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BaseService<T, ID extends Serializable> {

  T add(T t);

  List<T> addAll(List<T> list);

  void delete(T t);

  void delete(Iterable<ID> ids);

  void delete(ID id);

  /**
   * 根据实体PO更新，非动态更新，即为null的属性也会更新，不建议使用。
   *
   * @param t 实体PO
   * @return 更新后的实体PO
   */
  T update(T t);

  List<T> findAll();

  Optional<T> findById(ID id);

  Page<T> findAll(Example<T> example, Pageable page);

  Page findAll(Example<T> example, List<Range> ranges, Pageable page);

  /**
   * 自定义查询，可自定义Matcher，结果可以根据mapper转换，mapper为null时不转换
   */
  Page findAll(Example<T> example, List<Range> ranges, Pageable page, BaseMapper mapper);

  /**
   * 动态模糊查询
   *
   * @param query 查询对象，QueryEntity的query属性为对应的视图层对象，即VO
   * @return 持久层对象List，即PO List
   */
  Page findByQuery(QueryEntity query);

  /**
   * 动态模糊查询
   *
   * @param query 查询对象，QueryEntity的query属性为对应的视图层对象，即VO
   * @param mapper 对应的mapper
   * @return 根据mapper转换后的List，一般为视图层对象List，即VO List
   */
  Page findByQuery(QueryEntity query, BaseMapper mapper);

  /**
   * 匹配查询（其中一个参数模糊查询符合即可）
   *
   * @param clazz 对应的持久层对象Class，即PO.class
   * @param query 查询对象，QueryEntity的query属性为查询的字符串
   * @return 持久层对象List，即PO List
   */
  Page findByString(Class<T> clazz, QueryEntity<String> query)
      throws InstantiationException, IllegalAccessException;

  /**
   * 匹配查询（其中一个参数模糊查询符合即可）
   *
   * @param clazz 对应的持久层对象Class，即PO.class
   * @param query 查询对象，QueryEntity的query属性为查询的字符串
   * @param mapper 对应的mapper
   * @return 根据mapper转换后的List，一般为对应的视图层对象List，即VO List
   */
  Page findByString(Class<T> clazz, QueryEntity<String> query, BaseMapper mapper)
      throws InstantiationException, IllegalAccessException;

  /**
   * 根据实体PO更新，动态更新。
   *
   * @param t 实体PO
   * @param id ID
   * @return 更新后的实体PO
   */
  T update(T t, ID id);

}
