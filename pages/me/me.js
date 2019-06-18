// pages/me/me.js
Page({
  /**
   * 页面的初始数据
   */
  data: {
    casArray: ['文学', '地理', '文学', '科技', '历史','军事','经济','政治','艺术','音乐','体育'], //下拉框内容
    casIndex: 0,  //默认是文学
    b : 1,
    list:[
      {
      xuanxiang:"A",
      xuanxiangyi:"选项一",
      },
    ],
    abcd: ["A", "B", "C", "D", "E", "F", "G"],
    yiersansi: ['一', '二', '三', '四', '五','六','七']
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },
  addCheck: function () {
    var a = ++this.data.b
    console.log(this.data.abcd[0])
    var aa = {
      xuanxiangs: this.data.abcd[0],
      xuanxiangyis: this.data.yiersansi[1],
    }
    var newlist = this.data.list
    this.setData({
      list: newlist.concat(aa),
    })
  },

  bindCasPickerChange: function (e) {
    console.log('乔丹选的是', this.data.casArray[e.detail.value])
    if (e.detail.value == 4) {
      this.setData({ reply: true })
    } else {
      this.setData({ reply: false })
    }
    this.setData({
      casIndex: e.detail.value
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