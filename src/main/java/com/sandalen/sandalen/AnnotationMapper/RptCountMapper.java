package com.sandalen.sandalen.AnnotationMapper;

import com.sandalen.sandalen.entities.RptCount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RptCountMapper {
    @Select("select * from rpt_count limit 10,20")
    List<RptCount> getRptCount();

//    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into rpt_count(rpt_count) values(#{rpt_count})")
    int insert(RptCount rptCount);


}
