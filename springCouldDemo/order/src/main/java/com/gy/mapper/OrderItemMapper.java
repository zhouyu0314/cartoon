package com.gy.mapper;

import com.gy.annotation.DataSource;
import com.gy.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderItemMapper {

	@DataSource
	OrderItem getOrderItemById(@Param(value = "id") Long id);
	@DataSource
	List<OrderItem>	getOrderItemListByMap(Map<String, Object> param);
	@DataSource
	Integer getOrderItemCountByMap(Map<String, Object> param);
	@DataSource
	Integer insertOrderItem(OrderItem orderItem);
	@DataSource
	Integer updateOrderItem(OrderItem orderItem);


}
