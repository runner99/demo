package com.runner99.sql.mapper;

import com.runner99.sql.pojo.RiskOut;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author wei
* @description 针对表【risk_out】的数据库操作Mapper
* @createDate 2024-02-18 11:16:26
* @Entity com.runner99.sql.pojo.RiskOut
*/
@Mapper
public interface RiskOutMapper {

    int deleteByPrimaryKey(Long id);

    int insert(RiskOut record);

    int insertSelective(RiskOut record);

    RiskOut selectByPrimaryKey(Long id);
    List<RiskOut> selectByName(@Param(value = "name") String name);

    int updateByPrimaryKeySelective(RiskOut record);

    int updateByPrimaryKey(RiskOut record);

}
