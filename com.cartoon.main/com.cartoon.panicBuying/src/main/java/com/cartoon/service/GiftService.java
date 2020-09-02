package com.cartoon.service;

import com.cartoon.entity.Gift;
import com.cartoon.exceptions.DataNotFoundException;
import com.cartoon.exceptions.RecommitException;
import com.cartoon.exceptions.SellOutException;
import com.cartoon.exceptions.UpdateDataException;

import java.util.List;
import java.util.Map;

public interface GiftService {
    /**
     * 添加数据库
     * @param gift
     * @return
     */
    Integer addGift(Gift gift);

    /**
     * 自动生成Gift对象，
     * @return
     */
    Gift autoGenerate();

    /**
     * 取数据
     */
    List<Gift>	getGiftListByMap(Map<String,Object> param);

    /**
     * 此方法为测试放啊，最终需要放到定时任务执行
     */
    void addDB();

    /**
     * 抢红包
     */
    void RushRedPacket(String extime)throws SellOutException, RecommitException;

    /**
     * 抢成功的修改状态为1 ，过了场次之后修改没被抢的红包为2
     * @param gift
     * @return
     */
    Integer updateGift(Gift gift)throws UpdateDataException;

    /**
     * 查看redis中用户抢红包的状态
     */
    Gift showGiftStatus(String extime)throws DataNotFoundException;



}
