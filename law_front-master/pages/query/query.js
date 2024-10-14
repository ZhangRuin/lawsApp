// pages/query/query.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
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
                message: '你好，我是劳动法律咨询AI。有什么可以帮您？如果对我的回答不满意，点击右下角按钮即可切换人工服务哦（提供的法律建议仅供参考，不具备任何法律效力）',
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
  // 输入监听
  inputClick(e) {
    this.setData({
        content: e.detail.value
    })
},
// 发送监听
sendClick() {
    var that = this;
    var list = this.data.chatList;
    // 获取当前时间
    var date = new Date();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var hour = date.getHours();
    var minu = date.getMinutes();
    var now1 = month < 10 ? '0' + month : month;
    var now2 = day < 10 ? '0' + day : day;
    // 组装数据
    var msg = {
        msgId: this.data.login.id,
        nickname: this.data.login.user,
        avatar: this.data.login.avatar,
        message: this.data.content,
        type: 'text',
        date: now1 + '-' + now2 + ' ' + hour + ':' + minu
    }
    
    wx.request({
      url: 'http://39.101.74.22:9000/api/law/question',
      data:{
        question: this.data.content
      },
      method: 'POST',
      header:{
        'content-type': 'application/json',
        'cookie': app.globalData.cookie
      },
      success: (res)=>{
        console.log(res.data)
        //console.log(app.globalData.cookie)
        //app.globalData.cookie= 'satoken=' + res.data.result.token
        /*var that = this;
        var list = this.data.chatList;*/
        var num = res.data.result.lawQuestions.length
        var ans = ''
        for(let i = 0;i < num;i++){
          ans += res.data.result.lawQuestions[i].aiTitle + ':\n'
          + res.data.result.lawQuestions[i].answer + '\n'
        }
        this.setData({
          aires : {
            msgId: '2022',
            nickname: '劳动法律咨询AI',
            avatar: '/pages/images/R-C.png',
            message: ans,
            type: 'text',
            //date: now1 + '-' + now2 + ' ' + hour + ':' + minu
        }
        })
      }
    })
    this.setData({
        chatList: list.concat(msg)
    }, () => {
        that.scrollToBottom();
        that.setData({
            content: '',
        })
        let timer = setTimeout(() => {
          clearTimeout(timer)
          this.setData({
            chatList: list.concat(msg).concat(this.data.aires)
          })
        }, 5000)
    })
},
handleContact(e){
  console.log(e.detail)
},
// 滑动到最底部
scrollToBottom() {
    setTimeout(() => {
        wx.pageScrollTo({
            scrollTop: 200000,
            duration: 3
        });
    }, 600)
}
})