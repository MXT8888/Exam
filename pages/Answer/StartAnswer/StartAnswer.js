Page({
  data: {    //页面的初始数据
    question: {},
    questionPotion: [   //选项的存储
    ],
    answer: "",    //正确答案
    userAnswer: '',     //用户选择的答案
    userId: 0,        //用户id
    score: 0,      //初始分数为0
    trueOrFlase: false,      //答题情况
    answernumber: 0,       //答题次数
    animationData: {},       //动画
    // 通过ot对象保存userId和token
    ot: {
      userId: 0,
      token: ''
    },
    total: {}   //汇总对象
  },
  //下一题按钮
  startanswering: function () {
    var that = this
    //开始新的答题
    wx.redirectTo({
      url: 'StartAnswer',
    })
  },
  // 选择选项方法
  getradio: function (e) {
    var that = this    //当前的this=that
    let index = e.currentTarget.dataset.id;   //通过当前的id来循环有多少个ABCDEFG
    let radio = this.data.questionPotion;      //通过当前的questionPotion来判断选的是哪个选项框进行判断对错
    //自定义的动画效果 
    const animation = wx.createAnimation({
      duration: 1500,    //动画时间
      timingFunction: 'ease-out',    //动画效果
    })
    // 当前的animation等与animation
    this.animation = animation
    // 循环答题选项列表
    for (let i = 0; i < radio.length; i++) {
      this.data.questionPotion[i].checked = false; 
    }
  // 答题进行判断
    if (radio[index].checked) {
      this.data.questionPotion[index].checked = false;//通过radio也就questionPotion来判断选的是哪个选项框进行判断对错
    } else {
      this.data.questionPotion[index].checked = true;//通过radio也就questionPotion来判断选的是哪个选项框进行判断对错
    }
    // 获取用户选择的答案
    let userRadio = radio.filter((item, index) => {  //filter过滤器---过滤循环和当前下标
      return item.checked == true;      //返回当前的循环==true
    })
    var number = that.data.answernumber   //选中选项的index
    var newscore = that.data.score   //及时更新分数
    number++  //index++方法
    //判断选的答案是否正确进行加减分赋值
    if (this.data.answer === userRadio["0"].option) {
      that.setData({ trueOrFlase: true, score: newscore + 10 })    //如果正确+10
    }else{
      that.setData({ trueOrFlase: false, score: newscore - 5})      //如果错误-5分
    }
    //获取用户的答案
    that.setData({
      userAnswer: userRadio["0"].option
    })
    //加分减分动画
    setTimeout(function () {
      animation.translateY(-70).step()    //translateY的y轴方向进行移动
      that.setData({
        // 导出动画队列。export 方法每次调用后会清掉之前的动画操作。
        animationData: animation.export()
      })
    }.bind(this), 100)  //bind绑定当前的this.100是设置动画的时间
    //先后端发送请求-----计算答题结果
    wx.request({
      url: 'http://10.20.58.126:8080/v2/open/exam/submitAnswers',
      method: 'POST',     //请求方式
      data: {
        topicID: that.data.question.id,     //答题的编号存储
        userID: that.data.userId,       //答题用户userId的存储 
        submit_Answers: that.data.userAnswer    //用户提交答案的存储
      },
      header: {
        "Content-Type": "application/json"
      },
      // 成功后执行的方法
      success: function (res) {
        console.log(res)
        //提示框显示 
        wx.showToast({
          title: '下一题',
          icon: 'loading',
          duration: 200
        })
      },
      // 如果失败执行的方法
      fail: function (e) {
        wx.showToast({
          title: '考试宝出差了',
          image: '/image/warning.png',
          duration: 200
        })
      }
    })
    // 设置数据
    that.setData({
       questionPotion: this.data.questionPotion,    //答题选项赋值
       answernumber: number      //答案序号赋值
       })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    var token = "ot.token";  //获取用户的token
    var userIds = "ot.userId"//获取用户的userId
    //提示框获取试题
    wx.showToast({
      title: '加载中',
      icon: 'loading',
      duration: 300
    })
    // 先后段发送请求
    wx.request({
      url: 'http://10.20.58.126:8080/v2/open/exam/findOneQuestion',
      method: 'GET',    //请求方式
      header: {
        "Content-Type": "application/json"
      },
      // 成功后执行的方法
      success: function (res) {
        console.log(res)
        for (var i = 0; i < res.data.questionOptions.length;i++){
          res.data.questionOptions[i].option_CONTENT = res.data.questionOptions[i].option_CONTENT.split('：')[1]
        }
        // 设置数据
        that.setData({
          question: res.data,     //获得的正确的答案
          answer: res.data.answer,   //获得的正确的答案
          questionPotion: res.data.questionOptions, //获得的正确的答案
        })
      }
    })
    //获得总分方法
    wx.getStorage({
      key: 'userid',     //通过key保存用户的userId
      // 成功后执行的方法
      success(res) {
        that.setData({
          [userIds]: res.data,
        })
        wx.request({
          url: 'http://10.20.58.126:8080/v2/open/exam/findResult/' + that.data.ot.userId,
          method: 'GET',    //请求方式
          header: {
            "Content-Type": "application/json"
          },
          // 成功后执行方法
          success: function (e) {
            console.log(e)
            // 设置数据
            that.setData({
              total: e.data,
              score: e.data.current_total_score
            })
            //通过缓存保存用户id
            wx.getStorage({
              key: 'userId',
              //成功后执行的方法
              success: function (res) {
                that.setData({
                  userId: res.data
                })
              },
            })
          }
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