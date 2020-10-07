package com.gy.mapper;

import com.gy.annotation.DataSource;
import com.gy.entity.Gift;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface GiftMapper {

	@DataSource
	public Gift getGiftById(@Param(value = "id") Long id);
	@DataSource
	public List<Gift>	getGiftListByMap(Map<String, Object> param);
	@DataSource
	public Integer getGiftCountByMap(Map<String, Object> param);
	@DataSource
	public Integer insertGift(Gift gift);
	@DataSource
	public Integer updateGift(Gift gift);


}
