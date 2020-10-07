package com.gy.mapper;

import com.gy.entity.Gift;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface GiftMapper {

	Gift getGiftById(@Param(value = "id") Long id);
	List<Gift>	getGiftListByMap(Map<String, Object> param);
	Integer getGiftCountByMap(Map<String, Object> param);
	Integer insertGift(Gift gift);
	Integer updateGift(Gift gift);


}
