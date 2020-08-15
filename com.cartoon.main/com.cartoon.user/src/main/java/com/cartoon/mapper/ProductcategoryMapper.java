package com.cartoon.mapper;
import com.cartoon.entity.Productcategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductcategoryMapper {

	public Productcategory getProductcategoryById(@Param(value = "id") Long id)throws Exception;

	public List<Productcategory>	getProductcategoryListByMap(Map<String,Object> param)throws Exception;

	public Integer getProductcategoryCountByMap(Map<String,Object> param)throws Exception;

	public Integer insertProductcategory(Productcategory productcategory)throws Exception;

	public Integer updateProductcategory(Productcategory productcategory)throws Exception;


}
