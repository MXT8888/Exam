var util = require('../../../../utils/util.js');    //util工具包
Page({
  data: {
    aboutMe: {},
    newTime:''
  },
  onLoad: function (userid) {
   var that = this    
    var time = util.formatTime(new Date())     //time等于当前时间
    // 再通过setData更改Page()里面的data，动态更新页面的数据
    that.setData({
      newTime: time
    })
    // 提示框显示
    wx.showToast({
      title: '加载中',
      icon: 'loading',
      duration: 300
    })
    // 向后台发送一个数据----查询用户详细信息
    wx.request({
      url: 'http://10.20.58.126:8080/v2/open/exam/findMeById/' + userid.id,
      method: 'GET',
      header: {
        "Content-Type": "application/json"
      },
      // 成功后执行的方法
      success: function (e) {
        console.log(e)
        that.setData({
          aboutMe: e.data
        })
      }
    })
    //获取用户id
    wx.getStorage({
      key: 'userId',
      success: function (res) {
        console.log(res)
      },
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