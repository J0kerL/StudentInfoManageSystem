package com.sims.mapper;

import com.sims.model.dto.major.MajorDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 * @author Diamond
 * @create 2025-11-26 11:43
 */
public interface MajorMapper {

    @Select("select count(*) from major where id = #{majorId}")
    int existsById(Long majorId);

    @Insert("insert into major(name, code,description,college ,create_time, update_time) values(#{name}, #{code}, #{description}, #{college},#{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(MajorDTO majorDTO);

    @Select("select count(*) from major where name = #{name}")
    int existsByName(String name);

    @Select("select count(*) from major where code = #{code}")
    int existsByCode(String code);
}
