localhost:8001/api/person/getUserById
localhost:8001/api/person/getOrderByUserID


localhost:8002/api/order/getOrderByUserID

localhost:8003/api/gift/buySomeGift  买
{"ids":"830514884901867520,830306239173890048","counts":"3,5","area": "beijing"}
localhost:8003/api/gift/getGiftById



localhost:8003/api/gift/insertPerson

{"giftname":"华为xz","price":5999.00,"area": "xuzhou"}


用户购买商品
1.先验证用户是否存在（查出此用户）
2.查找商品是否存在（查询商品信息）
3.商品总价对比用户余额
4.减商品数量，减用户余额，创建订单

用户
id,name,money
商品
id,name,price
订单
id,user_id,order_item_id,total_price,total_count
订单详情
id,gift_id,count,total_price