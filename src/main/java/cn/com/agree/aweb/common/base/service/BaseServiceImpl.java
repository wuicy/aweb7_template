package cn.com.agree.aweb.common.base.service;

import cn.com.agree.aweb.common.base.dao.ExampleWithRangeSpecification;
import cn.com.agree.aweb.common.base.dao.Range;
import cn.com.agree.aweb.common.base.mapper.BaseMapper;
import cn.com.agree.aweb.entity.query.QueryEntity;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public abstract class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {

  public abstract JpaRepositoryImplementation<T, ID> getDao();

  @Override
  public T add(T t) {
    return getDao().save(t);
  }

  @Override
  public List<T> addAll(List<T> list) {
    return getDao().saveAll(list);
  }

  @Override
  public void delete(T t) {
    getDao().delete(t);
  }

  @Override
  public void delete(Iterable<ID> ids) {
    ids.forEach(this::delete);
  }

  @Override
  public void delete(ID id) {
    this.findById(id).ifPresent(this::delete);
//    getDao().deleteById(id);
  }

  @Override
  public List<T> findAll() {
    return getDao().findAll();
  }

  @Override
  public Optional<T> findById(ID id) {
    return getDao().findById(id);
  }

  @Override
  public Page<T> findAll(Example<T> example, Pageable page) {
    return getDao().findAll(example, page);
  }

  @Override
  public Page findAll(Example<T> example, List<Range> ranges, Pageable page) {
    return findAll(example, ranges, page, null);
  }

  //可自定义Matcher，结果可以根据mapper转换，mapper为null时不转换
  @Override
  public Page findAll(Example<T> example, List<Range> ranges, Pageable page, BaseMapper mapper) {
    Specification<T> specification = new ExampleWithRangeSpecification<>(example, ranges);
    Page<T> poPage = getDao().findAll(specification, page);
    if (mapper == null) {
      return poPage;
    } else {
      return new PageImpl<>(mapper.poToVO(poPage.getContent()), page,
          poPage.getTotalElements());
    }
  }

  //默认为String类型参数模糊查询的Mathcer，结果不转换
  @Override
  public Page findByQuery(QueryEntity query) {
    return findByQuery(query, null);
  }

  //默认为String类型参数模糊查询的Mathcer，结果根据mapper转换，为null时不转换
  @Override
  public Page findByQuery(QueryEntity query, BaseMapper mapper) {
    ExampleMatcher matcher = ExampleMatcher.matching()
        .withStringMatcher(StringMatcher.CONTAINING)
        .withIgnoreCase();
    Example<T> example;
    if (mapper != null) {
      example = Example.of((T) mapper.voToPO(query.getQuery()), matcher);
    } else {
      example = Example.of((T) query.getQuery(), matcher);
    }
    List<Range> ranges = query.getRanges();
    Pageable page = query.getPage();
    return findAll(example, ranges, page, mapper);
  }

  //默认为其中一个参数模糊查询符合即可的Mathcer，结果不转换
  @Override
  public Page findByString(Class<T> clazz, QueryEntity<String> query)
      throws InstantiationException, IllegalAccessException {
    return findByString(clazz, query, null);
  }

  //默认为其中一个参数模糊查询符合即可的Mathcer，结果根据mapper转换，为null时不转换
  @Override
  public Page findByString(Class<T> clazz, QueryEntity<String> query, BaseMapper mapper)
      throws InstantiationException, IllegalAccessException {
    ExampleMatcher matcher = ExampleMatcher.matchingAny()
        .withStringMatcher(StringMatcher.CONTAINING)
        .withIgnoreCase().withIgnorePaths("id", "createTime", "updateTime");
    T po = getSameValueObject(clazz, query.getQuery());
    Example<T> example = Example.of(po, matcher);
    List<Range> ranges = query.getRanges();
    Pageable page = query.getPage();
    return findAll(example, ranges, page, mapper);
  }

  @Override
  public T update(T t) {
    return getDao().save(t);
  }

  //动态更新（传入属性值为null的属性不更新 ）
  @Override
  public T update(T t, ID id) {
    T source = getDao().findById(id).orElse(null);
    if (source != null) {
      BeanUtils.copyProperties(t, source, getNullPropertyNames(t));
      source = getDao().save(source);
    }
    return source;
  }

  private String[] getNullPropertyNames(Object source) {
    final BeanWrapper wrapper = new BeanWrapperImpl(source);
    List<String> list = new ArrayList<>();
    for (PropertyDescriptor descriptor : wrapper.getPropertyDescriptors()) {
      if (wrapper.getPropertyValue(descriptor.getName()) == null) {
        list.add(descriptor.getName());
      }
    }
    String[] result = new String[list.size()];
    return list.toArray(result);
  }

  private T getSameValueObject(Class<T> clazz, String query)
      throws IllegalAccessException, InstantiationException {
    T po = clazz.newInstance();
    final BeanWrapper wrapper = new BeanWrapperImpl(po);
    for (PropertyDescriptor descriptor : wrapper.getPropertyDescriptors()) {
      if (descriptor.getPropertyType().equals(String.class)) {
        wrapper.setPropertyValue(descriptor.getName(), query);
      }
    }
    return po;
  }

}
