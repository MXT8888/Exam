var imagePath = '';    // 用来存放图片的路径
Page({
  data: {
    smileUrl: '../../images/Smile.png',
    camera: '../../images/camera.png',
    canback: '../../images/canback.png',
    photoStatus: true,     //显示文本一，文本二，相机一，照相二，点击后改变
    userImage: '',    ////保存相机拍照后的存放路径
   // 用来存放转换的Base64位图片
    userimgebaser: {
      imageBase: ''
    },
    // 通过缓存加载userId和token
    ot: {
      userId: '',
      token: ''
    },
    // 汇总对象
    total: {}
  },
  //启动相机拍照
  camera: function () {
    var that = this    //当前的this指定为that
    var imageBase = "userimgebaser.imageBase"    //userimgebaser封装到里imageBase
     // 调用微信官方的拍照方法
    this.ctx.takePhoto({
      quality: 'high',    //拍照质量
      // 拍照成功后执行的方法
      success: function (res) {
        // 提示框
        wx.showLoading({
          title: '检测中'
        })
        imagePath = res.tempImagePath,    // 获取临时路径存进事先定义的imagePath
          // 微信官方获取文件系统管理器
          wx.getFileSystemManager().readFile({
            filePath: imagePath, //选择图片返回的相对路径
            encoding: 'base64', //编码格式
            success: res => { //成功的回调
              that.setData({
                [imageBase]: res.data
              })
              //提示框
              wx.showToast({
                title: '检测中',
                icon: 'loading',
                duration: 2000
              })
              // 先后段发送一个请求
              wx.request({
                url: 'http://10.20.58.126:8080/v2/open/user/searchFace',
                method: 'POST',    //请求方式
                data: that.data.userimgebaser,    //将本地转码的imageBase64存进服务器
                header: {
                  "Content-Type": "application/json"
                },
                // 成功后应执行的方法
                success: function (e) {
                  console.log(that.data.userimgebaser)
                  // 获取人脸数据与后台的人脸进行对比
                  if (e.data.result !== null) {
                    // 如果相似度60,就会显示检测成功
                    if (60 < e.data.result.user_list["0"].score) {
                      wx.showToast({
                        title: '检测成功',
                        icon: 'success',
                        duration: 2000
                      })
                      //设置photoStatus和canback的数据
                      that.setData({
                        photoStatus: false,
                        canback: imagePath
                      })
                    } else {
                      // 显示提示框
                      wx.showToast({
                        title: '人脸检测失败 重新检测',
                        image: '../../images/error.png',
                        duration: 1000
                      })
                    }
                  } else {
                    // 显示提示框
                    wx.showToast({
                      title: '请勿遮盖脸部',
                      image: '../../images/error.png',
                      duration: 1000
                    })
                  }
                },
                // 如果检测失败返回的结果
                fail: function (e) {
                  wx.showToast({
                    title: '检测失败',
                    image: '../../images/error.png',
                    duration: 1000
                  })
                }
              })
            }
          })
      }
    })
  },
  //进入答题方法
  getinto: function () {
    // 显示提示框
    wx.showToast({
      title: '登录中',
      icon: 'loading',
      duration: 1000
    })
    // 跳转到答题页面
    wx.switchTab({
      url: '/pages/Answer/Answer'
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