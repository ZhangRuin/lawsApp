// pages/query/query.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    index:0,
    imgIndexArr:[],
    imagePath: '',
    imagePaths: [],
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
          message: 'AI分析合同内容中，请耐心等待~',
          type: 'text',
        },
        // 聊天信息
        chatList: [{
                msgId: '2022',
                nickname: '劳动法律咨询AI',
                avatar: '/pages/images/GPT.png',
                message: '您好，很高兴为您提供合同审查预警服务。我将分析您上传的合同，识别出可能存在的潜在风险。若对AI回答的结果不满意，可点击左下角切换人工客服哦～',
                type: 'text',
            }
        ],
    },
    //点击放大图片
    previewImage: function(event) {
      var currentImage = this.data.imagePath;
      wx.previewImage({
        current: currentImage,
        urls: [currentImage]
      })
    },

uploadImg: function() {
    var that = this;
    var list = that.data.imgIndexArr; 
    var msg = {
        msgId: this.data.login.id,
        nickname: this.data.login.user,
        avatar: this.data.login.avatar,
        message: this.data.content,
        type: 'text',
    }

    wx.chooseImage({
      count: 9,
      sizeType: ['original', 'compressed'],
      sourceType: ['album', 'camera'],
      success: function (res) {
        const tempFilePaths = res.tempFilePaths;
        const length = tempFilePaths.length;
        // 更新页面数据，保存选择的图片路径
        this.setData({
            imagePaths: tempFilePaths,
            // imagePath: tempFilePaths[0]
        });
        console.log(tempFilePaths);
        var index = that.data.index;
        var list = [];
    
        for (let i = 0; i < length; i++) {
            list.push(index);
            index += 1;
            var templist = that.data.chatList;
            that.setData({
                chatList: templist.concat(msg)
            });
        }
        that.setData({
            imgIndexArr: list,
        });
        console.log(that.data.imgIndexArr);
        var contractContent = '';
    
        tempFilePaths.forEach(function(tempFilePath, index) {
            wx.uploadFile({
                url: 'https://lawaaaa.online/api/warn/img2Text',
                filePath: tempFilePath,
                name: 'multipartFiles',
                formData: {
                    // 如果有其他参数需要传递，可以在这里添加
                },
                method: 'POST',
                header: {
                    'content-type': 'application/json',
                    'cookie': app.globalData.cookie
                },
                success: (res) => {
                    console.log("img2Text:");
                    console.log(res.data);
                    contractContent.concat(res.data)
                    var templist = that.data.chatList;
                    this.setData({
                      chatList: templist.concat(msg)
                  });
                },
            });
        }); // 这里添加逗号

        var templist = that.data.chatList;
        this.setData({
          chatList: templist
      }, () => {
          that.scrollToBottom();
          that.setData({
              content: '',
          })
          let timer1 = setTimeout(() => {
            clearTimeout(timer1);
            var list = that.data.chatList;
            this.setData({
              chatList: list.concat(this.data.loadingMsg)
            });
            that.scrollToBottom();
          }, 666);
      })

        wx.request({
          url: 'https://lawaaaa.online/api/warn/text2Warn',
          data:{
            content: contractContent
          },
          method: 'POST',
          header:{
            'content-type': 'application/json',
            'cookie': app.globalData.cookie
          },
          success: (res)=>{
            console.log("text2Warn:")
            console.log(res.data)

            this.setData({
              aiAnswer : {
                msgId: '2022',
                nickname: '劳动法律咨询AI',
                avatar: '/pages/images/GPT.png',
                message: res.data,
                type: 'text',
              }
            })

            var list = that.data.chatList;
            this.setData({
              chatList: list.concat(loadingMsg)
          }, () => {
              that.scrollToBottom();
              let timer2 = setTimeout(() => {
                clearTimeout(timer2);
                let chatList = list.concat(loadingMsg).concat(aiAnswer);              
                this.setData({
                  chatList: chatList
                });
                that.scrollToBottom();
              }, 5000);
          
          })
          }
        })

    }.bind(this), // 这里也需要逗号
    });
      

    },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    var list = this.data.imgIndexArr
    list.push(6)
    this.setData({
      imgIndexArr:list
    })
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