// pages/identify/identify.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    password: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

  },
  getPassword(e){
    //console.log(e.detail.value)
    this.setData({
      password: e.detail.value
    })
  },
  onConfirm(){
    wx.request({
      url: 'http://39.101.74.22:9000/api/add/staff',
      data:{
        userId: app.globalData.openId,
        staffPassword: this.data.password
      },
      method: 'POST',
      header:{
        'content-type': 'application/json',
        'cookie': app.globalData.cookie
      },
      success:(res)=>{
        console.log(res.data)
        if(res.data.code==2000){
          wx.redirectTo({
            url: '/pages/customerlist/customerlist',
          })
        }
        else{
          wx.showModal({
            title: '密码错误',
            content: '返回首页',
            complete: (res) => {
              if (res.cancel) {
                
              }
          
              if (res.confirm) {
                wx.redirectTo({
                  url: '/pages/homepage/homepage',
                })
              }
            }
          })
        }
      }
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