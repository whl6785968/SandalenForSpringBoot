package com.sandalen.sandalen.ConfigMapper;

import com.sandalen.sandalen.entities.RptCount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RptMapper {
    List<RptCount> getRptCoutList();
}
