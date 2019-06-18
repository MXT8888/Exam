const app = getApp()
Page({
  data: {
    isLogin: false,     //授权按钮初始状态是显示
    canIUse: wx.canIUse('button.open-type.getUserInfo'),     //授权后显示用户头像和昵称
    detectionStatus: true,      //登陆注册按钮状态
    imagess: "../../images/dati.png",      //中间图片显示
    // 用数组来存放定义的变量
    userinfoNew: {
      city: "",
      code: "",
      country: "",
      head_portrait: "",
      name: "",
      province: ""
    },
  },
  // 初始化先执行onLoad方法
  onLoad: function () {
    var that = this;       //当前的this指定为that
    //获取本地信息
    var city = "userinfoNew.city"
    var code = "userinfoNew.code"
    var country = "userinfoNew.country"
    var head_portrait = "userinfoNew.head_portrait"
    var name = "userinfoNew.name"
    var province = "userinfoNew.province"
    // 登陆方法
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 userId, sessionKey
        that.setData({
          [code]: res.code,
        })
        console.log(res.code);
        //判断授权方法
        wx.getSetting({
          // 授权成功方法
          success(res) {
            if (res.authSetting['scope.userInfo']) {
              that.setData({
                isLogin: true    //授权按钮状态为隐藏
              })
              // 授权成功后，可以直接调用 getUserInfo 获取头像昵称
              wx.getUserInfo({
                // 获取用户信息成功方法
                success: function (res) {
                  //用缓存来保存用户信息
                  wx.setStorage({
                    key: 'userinfo',    //通过key将用户信息进行保存
                    data: res.userInfo    //获取本地用户信息缓存进data
                  })
                  console.log(res.userInfo)   //Log
                  //封装用户对象，把本地用户信息进行封装到上面的userinfoNew
                  that.setData({
                    [city]: res.userInfo.city,
                    [country]: res.userInfo.country,
                    [head_portrait]: res.userInfo.avatarUrl,
                    [name]: res.userInfo.nickName,
                    [province]: res.userInfo.province, 
                  })
                  //通过接口向后端发送请求
                  wx.request({
                    url: 'http://10.20.58.126:8080/v2/open/user/',   //插入用户信息接口
                    method: "POST",    //请求方式
                    data: that.data.userinfoNew,    //向服务器发送userinfoNew里面封装好的本地数据
                    // 发送信息成功后
                    success: function (e) {
                      // 通过Key---缓存获取到的userid
                      wx.setStorage({
                        key: 'userid',
                        data: e.data.userId
                      })
                      console.log(e)    //获取用户插入到服务器的数据
                      //通过toke进行判断登陆还是注册
                      if (e.data.token == null) {
                        that.setData({
                          detectionStatus:  false
                        })
                      } else {
                        that.setData({
                          detectionStatus: true
                        })
                      }
                    }
                  })
                }
              })
            } 
            else {
              that.setData({
                isLogin: false
              })
            }
          }
        })
      }
    })
  },
  // 点击按钮进行授权-----授权后按钮隐藏
  bindGetUserInfo(e) {
    this.setData({
      isLogin: true
    });
    this.onLoad()
  },
  //注册按钮显示
  register: function () {
    wx.redirectTo({
      url: '../Register/Register',
    })
  },
  //登陆按钮显示
  login: function () {
    wx.redirectTo({
      url: '../Login/Login',
    })
  },
})