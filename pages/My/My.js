var util = require('../../utils/util.js');    //util工具类
Page({
  data: {
    aboutMe: {},    // 创建一个对象
    newTime: ''    // 创建一个时间对象
  },
  // 初始化执行
  onLoad: function (options) {
    this.public();
  },

  public:function(){
    var that = this     // var that = this就是将当前的this对象复制一份到that变量中
    var time = util.formatTime(new Date())     // 通过util获取当前的时间
    // 再通过setData更改Page()里面的data，动态更新页面的数据
    that.setData({
      newTime: time
    })
    // 界面显示消息提示框
    wx.showToast({
      title: '加载中',
      icon: 'loading',
      duration: 300
    })
    //通过缓存获取用户id
    wx.getStorage({
      // 用户ID
      key: 'userId',
      // 定义一个成功的方法
      success: function (res) {
        console.log(res)
        // 先后端发送一个请求
        wx.request({
          // 接口地址
          url: 'http://10.20.58.126:8080/v2/open/exam/findMeById/' + res.data,
          // 请求方式---获取
          method: 'GET',
          header: {
            "Content-Type": "application/json"
          },
          // 请求成功后怎样
          success: function (e) {
            console.log(e)
            that.setData({
              aboutMe: e.data
            })
          }
        })
      },
      // 失败的方法
      fail: function () {
        wx.showToast({
          title: '服务器累了',
          image: '../../image/error.png',
          duration: 1000
        })
      }
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  },
  /**
   * 生命周期函数--监听页面显示------进行动态数据的绑定
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
    this.public();
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