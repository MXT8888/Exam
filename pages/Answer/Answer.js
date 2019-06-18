var app = new App();    //new一个实例化的App对象进行使用
Page({
  data: {
    userInfor: {},    //保存用户信息的对象
    motto: "开始答题",    //双向绑定
    imagess: "../images/one.png",    //中间图片显示
    // 保存用户的userId和token
    ot: {
      userId: 0,
      token: ''
    },
    // 汇总对象
    total: {}
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function () {
    this.public();
  },
  // 封装数据
  public:function(){
    var that = this   //当前的this=that
    var token = "ot.token";    //将token值存起来
    var userIds = "ot.userId"   //将userId值存起来
    //通过缓存存储用户的Id
    wx.getStorage({
      key: 'userid',
      // 如果成功后执行的方法
      success: function (res) {
        console.log(res.data)
        that.setData({
          [userIds]: res.data,
        })
        //发送一个请求获取用户id与总分
        wx.request({
          url: 'http://10.20.58.126:8080/v2/open/exam/findResult/' + that.data.ot.userId,
          method: 'GET',    //请求方式
          header: {
            "Content-Type": "application/json"
          },
          // 如果请求成功后
          success: function (e) {
            //把值汇总到对象
            that.setData({
              total: e.data
            })
            console.log(e);
            // 通过缓存保存用户的userId
            wx.setStorage({
              key: 'userId',
              data: e.data.userID,
            })
          }
        })
      }
    })
  },
  guize: function () {
    wx.navigateTo({
      url: 'Regulation/Regulation',
    })
  },
  startTitle: function () {
    wx.navigateTo({
      url: 'StartAnswer/StartAnswer',
    })
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
    this.public();
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
  },
 
})