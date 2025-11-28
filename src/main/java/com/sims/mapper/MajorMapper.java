package com.sims.mapper;

import com.github.pagehelper.Page;
import com.sims.model.dto.major.MajorDTO;
import com.sims.model.dto.major.PageQueryMajorDTO;
import com.sims.model.entity.Major;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    /**
     * 根据ID查询专业
     */
    @Select("select * from major where id = #{id}")
    Major findById(Long id);

    /**
     * 根据名称查询专业
     */
    @Select("select * from major where name = #{name}")
    Major findByName(String name);

    /**
     * 根据代码查询专业
     */
    @Select("select * from major where code = #{code}")
    Major findByCode(String code);

    /**
     * 批量删除专业
     */
    void deleteByIds(@Param("ids") List<Long> ids);

    /**
     * 更新专业信息
     */
    void update(MajorDTO majorDTO);

    /**
     * 分页查询专业
     */
    Page<Major> page(PageQueryMajorDTO pageQueryMajorDTO);
}
