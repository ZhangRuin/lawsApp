// pages/blog_comment/blog_comment.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    id:'',
    avatar:'/pages/images/R-C.png',
    updater:'小王',
    content:"cdjnaicjsamcknsacjnsicdsoancjdncbjdbddwjcsc\nsjidsiancknskckxsnj\najsiacnsjancindisaocxisncjndnacinc\nisnacixmksmckdnjncijijccndsnvjnfneia",
    pictures:[
      '/pages/images/1.png',
      '/pages/images/2.png',
      '/pages/images/3.png',
      '/pages/images/background.jpg'
    ],
    comments:[
      {
        id:1,
        avatar:'/pages/images/R-C.png',
        user:'小李',
        content:'有道理',
        likes:5
      },
      {
        id:1,
        avatar:'/pages/images/R-C.png',
        user:'小李',
        content:'有道理',
        likes:5
      }
    ]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad:function(options) {
    this.setData({
      id:options.id
    })
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

  }
})