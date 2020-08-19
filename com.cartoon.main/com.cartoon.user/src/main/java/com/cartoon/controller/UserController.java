package com.cartoon.controller;

import com.cartoon.dto.Dto;
import com.cartoon.dto.DtoUtil;
import com.cartoon.entity.User;
import com.cartoon.serivce.IUserService;
import com.cartoon.util.TokenDecode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
@Api(value = "用户接口", description = "用户接口")
public class UserController extends BaseController {
    @Autowired
    private IUserService iUserService;

    @Value("${headImgTempPath}")
    private String tempPath;


    /**
     * 使用手机号注册
     */
    @ApiOperation(value = "用户注册", notes = "返回用户是否注册成功，" +
            "可能会抛出的异常有：IllegalParamsException(格式错误)，UsernameConflictExcaption(用户名冲突), " +
            "InsertDataException(数据插入失败)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "用户手机号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "recommender", value = "推荐人", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping("/userReg")
    public Dto UserReg(String phone, String password, String recommender) {
        iUserService.userReg(phone, password, recommender);
        return DtoUtil.returnSuccess("success");

    }


    /**
     * 根据用户携带的token查询用户的信息，前端显示用户信息用此方法
     *
     * @return DataNotFoundException, TransmissionException
     */
    @ApiOperation(value = "根据用户携带的token查询用户的信息", notes = "返回用户信息，" +
            "可能会抛出的异常有：IllegalParamsException(格式错误)，" +
            "DataNotFoundException(未找到数据)")
    @GetMapping("/serchUserByPhone")
    public Dto serchUserByPhone() {
        //解析token
        Map<String, String> userInfo = TokenDecode.getUserInfo();
        User user = iUserService.serchUserByPhone(userInfo.get("username"));
        user.setPassword("");
        return DtoUtil.returnSuccess("success", user);
    }

    /**
     * 修改用户资料
     *
     * @param user
     * @return
     */

    @ApiOperation(value = "修改用户资料", notes = "返回是否修改成功，" +
            "可能会抛出的异常有：InsertDataException(修改用户资料错误)，" +
            "DataNotFoundException(未查询到用户)，" +
            "IllegalParamsException(参数格式违法)")
    @ApiImplicitParam(name = "user", value = "用户对象", required = true, dataType = "User", paramType = "query")
    @PostMapping("/changeUserInfo")
    public Dto changeUserInfo(User user) {
        //解析token
        Map<String, String> userInfo = TokenDecode.getUserInfo();
        user.setPhone(userInfo.get("username"));
        iUserService.changeUserInfo(user);
        return DtoUtil.returnSuccess("success");
    }


    /**
     * 修改密码
     *
     * @param newPwd
     * @param oldPwd
     * @return
     */
    @ApiOperation(value = "修改用户密码", notes = "返回是否修改成功，" +
            "可能会抛出的异常有：InsertDataException(修改用户资料错误)，" +
            "DataNotFoundException(未查询到用户)，" +
            "IllegalParamsException(参数格式违法)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "用户手机号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping("/changeUserPwd")
    public Dto changeUserPwd(String newPwd, String oldPwd) {
        //解析token
        Map<String, String> userInfo = TokenDecode.getUserInfo();
        iUserService.changeUserPwd(newPwd, oldPwd, userInfo.get("username"));
        return DtoUtil.returnSuccess("success");
    }


    //修改头像
    @ApiOperation(value = "修改用户头像", notes = "返回是否修改成功，" +
            "可能会抛出的异常有：UploadFileException(上传头像失败),InsertDataException(修改用户资料错误)")
    @ApiImplicitParam(name = "file", value = "用户文件", required = true, dataType = "User", paramType = "query")
    @PostMapping("/changeUserHeaderImg")
    public Dto changeUserHeaderImg(MultipartFile file) {
        //解析token
        Map<String, String> userInfo = TokenDecode.getUserInfo();
        iUserService.changeUserHeaderImg(file, tempPath, userInfo.get("username"));
        return DtoUtil.returnSuccess("success");
    }


    @GetMapping("/share")
    public Dto share() {
        //解析token
        Map<String, String> userInfo = TokenDecode.getUserInfo();
        //TODO
        return null;
    }

    /**
     * 此为redis里最新的token
     *
     * @return
     */
    @GetMapping("/getToken")
    public Dto getToken() {
        String phone = TokenDecode.getUserInfo().get("username");

        return DtoUtil.returnSuccess("新的token！", iUserService.getToken(phone));

    }



    //-----------------feign----------------------

    /**
     * 根据手机查询用户信息，oauth调用
     *
     * @param phone
     * @return DataNotFoundException, TransmissionException, IllegalParamsException
     */
    @PostMapping("/serchUserByPhoneFeign")
    public User serchUserByPhoneFeign(@RequestBody String phone) {
        User user = iUserService.serchUserByPhone(phone);
        return user;
    }

    /**
     * oauth调用，验证用户vip合法性
     *
     * @param user
     */
    @PostMapping("/checkVip")
    public void checkVip(@RequestBody User user) {
        iUserService.checkVip(user);
    }

    /**
     * 查询用户的头像，昵称，vip,元宝,月票,积分 feign调用
     *
     * @param phone
     * @return
     */
    @GetMapping("/getUserInfo/{phone}")
    public User getUserInfo(@PathVariable("phone") String phone) {
        return iUserService.getUserInfo(phone);
    }


    /**
     * 前端需提供
     * 1.用户phone
     * 2.记录分类type（元宝（gold）1、阅读券(coupon)2、积分(score)3、月票(ticket)4、vip5）
     * 3.记录描述recordReason
     * 4.数量count
     * 5.消费目标/获得来源target
     * @param params
     * @return
     */
    @PostMapping("/updateUsergoldOrTicketOrScoreOrCoupon")
    public Dto updateUsergoldOrTicketOrScoreOrCoupon(@RequestBody Map<String, Object> params) {
        return DtoUtil.returnSuccess("如果返回的1就是修改成功",
                iUserService.updateUsergoldOrTicketOrScoreOrCoupon(params));
    }

//---------------------------测试代码-----------------------------------

    @GetMapping("/show")
    public String show() {
        String authorities = TokenDecode.getUserInfo().get("username");

        return "测试";
    }

    @GetMapping("/show2")
    public String show2() {

        return "测试2";
    }


}
