package com.github.trang.dynamic.base.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.trang.dynamic.base.mapper.BaseMapper;
import com.github.trang.dynamic.base.model.BaseModel;
import com.github.trang.dynamic.base.service.BaseService;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * BaseService 实现类
 *
 * @author trang
 */
public abstract class BaseServiceImpl<T extends BaseModel<PK>, PK extends Serializable> implements BaseService<T, PK> {

    @Autowired
    private BaseMapper<T> mapper;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int insert(T record) {
        Preconditions.checkNotNull(record);
        return mapper.insert(record);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int insertSelective(T record) {
        Preconditions.checkNotNull(record);
        return mapper.insertSelective(record);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int insertList(Iterable<T> records) {
        Preconditions.checkArgument(records != null && !Iterables.isEmpty(records));
        return mapper.insertList(newArrayList(records));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int updateSelectiveByPk(T record) {
        Preconditions.checkArgument(record != null && record.getPk() != null);
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int updateByPk(T record) {
        Preconditions.checkArgument(record != null && record.getPk() != null);
        return mapper.updateByPrimaryKey(record);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int updateSelectiveByExample(T record, Example example) {
        Preconditions.checkArgument(record != null && example != null);
        return mapper.updateByExampleSelective(record, example);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int updateByExample(T record, Example example) {
        Preconditions.checkArgument(record != null && example != null);
        return mapper.updateByExample(record, example);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int deleteByPk(PK pk) {
        Preconditions.checkNotNull(pk);
        return mapper.deleteByPrimaryKey(pk);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int deleteByPks(Iterable<? extends PK> pks) {
        Preconditions.checkArgument(pks != null && !Iterables.isEmpty(pks));
        String pksStr = Joiner.on(',').skipNulls().join(pks);
        return mapper.deleteByIds(pksStr);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int delete(T param) {
        Preconditions.checkNotNull(param);
        return mapper.delete(param);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int deleteByExample(Example example) {
        Preconditions.checkNotNull(example);
        return mapper.deleteByExample(example);
    }

    @Override
    public T selectByPk(PK pk) {
        Preconditions.checkNotNull(pk);
        return mapper.selectByPrimaryKey(pk);
    }

    @Override
    public List<T> selectByPks(Iterable<? extends PK> pks) {
        Preconditions.checkArgument(pks != null && !Iterables.isEmpty(pks));
        String pksStr = Joiner.on(',').skipNulls().join(pks);
        return mapper.selectByIds(pksStr);
    }

    @Override
    public List<T> select(T param) {
        Preconditions.checkNotNull(param);
        return mapper.select(param);
    }

    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public List<T> selectByExample(Example example) {
        Preconditions.checkNotNull(example);
        return mapper.selectByExample(example);
    }

    @Override
    public T selectOne(T param) {
        Preconditions.checkNotNull(param);
        return mapper.selectOne(param);
    }

    @Override
    public T selectOneByExample(Example example) {
        Preconditions.checkNotNull(example);
        return mapper.selectOneByExample(example);
    }

    @Override
    public T selectLimitOne(T param) {
        Preconditions.checkNotNull(param);
        Page<T> page = PageHelper.offsetPage(0, 1, false).doSelectPage(
                () -> mapper.select(param)
        );
        return CollectionUtils.isNotEmpty(page) ? page.get(0) : null;
    }

    @Override
    public T selectLimitOneByExample(Example example) {
        Preconditions.checkNotNull(example);
        Page<T> page = PageHelper.offsetPage(0, 1, false).doSelectPage(
                () -> mapper.selectByExample(example)
        );
        return CollectionUtils.isNotEmpty(page) ? page.get(0) : null;
    }

    @Override
    public long selectCount(T param) {
        Preconditions.checkNotNull(param);
        return mapper.selectCount(param);
    }

    @Override
    public long selectCountByExample(Example example) {
        Preconditions.checkNotNull(example);
        return mapper.selectCountByExample(example);
    }

    @Override
    public List<T> selectPage(T param) {
        Preconditions.checkArgument(param != null && param.getPage() != null && param.getPageSize() != null);
        return PageHelper.startPage(param.getPage(), param.getPageSize(), false).doSelectPage(
                () -> mapper.select(param)
        );
    }

    @Override
    public List<T> selectPageByExample(Example example, int pageNum, int pageSize) {
        Preconditions.checkNotNull(example);
        return PageHelper.startPage(pageNum, pageSize, false).doSelectPage(
                () -> mapper.selectByExample(example)
        );
    }

    @Override
    public PageInfo<T> selectPageAndCount(T param) {
        Preconditions.checkArgument(param != null && param.getPage() != null && param.getPageSize() != null);
        return PageHelper.startPage(param.getPage(), param.getPageSize()).doSelectPageInfo(
                () -> mapper.select(param)
        );
    }

    @Override
    public PageInfo<T> selectPageAndCountByExample(Example example, int pageNum, int pageSize) {
        Preconditions.checkNotNull(example);
        return PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(
                () -> mapper.selectByExample(example)
        );
    }

}