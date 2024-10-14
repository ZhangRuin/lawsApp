// pages/allfunctions/allfunctions.js
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

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
  onQuery(){
    wx.navigateTo({
      url: '/pages/query/query'
    })
  },
  onContract(){
    wx.navigateTo({
      url: '/pages/contract/contract'
    })
  },
  onConflict(){
    wx.navigateTo({
      url: '/pages/conflict/conflict'
    })
  },
  onForum(){
    wx.navigateTo({
      url: '/pages/forum/forum'
    })
  },
  onChannel(){
    wx.navigateTo({
      url: '/pages/channel/channel'
    })
  },
  onVoice(){
    wx.navigateTo({
      url: '/pages/voice/voice'
    })
  },
})