var imagePath = '';     // 用来存放图片的路径
var imageBase64 = '';    // 用来存放转换的Base64位图片
var imageUser;    // 定义一个图片的变量
Page({
  data: {
    smileUrl: '../../images/Smile.png',
    camera: '../../images/camera.png',
    canback: '../../images/canback.png',
    photoStatus: true,     //显示文本一，文本二，相机一，照相二，点击后改变
    saveImagePath: '',      //保存相机拍照后的存放路径
   // 用来存放转换的Base64位图片
    userimgebaser: {
      imageBase: ""
    },
    face_token: '',    // 定义一个变量foce_token
    // 通过缓存加载userId和token
    ot: {
      userId: 0,
      token: ''
    }
  },
  //点击进行拍照
  camera: function () {
    var that = this    //当前的this指定为that
    var imageBase = "userimgebaser.imageBase" //imageBase封装到userimgebaser里
    // 调用微信官方的拍照方法
    this.ctx.takePhoto({
      quality: 'high',    //拍照质量
      // 拍照成功后执行的方法
      success: function (res) {
        // 提示框
        wx.showLoading({
          title: '添加人脸数据中'
        })
        imagePath = res.tempImagePath,     // 获取临时路径存进事先定义的imagePath
          console.log(imagePath);
          console.log("-------------------------")
          //设置数据
          that.setData({
          photoStatus: false,  //隐藏文本一，文本二，相机一，照相二，点击后改变
            canback: res.tempImagePath
          })
        // 微信官方获取文件系统管理器
        wx.getFileSystemManager().readFile({
          filePath: imagePath,     //选择图片返回的相对路径
          encoding: 'base64',     //编码格式
          success: res => {     //成功的回调
            imageBase64: res.data   //进行转码
            // 将转码后的图片用imageBase保存
            that.setData({
              [imageBase]: res.data  
            })
            // 提示框显示注册中
            wx.showToast({
              title: '注册中',
              icon: 'loading',
              duration: 2000
            })
           imageUser = that.data.userimgebaser    //存放事先已经设置好的图像变量中
            console.log(imageUser)
            // 先后段发送一个请求
            wx.request({
              url: 'http://10.20.58.126:8080/v2/open/user/faceRe',
              method: 'POST',    //请求方式
              data: that.data.userimgebaser,    //将本地转码的imageBase64存进服务器
              header: {
                "Content-Type": "application/json"
              },
              // 成功后应执行的方法
              success: function (e) {
                console.log(e)
                //用face_token保存用户数据
                that.setData({
                  face_token: e.data
                })
                // 提示框显示
                wx.showToast({
                  title: '注册成功',
                  icon: 'success',
                  duration: 1000
                })
              }, fail: function (e) {    //如果失败就会弹出此方法
                wx.showToast({
                  title: '登录失败',
                  image: '../../image/error.png',
                  duration: 1000
                })
              }
            })
          }
        })
        wx.hideLoading()    //隐藏加载方法
      }
    })
  },
  //点击按钮进行登陆
  oklogo: function () {
    var that = this
    var token = "ot.token";    //获取ot.token里面的token值
    var userId = "ot.userId"    //获取ot.userId里面的userId值
    // 通过缓存保存用户信息
    wx.getStorage({
      key: 'userid',    //通过key进行识别
      // 访问成功后执行的方法
      success(res) {
        // 设置userId，token
        that.setData({
          [userId]: res.data,
          [token]: that.data.face_token
        })
        console.log(that.data.ot)
        // 发送一个请求向后端插入人脸数
        wx.request({
          url: 'http://10.20.58.126:8080/v2/open/user/setToken',
          method: 'POST',    //请求方式
          data: that.data.ot,    //将当前的ot存进data
          header: {
            "Content-Type": "application/json"
          },
          // 成功后控制太显示更新成功
          success: function (e) {
            console.log("更新数据成功")
          }
        })
      }
    })
    // 拍照成功后跳转-----进行页面登陆
    wx.navigateTo({
      url: '../../UserOperation/Authorization/Authorization'
    })
  },
  // 注册人脸重拍
  nologo:function(){
    wx.redirectTo({
      url: '../../UserOperation/Register/Register',
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.ctx = wx.createCameraContext()
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  },
  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  },
  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  },
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  }
})