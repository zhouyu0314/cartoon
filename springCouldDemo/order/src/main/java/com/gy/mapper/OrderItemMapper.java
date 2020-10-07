package com.gy.mapper;

import com.gy.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderItemMapper {

	OrderItem getOrderItemById(@Param(value = "id") Long id);
	List<OrderItem>	getOrderItemListByMap(Map<String, Object> param);
	Integer getOrderItemCountByMap(Map<String, Object> param);
	Integer insertOrderItem(OrderItem orderItem);
	Integer updateOrderItem(OrderItem orderItem);


}
