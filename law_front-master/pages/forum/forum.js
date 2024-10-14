// pages/blogs/blogs.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    blogs:[
      {
        id: '1',
        avatar: '/pages/images/R-C.png',
        updater: '小王',
        title:'cdjnaicjsamcknsacjnsicdsoancjdncbjdbddwjcsc\nsjidsiancknskckxsnj\najsiacnsjancindisaocxisncjndnacinc\nisnacixmksmckdnjncijijccndsnvjnfneia',
        content:'this is a test',
        pictures:[
          '/pages/images/1.png',
          '/pages/images/2.png',
          '/pages/images/3.png',
          '/pages/images/background.jpg'
        ]
      },
      {
        updater: '小王',
        title:'test',
        content:'this is a test',
        pictures:[]
      }
    ]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

  },
  onEdit(){
    wx.navigateTo({
      url: '/pages/postblog/postblog',
    })
  },
  onDetail:function(e){
    let id = e.currentTarget.dataset.id
    wx.navigateTo({
      url: '/pages/blog_comment/blog_comment?id='+id,
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