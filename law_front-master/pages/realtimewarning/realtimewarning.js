// pages/realtimewarning/realtimewarning.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    on: false,
    aires:{},
    content: '',
        // 当前登录者信息
        login: {
            id: '2023',
            user: '您',
            avatar: 'https://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRna42FI242Lcia07jQodd2FJGIYQfG0LAJGFxM4FbnQP6yfMxBgJ0F3YRqJCJ1aPAK2dQagdusBZg/0'
        },
        // 聊天信息
        chatList: [{
                msgId: '2022',
                nickname: '劳动法律咨询AI',
                avatar: '/pages/images/R-C.png',
                message: '你好，我是劳动法律智能小助手。如果您想要开启实时风险提示，请点击开始',
                type: 'text',
                //date: '05-02 14:24' // 每隔5分钟记录一次时间
            }
        ],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.scrollToBottom();
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },
  onStart(){
    this.setData({
      on: true
    })
  },
  onStop(){
    this.setData({
      on: false
    })
  },
  scrollToBottom() {
    setTimeout(() => {
        wx.pageScrollTo({
            scrollTop: 200000,
            duration: 3
        });
    }, 600)
  }
})