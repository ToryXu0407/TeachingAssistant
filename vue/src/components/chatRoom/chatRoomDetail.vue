<template>
  <div>
    <div >
      <div style="float: left;">
        <h2>聊天室</h2>
        <div
          style="width: 1000px; height: 400px; overflow: scroll; border: 1px solid;"
          id="output"></div>
        <br/>
      </div>
      <div style="float: left;">
        <h2>在线用户</h2>
        <div
          style="width: 200px; height: 400px; overflow: scroll; border: 1px solid;"
          id="onlineUsers">
          <li v-for="(user, i) in users" :data-listId="user" :name="user">
              user
          </li>
        </div>
      </div>

    </div>

    <br/>
    <div class="LidtEditor bgWhite" id="LidtEditor">
      <div class="Editor">
        <div id="editorElem" style="text-align:left">
        </div>
        <span class="errts" id="postErrts" v-show="iSerrts"></span>
        <span class="PostHtml cur" @click="" id="PostHtml">发表</span>
      </div>
    </div>
  </div>
</template>

<script>
  import E from 'wangeditor'
    export default {
      name: "chatRoomDetail",
      data() {
        return {
          users: '',
          iSerrts: false,
          editorContent: '',
        }
      },
      created: function () {
        function load() {
          dingyue();
        }

        var goeasy = new GoEasy({
          appkey: 'BC-554a3c0772034f42bf753a901bb0b5b7',
          userId: 'user',
          userData: '123456'
        });

        function chaxun() {
          goeasy.hereNow(
            {
              channels: ['letschat'], //要查询的channel 列表
              includeUsers: true, //可选项，是否返回用户列表，默认false
              distinct: true //可选项，是否过滤相同的userId，默认false
            },
            function (response) {
              console.log("response:" + response);
              var data = response.content.channels.letschat.users;
              console.log("data:" + data);
              //查询到上线用户信息并显示出来
              getOnlineUser(data);
            });
        }

        function quxiao() {
          goeasy.unsubscribePresence({
            channel: 'letschat',
            //取消成功
            onSuccess: function () {
              alert("取消订阅presence 成功.")
            },
            //取消失败
            onFailed: function (error) {
              alert("取消订阅presence 失败.");
            }
          });
        }

        function dingyue() {
          goeasy.subscribePresence({
            channel: 'letschat',
            //订阅Presence 成功
            onSuccess: function () {
              chaxun();
            },
//订阅Presence 失败
            onFailed: function (error) {
              alert('Presence subscription failure.');
            },
//当有客户端状态发生变化
            onPresence: function (presenceEvents) {
              console.log("presenceEvents:" + presenceEvents);
              var data = JSON.stringify(presenceEvents);
              chaxun();
            }
          });
        }

        goeasy.subscribe({
          channel: 'letschat',
          onFailed: function (error) {
            alert("failed motherfucker!")
          },
          onSuccess: function () {
          },
          onMessage: function (message) {
            //收到消息的第一行，打出日志，以这个时间作为检查的标准
            //如果不是本人发的，则显示到屏幕上
            var username = message.content.split("::", 2)[1];
            if (username != "${username}") {
              writeToScreen(message.content);
            }
          }
        });

        function publishMessage() {
          editor.sync();
          var publishMessage = $("#content").val();
          goeasy.publish({
            channel: 'letschat',
            message: publishMessage + '::${username}',
            onFailed: function (error) {
              alert(error.code + " : " + error.content);
              writeToScreen('<span style="color:red;">系统出错啦</span>' + msg.data);
            },
            onSuccess: function () {
              writeToScreen(publishMessage + '::${username}');
            }
          });
        }

        // //聊天信息写入窗口中
        // function writeToScreen(message) {
        //   var pre = document.createElement("p");
        //   pre.style.wordWrap = "break-word";
        //   var username = message.split("::", 2)[1];
        //   var content = message.split("::", 2)[0];
        //   //如果是本人发的，则放到对话框的邮编
        //   if (username == "${username}") {
        //     pre.style.setProperty('text-align', 'right');
        //     pre.innerHTML = content + ":" + username;
        //   } else
        //     pre.innerHTML = username + ":" + content;
        //   output.appendChild(pre);
        //   editor.html("");
        // }

        function getOnlineUser(message) {
          this.users = message;
        }

    },
      mounted:function () {
        var editor = new E('#editorElem')
        editor.customConfig.menus = [
          // 'head',  // 标题
          'bold',  // 粗体
          'italic',  // 斜体
          'underline',  // 下划线
          // 'strikeThrough',  // 删除线
          // 'foreColor',  // 文字颜色
          // 'backColor',  // 背景颜色
          'link',  // 插入链接
          // 'list',  // 列表
          // 'justify',  // 对齐方式
          // 'quote',  // 引用
          'emoticon',  // 表情
          'image' // 插入图片
          // 'table',  // 表格
          // 'video',  // 插入视频
          // 'code',  // 插入代码
          // 'undo',  // 撤销
          // 'redo'  // 重复
        ]
        // editor.customConfig.uploadImgShowBase64 = true   // 使用 base64 保存图片
        editor.customConfig.pasteFilterStyle = true
        editor.customConfig.uploadImgServer = '//moment.snail.com/api/v1/upload/image'  // 上传图片到服务器
        editor.customConfig.uploadImgMaxSize = 2 * 1024 * 1024
        editor.customConfig.uploadImgMaxLength = 50
        editor.customConfig.withCredentials = true
        editor.customConfig.uploadImgParams = {
          from: 'comment'   // 属性值会自动进行 encode ，此处无需 encode
        }
        editor.customConfig.uploadFileName = 'file'
        editor.customConfig.uploadImgHeaders = {
          'Accept': 'text/x-json'
        }
        editor.customConfig.customAlert = function (info) {
          // info 是需要提示的内容
          // alert('info')
          document.getElementById('postErrts').style.display = 'inline-block'
          document.getElementById('postErrts').innerHTML = '图片过大，请上传小于2M的图片'
          // console.log(info)
        }
        editor.customConfig.uploadImgHooks = {
          fail: function (xhr, editor, result) {
            // 图片上传并返回结果，但图片插入错误时触发
            // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
            document.getElementById('postErrts').style.display = 'inline-block'
            document.getElementById('postErrts').innerHTML = '图片插入错误，请重新选择图片'
          },
          error: function (xhr, editor) {
            // 图片上传出错时触发
            // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象
            document.getElementById('postErrts').style.display = 'inline-block'
            document.getElementById('postErrts').innerHTML = '图片上传错误，请重新选择图片'
          },
          customInsert: function (insertImg, result, editor) {
            // 图片上传并返回结果，自定义插入图片的事件（而不是编辑器自动插入图片！！！）
            // insertImg 是插入图片的函数，editor 是编辑器对象，result 是服务器端返回的结果
            // 举例：假如上传图片成功后，服务器端返回的是 {url:'....'} 这种格式，即可这样插入图片：
            if (result.code === 200) {
              document.getElementById('postErrts').style.display = 'none'
              var url = result.url
              insertImg(url)
            } else {
              document.getElementById('postErrts').style.display = 'inline-block'
              document.getElementById('postErrts').innerHTML = result.message
            }
            // console.log(result)
            // result 必须是一个 JSON 格式字符串！！！否则报错
          }
        }
        editor.customConfig.linkImgCallback = function (url) {
          // console.log(url) // url 即插入图片的地址
        }
        editor.create()
        // editor.txt.html('<p style="font-size:12px;color:#aaa;">请输入内容(～￣▽￣)～</p>')
        var _this = this
        document.getElementById('PostHtml').addEventListener('click', function () {
          this.editorContent = editor.txt.html()
          console.log(this.editorContent)
          _this.getContent(editor.txt.html())
          // console.log('1')
        })
      },

      }


</script>

  <style>
</style>
