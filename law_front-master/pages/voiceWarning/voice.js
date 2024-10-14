const app = getApp();
const recorderManager = wx.getRecorderManager();


Page({

  /**
   * 页面的初始数据
   */
  data: {
    wannaStop: false,
    buttonColor:"#3e7afe",
    buttonText: '开启实时风险预警',
    isStart:false,
    beforeAnswer:[],
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
          nickname: '实时风险预警AI',
          avatar: '/pages/images/GPT.png',
          message: 'AI分析实时语音中，请耐心等待~',
          type: 'text',
        },
        // 聊天信息
        chatList: [{
                msgId: '2022',
                nickname: '实时风险预警AI',
                avatar: '/pages/images/GPT.png',
                message: '您好，很高兴为您提供实时风险预警服务。我将分析您实时上传的音频信息，识别出可能存在的潜在风险，并告知您，使您可以在充分知晓风险与后果后作出理性的选择。请点击下方按钮开始吧~',
                type: 'text',
            },



        ],
    },

    weChatSIrecording:function(){
      manager.start({duration: 10000, lang: "zh_CN"})
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
},

clickBtn: function() {
  if (!this.data.isStart) {
    this.setData({
      buttonText: '关闭实时风险预警',
      isStart: true,
      buttonColor:"#f36a60"
    });
    this.startRecording()
  } else {
    this.setData({
      buttonText: '开启实时风险预警',
      isStart: false,
      buttonColor:"#3e7afe"
    });
    this.stopRecording()
  }
},



startRecording: async function() {
  this.setData({
    wannaStop : false
  });

  var that = this
  var list = this.data.chatList;
  let timer1 = setTimeout(() => {
    clearTimeout(timer1);
    this.setData({
      chatList: list.concat(this.data.loadingMsg)
    });
    that.scrollToBottom();
  }, 666);

  recorderManager.start({
    format: 'pcm', // 可以设置录音格式
    duration: 59000, // 最长录音时长，单位 ms
    sampleRate: 16000, // 采样率
    numberOfChannels: 1, // 录音通道数
    encodeBitRate: 48000, // 编码码率
  });

  recorderManager.onStart(() => {
    console.log('recorder start')
  });

  recorderManager.onStop((res) => {
    this.setData({
      recordFilePath: res.tempFilePath,
    });
    console.log('recorder stop path :' + res.tempFilePath)
    this.speech2Text(res.tempFilePath);

    if(!this.data.wannaStop){
      this.startRecording()
    }
  });
},

stopRecording: async function() {
  this.setData({
    wannaStop : true,
  });
  console.log("Stop")
  recorderManager.stop();
},
speech2Text: async function(filePath){
  wx.cloud.uploadFile({
    cloudPath: 'voice.pcm',
    filePath: filePath,
    config: {
      env: 'prod-9gmu0fi979662cb2' // 需要替换成自己的微信云托管环境ID
    },
    success: async function (res) {
      console.log(res.fileID);
      const voiceRes = await wx.cloud.callContainer({
        config: {
          env: 'prod-9gmu0fi979662cb2'
        },
        path: '/api/speech/text',
        method: 'POST',
        header: {
          "X-WX-SERVICE": "law",
          'cookie': app.globalData.cookie
        },
        data: {
          file: res.fileID,
        },
      });
      console.log("success");
      console.log(voiceRes.data);
    },
  })

},
// speech2Text: function(filePath) {
//   var list = this.data.chatList;
//       wx.uploadFile({
//         url: 'http://49.235.175.168:9000/api/warn/conversation', 
//         filePath: filePath,
//         name: 'multipartFile',
//         formData: {
//           // 如果有其他参数需要传递，可以在这里添加
//         },
//         header:{
//           'content-type': 'application/json',
//           'cookie': app.globalData.cookie
//         },
//         success: (res) => {
//           const regex = /"result":"(.*?)"/;
//           const match = res.data.match(regex);
//           var str = match[1].replace(/\\n/g, "\n");
//           console.log(match[1])
//           console.log(str)
//           if(match){
//             this.setData({
//               aiAnswer : {
//                 msgId: '2022',
//                 nickname: '实时风险预警AI',
//                 avatar: '/pages/images/GPT.png',
//                 message: str,
//                 type: 'text',
//               }
//             }),   
//             console.log("")
//           }else{
//             this.setData({
//               aiAnswer : {
//                 msgId: '2022',
//                 nickname: '实时风险预警AI',
//                 avatar: '/pages/images/GPT.png',
//                 message: "正则化失败",
//                 type: 'text',
//               }
//             }),   
//             console.log("")
//           }
//           this.setData({
//             chatList: list.concat(this.data.aiAnswer)
//           });
//         },
//         fail: (err) => {
//           console.error(err);
//         }
//       })
//     },

})      