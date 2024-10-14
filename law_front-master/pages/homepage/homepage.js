// pages/homepage/homepage.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    indicatordots:true,
    interval:5000,
    autoplay:true,
    
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
    wx.hideHomeButton()
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
  onIdentify(){
    wx.navigateTo({
      url: '/pages/identify/identify',
    })
  },
  onQuery(){
    wx.navigateTo({
      url: '/pages/query/query'
    })
  },
  onWarn(){
    wx.navigateTo({
      url: '/pages/warn/warn'
    })
  },
  onConflict(){
    wx.navigateTo({
      url: '/pages/conflict/conflict'
    })
  },
  onLegalDoc(){
    wx.navigateTo({
      url: '/pages/legalDocument/document'
    })
  },
  onChannels(){
    wx.navigateTo({
      url: '/pages/channels/4types'
    })
  },
  onMore1(){
    wx.navigateTo({
      url: '/pages/consultAndConflict/allfunctions'
    })
  },
  onMore2(){
    wx.navigateTo({
      url: '/pages/contractAndConversation/both'
    })
  },
})