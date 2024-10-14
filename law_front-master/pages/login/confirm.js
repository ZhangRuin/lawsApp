// pages/confirm/confirm.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    open: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  async wantToUse(){
    wx.showModal({
      title: '提示',
      content: '通过登录，我同意智行劳动的服务条款和隐私政策',
      success : async (res) =>{
        if (res.confirm) {
          wx.getUserInfo({
            //成功后会返回
            success:(res)=>{
              app.globalData.userInfo = res.userInfo
              console.log("wx.getUserInfo:")
              console.log(res);

              // 把你的用户信息存到一个变量中方便下面使用
              // 获取userId（需要code来换取）这是用户的唯一标识符
              // 获取code值
              wx.login({
                //成功放回
                success:async (res)=>{
                  console.log("wx.login:")
                  console.log(res);
                  
                //   const login_res = await wx.cloud.callContainer({
                //     config: {
                //         env: 'prod-9gmu0fi979662cb2', // 微信云托管的环境ID
                //     },
                //     path: '/api/login', // 填入业务自定义路径和参数，根目录，就是/
                //     method: 'POST', // 按照自己的业务开发，选择对应的方法
                //     header: {
                //       "X-WX-SERVICE": "law",
                //     },
                //     data: {
                //         code: res.code
                //     }
                // });
                
                //   console.log("api/login:")
                //   console.log(login_res)
                  // app.globalData.cookie= 'satoken=' + login_res.data.result.token,
                  // app.globalData.userId=login_res.data.result.userId
                  // console.log("globalData:")
                  // console.log(app.globalData)
                  // 通过code换取userId
                  wx.request({
                    url: 'https://lawaaaa.online/api/login',
                    data: {
                      code: res.code
                    },
                    method: 'POST',
                    header:{
                      'content-type': 'application/json',
                      'cookie': app.globalData.cookie
                    },
                    success: (res)=>{
                      console.log("api/login:")
                      console.log(res.data)
                      app.globalData.cookie= 'satoken=' + res.data.result.token,
                      app.globalData.userId=res.data.result.userId
                      console.log("globalData:")
                      console.log(app.globalData)
                    },
                    fail: (err) => {
                      console.error(err);
                    }
                  }) 
                }
              })
      
            }
          })
          //url: '/pages/test/test',
          //url: '/pages/homepage/homepage'
          
          wx.redirectTo({
            url: '/pages/homepage/homepage',
          })
        } else if (res.cancel) {
          wx.exitMiniProgram({success: (res) => {}})
        }
      }
    })
  },
  onLoad(options) {
    this.wantToUse()
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