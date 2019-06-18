var util = require('../../../utils/util.js');    //util工具包属性设置
Page({
  data: {
    answerRanking: [],    // 定义一个数组存放数据对象
    newTime: ''    // 定义当前时间
  },
  // 生命周期函数--监听页面加载
  onLoad: function (options) {
    this.public();
  },
public:function(){
  var that = this    // var that = this就是将当前的this对象复制一份到that变量中
  var time = util.formatTime(new Date())    // 调用函数时，传入new Date()参数，返回值是日期和时间
  that.setData({    // 再通过setData更改Page()里面的data，动态更新页面的数据
    newTime: time
  })
  // 页面提示信息
  wx.showToast({
    title: '获取中',
    icon: 'loading',
    duration: 500
  })
  // 先后段发送一个请求------查询排名的接口
  wx.request({
    url: 'http://10.20.58.126:8080/v2/open/exam/findRanking',
    method: 'GET',
    header: {
      "Content-Type": "application/json"
    },
    // 请求成功后的方法
    success: function (e) {
      console.log(e)
      that.setData({
        answerRanking: e.data
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
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  },
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  },
  //分享
  share: function () {

  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    let that = this;
    return {
      title: '答题宝前50排行榜', // 转发后 所显示的title
      path: '/pages/rankingList/rankingList', // 相对的路径
      success: (res) => {    // 成功后要做的事情
        console.log(res.shareTickets[0])
        wx.getShareInfo({
          shareTicket: res.shareTickets[0],
          success: (res) => {
            that.setData({
              isShow: true
            })
            console.log(that.setData.isShow)
          },
          fail: function (res) { console.log(res) },
          complete: function (res) { console.log(res) }
        })
      },
      // 分享失败
      fail: function (res) {
        console.log(res)
      }
    }
  }
})