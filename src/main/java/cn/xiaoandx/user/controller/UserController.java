/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.xiaoandx.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.xiaoandx.user.entity.User;
import cn.xiaoandx.user.service.UserService;
import cn.xiaoandx.user.vo.OneValueVO;
import cn.xiaoandx.user.vo.UserImage;
import cn.xiaoandx.user.vo.WUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**  
 * <p>用户注册登录 </p> 
 * @ClassName:UserController   
 * @author: Fuqiang.Wu 
 * @since: V0.1 
 * @version V0.1
 */
@RequestMapping("/v2/open/user")
@RestController
@Api(tags = "微信用户相关操作API")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 用户登录注册
	 *@author: Fuqiang.Wu 
	 * @since: V0.1 
	 * @version V0.1
	 * @param wuser
	 * @return
	 */
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(httpMethod = "POST", value = "根据微信code，获取微信openid,向数据库插入该用户", notes = "根据微信code，获取微信openid<br><br><b>@author Fuqiang.Wu</b>")
    public OneValueVO get(@ApiParam(value = "用户信息*必填",required = true)@RequestBody WUser wuser) {
    	OneValueVO vo = null;
        String openid = userService.getWeixinOpenInfo(wuser.getCode());
        User user = userService.findUser(openid,wuser);
        vo = new OneValueVO(user.getID(),user.getToken());
        return vo;
    }
    
    /**
     *<p>人脸注册</p> 
     * @version:V0.1     
     * @param userimage		用户人脸注册的Base64人像数据
     * @throws Exception    
     * @return:String		返回注册结果(JSON对象)
     */
    @PostMapping(value = "/faceRe",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(httpMethod = "POST",value = "首次人脸注册，获取tock",notes = "拍照，获取本人的tock<br><br><b>@author Fuqiang.Wu</b>")
    public String registrationFace(@ApiParam(value = "人脸Base64数据*必填",required = true)@RequestBody UserImage userimage) throws Exception {
    	return userService.faceRe(userimage.getImageBase());
    }
    
    /**
     *<p>写入token</p> 
     * @version:V0.1     
     * @param oneValueVO
     * @return:OneValueVO
     */
    @PostMapping(value = "/setToken",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(httpMethod = "POST",value = "将用户token写入数据库",notes = "用户点击确定注册后将人脸的tock保存在数据库<br><br><b>@author Fuqiang.Wu</b>")
    public OneValueVO registr(@ApiParam(value = "数据对象*必填",required = true) @RequestBody OneValueVO oneValueVO) {
    	userService.addToken(oneValueVO);
    	return oneValueVO;
    }
    
    /**
     *<p>检测</p> 
     * @version:V0.1     
     * @param userimage	检测人脸数据
     * @return:String	返回检测数据
     */
    @PostMapping(value = "/searchFace",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(httpMethod = "POST",value = "人脸检测登录，更具相似度来判断",notes = "需要传入人脸Base64数据<br><br><b>@authorFuqiang.Wu</b>")
    public String searchFace(@ApiParam(value = "Base64格式*必填",required = true) @RequestBody UserImage userimage) {
    	return userService.searchFace(userimage.getImageBase());
    	
    }
}




