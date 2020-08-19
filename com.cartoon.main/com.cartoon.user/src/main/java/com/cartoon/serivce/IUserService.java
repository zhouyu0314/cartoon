package com.cartoon.serivce;

import com.cartoon.entity.User;
import com.cartoon.exceptions.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IUserService {
    /**
     * 使用手机号注册
     */
    Integer userReg(String phone, String password, String recommender) throws
            UsernameConflictExcaption, InsertDataException, IllegalParamsException;

    /**
     * 使用手机号查询用户是否存在
     *
     * @param phone
     * @return
     */
    User serchUserByPhone(String phone) throws DataNotFoundException, IllegalParamsException;


    /**
     * 修改用户资料根据id
     *
     * @param user
     * @return
     */
    Integer changeUserInfo(User user) throws DataNotFoundException, InsertDataException, IllegalParamsException;

    /**
     * 修改密码
     */
    Integer changeUserPwd(String newPwd, String oldPwd, String phone) throws DataNotFoundException,
            IllegalParamsException, InsertDataException;

    /**
     * 上传头像
     *
     * @param file
     * @param tempPath
     * @return
     * @throws UploadFileException
     * @see com.cartoon.util.ToolUpLoad
     */
    Integer changeUserHeaderImg(MultipartFile file, String tempPath, String phone) throws UploadFileException, InsertDataException;


    String getToken(String phone) throws LoginTimeOutException;

    /**
     * 查询用户信息（评论： 头像、昵称、vip）
     *
     * @param phone
     * @return
     */
    User getUserInfo(String phone);


    /**
     * 验证用户vip状态
     *
     * @param user
     * @return
     */
    void checkVip(User user);


    /**
     * 修改用户的元宝、积分、月票。。。。
     */
    Integer updateUsergoldOrTicketOrScoreOrCoupon(Map<String, Object> params) throws InsertDataException;




}
