package com.tools.mybatis.mapping.mapper;


import java.util.List;
import java.util.Map;

/**
 * @author Cuidy
 * @date 2017-02-09
 */
public interface CFansMapper {

    List<CFans> getListByCondition(PageOptions options);

    List<CFans> search(PageOptions options);

    Integer getCount(PageOptions options);

    List<CFans> getTodayData();

    int updateBatch(Map<String, Object> map);
}
