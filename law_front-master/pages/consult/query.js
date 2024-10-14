// pages/query/query.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    aiAnswer: [],
    content: '',
        // 当前登录者信息
        login: {
            id: '2023',
            user: '您',
            avatar: '/pages/images/user.png'
        },
        loadingMsg:{
          msgId: '2022',
          nickname: '劳动法律咨询AI',
          avatar: '/pages/images/GPT.png',
          message: 'AI生成回答中，请耐心等待~',
          type: 'text',
        },
        // 聊天信息
        chatList: [{
                msgId: '2022',
                nickname: '劳动法律咨询AI',
                avatar: '/pages/images/GPT.png',
                message: '您好，很高兴为您提供劳动法咨询服务。请问您有什么问题需要咨询吗？如果对回答不满意，点击屏幕左下角即可切换人工服务哦~(提供的法律建议仅供参考，不具备任何法律效力)',
                type: 'text',
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
  var msg = {
      msgId: this.data.login.id,
      nickname: this.data.login.user,
      avatar: this.data.login.avatar,
      message: this.data.content,
      type: 'text',
  }
  
  wx.request({
    url: 'https://lawaaaa.online/api/law/question',
    data:{
      question: this.data.content
    },
    method: 'POST',
    header:{
      'content-type': 'application/json',
      'cookie': app.globalData.cookie
    },
    success: (res)=>{
      if(res.data.code == -1){
        console.log(res.data)
        this.setData({
          aiAnswer : {
            msgId: '2022',
            nickname: '劳动法律咨询AI',
            avatar: '/pages/images/GPT.png',
            message: "抱歉，我无法理解您的意思。请提供更多细节，或者指明你想要咨询的问题。如有需要，可选择左下角切换人工服务哦~",
            type: 'text',
          }
        })
        return
      }
      console.log("api/lawQuestion:")
      console.log(res.data)
      let aiAnswers = [];
      var num = res.data.result.lawQuestions.length
      // var numStr = num.toString()
      // var ansArray = []
      for(let i = 0; i<num+1 ;i++){
        if(i == 0){
          aiAnswers.push({
            msgId: '2022',
            nickname: '劳动法律咨询AI',
            avatar: '/pages/images/GPT.png',
            message: "感谢您的提问。根据您的描述，我发现您的问题与以下" + num + "种情况有关。",
            type: 'text'
          });
          continue;
        }
        aiAnswers.push({
          msgId: '2022',
          nickname: '劳动法律咨询AI',
          avatar: '/pages/images/GPT.png',
          message: i+"、"+res.data.result.lawQuestions[i - 1].aiTitle + ':\n' + "\t\t\t\t" + res.data.result.lawQuestions[i-1].answer,
          type: 'text'
        });
      }
      this.setData({
        aiAnswer : aiAnswers
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
    let timer1 = setTimeout(() => {
      clearTimeout(timer1);
      this.setData({
        chatList: list.concat(msg).concat(this.data.loadingMsg)
      });
      that.scrollToBottom();
    }, 666);
    
    let timer2 = setTimeout(() => {
      clearTimeout(timer2);
      let chatList = list.concat(msg); // 初始化 chatList

      for (let i = 0; i < this.data.aiAnswer.length; i++) {
        chatList.push(this.data.aiAnswer[i]); // 将 aiAnswer 中的每个元素添加到 chatList 中
      }
    
      this.setData({
        chatList: chatList
      });
      that.scrollToBottom();
    }, 5000);

})
},

// 发送监听
// async sendClick() {
//   var that = this;
//   var list = this.data.chatList;
//   var msg = {
//       msgId: this.data.login.id,
//       nickname: this.data.login.user,
//       avatar: this.data.login.avatar,
//       message: this.data.content,
//       type: 'text',
//   }

//   this.setData({
//       chatList: list.concat(msg)
//   }, () => {
//       that.scrollToBottom();
//       that.setData({
//           content: '',
//       })
//       let timer1 = setTimeout(() => {
//         clearTimeout(timer1);
//         this.setData({
//           chatList: list.concat(msg).concat(this.data.loadingMsg)
//         });
//         that.scrollToBottom();
//       }, 666);
//   })

//   const questionRes = await wx.cloud.callContainer({
//     config: {
//         env: 'prod-9gmu0fi979662cb2', // 云托管环境ID
//     },
//     path: '/api/law/question', // 问题接口路径
//     method: 'POST', // POST 请求
//     header: {
//         "X-WX-SERVICE": "law",
//         'content-type': 'application/json',
//         'cookie': app.globalData.cookie // 假设 cookie 存在全局变量中
//     },
//     data: {
//         question: this.data.content // 假设 content 是问题内容
//     }
// });
//         // 处理问题请求的返回结果，更新页面显示等操作
//         if (questionRes.data.code == -1) {
//           console.log(questionRes.data);
//           this.setData({
//               aiAnswer: {
//                   msgId: '2022',
//                   nickname: '劳动法律咨询AI',
//                   avatar: '/pages/images/GPT.png',
//                   message: "抱歉，我无法理解您的意思。请提供更多细节，或者指明你想要咨询的问题。如有需要，可选择左下角切换人工服务哦~",
//                   type: 'text',
//               }
//           });
//           return;
//       }

//       console.log("api/lawQuestion:");
//       console.log(questionRes.data);
//       let aiAnswers = [];
//       var num = questionRes.data.result.lawQuestions.length;

//       for (let i = 0; i < num + 1; i++) {
//         if (i == 0) {
//             aiAnswers.push({
//                 msgId: '2022',
//                 nickname: '劳动法律咨询AI',
//                 avatar: '/pages/images/GPT.png',
//                 message: "感谢您的提问。根据您的描述，我发现您的问题与以下" + num + "种情况有关。",
//                 type: 'text'
//             });
//             continue;
//         }
//         aiAnswers.push({
//             msgId: '2022',
//             nickname: '劳动法律咨询AI',
//             avatar: '/pages/images/GPT.png',
//             message: i + "、" + questionRes.data.result.lawQuestions[i - 1].aiTitle + ':\n' + "\t\t\t\t" + questionRes.data.result.lawQuestions[i - 1].answer,
//             type: 'text'
//         });
//     }

//       this.setData({
//         aiAnswer: aiAnswers
//       })

//       this.setData({
//           chatList: list.concat(msg).concat(this.data.loadingMsg)
//       }, () => {

//           that.scrollToBottom();

//           let timer2 = setTimeout(() => {
//             clearTimeout(timer2);
//             let chatList = list.concat(msg).concat(this.data.loadingMsg); 
//             // 初始化 chatList

//             for (let i = 0; i < this.data.aiAnswer.length; i++) {
//               chatList.push(this.data.aiAnswer[i]); // 将 aiAnswer 中的每个元素添加到 chatList 中
//             }
          
//             this.setData({
//               chatList: chatList
//             });
//             that.scrollToBottom();
//           }, 2000);
      
//       })
// },

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