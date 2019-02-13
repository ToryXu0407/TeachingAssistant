<template>
  <div id="SocialDetail" class="SocialDetail">
    <div class="MainList fl" id="MainList">
      <div class="ListPage Page-Top bgWhite">
        <!--<div>答疑聊天室-在线人数{{onlineNum}}</div>-->
        <div>{{courseName}}</div>
        <router-link class="goBack cur"  :to="{ name: 'chatroom'}" wn_tj_click_href wn_tj_click_gameId wn_tj_click_excel="previous_page" wn_tj_click_id><img src="../../images/icon14.png"/>返回</router-link>
      </div>

      <!--<div class="ListPage Page-Bootom">-->
        <!--<pagination :cur.sync="cur" :all.sync="all" :isJump.sync="isJump"  @listen="monitor"></pagination>-->
        <!--<router-link class="goBack cur"  :to="{ name: 'social'}" wn_tj_click_href wn_tj_click_gameId wn_tj_click_excel="previous_page" wn_tj_click_id><img src="../../images/icon14.png"/>返回</router-link>-->
      <!--</div>-->

      <div v-show="isStarted">
        <div class="content" id="content">
          <ul v-for="(list,temp) in conversationList">
            <li>
              <div class="chats"v-if="list.isSelf===1">
                <div style="text-align: left;margin-top:20px">
                  <div>
                    <label>
                      我 {{list.date}}</label>
                  </div>
                  <div><div style="display:inline-block; font-size:20px;background:#B0B0B0;color:#000" v-html="list.message"></div></div>
                </div>
              </div>
              <div class="chats" v-else>
                <div style="text-align:right;margin-top: 20px;">
                  <div>
                    <label>
                      {{list.nickname}} {{list.date}}
                    </label>
                  </div>
                  <div><div style="display:inline-block; font-size:20px;background:#5CACEE;color:#FFF" v-html="list.message"></div></div>
                </div></div>
            </li>
          </ul>
        </div>
        <div v-show="canChat" class="LidtEditor bgWhite" id="LidtEditor">
          <div class="Editor">
            <div id="editorElem" style="text-align:left">
            </div>
            <span class="errts" id="postErrts" v-show="iSerrts"></span>
            <span class="PostHtml cur" @click="" id="PostHtml">发送</span>
          </div>
          <div class="loginMask" v-show="!isLogin">
            <p>游客不能回复哦~</p>
            <div class="loginMaskBox">
              <a href="javascript:;" @click="detailLogin()">登录</a>
              <a href="javascript:;" class="loginMaskBoxline"></a>
              <a href="javascript:void(0);" @click="register">注册</a>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="MainMoudle fr" id="MainMoudle">
      <PeoInfo ref="myPeoInfo"></PeoInfo>
      <!--<chatRoomUserList></chatRoomUserList>-->
      <!--<SocialIndexRecommend id ="SocialIndexRecommend"></SocialIndexRecommend>-->
      <span class="goTop cur" v-show="isGoTop" @click="goTop()"></span>
    </div>
    <!--<rulePop :isPopInfo="isPopInfo" :DetailLandlord="DetailLandlord" :cur="cur" @abc="delComment"></rulePop>-->
    <!--<div class="divmask a" v-show="isPostManageMask" @click="closeMask"></div>-->
    <LoginPop v-show="showDialog" @on-cancel="closeLoginPop" @on-suceess="closeLoginPop"></LoginPop>
  </div>

</template>

<script>
  import chatRoomIndexHeader from './chatRoomIndexHeader.vue'
  import chatRoomUserList from './chatRoomUserList.vue'
  import pagination from '../pagination.vue'
  import rulePop from '../rulePop.vue'
  import E from 'wangeditor'
  import PeoInfo from "../PeoInfo";
  import LoginPop from '../LoginPop'
  import BaseFooter from '../BaseFooter'


  export default {
    name: 'chatRoomDetail',
    components: {
      BaseFooter: BaseFooter,
      LoginPop: LoginPop,
      PeoInfo:PeoInfo,
      chatRoomIndexHeader: chatRoomIndexHeader,
      chatRoomUserList: chatRoomUserList,
      pagination: pagination,
      rulePop: rulePop
      // statistics: statistics
    },
    ready () {
      this.requestData()
      window.addEventListener('scroll', this.handleScroll)
    },
    destroyed () {
      window.removeEventListener('scroll', this.handleScroll)
    },
    data () {
      return {
        chatRoomId: this.$route.params.chatRoomId,
        showDialog:false,
        isStarted:false,
        canChat:true,
        courseName:'',
        timer:null,
        message:'',
        nickname:'',
        startTime:'',
        endTime:'',
        ws:'',
        onlineNum:'',
        userid:'',
        conversationList:[],
        all:'',
        isJump:'',
        iSerrts:'',
        isGoTop:'',
        isPopInfo: {
          isPopShow: false,
          isPopTsShow: 0,
          isMaskShow: false,
          commentId: ''
        },
        isLogin: false
      }
    },
    watch: {
      cur: {
        handler: function (val, oldval) {
          this.cur = val
          console.log(this.cur)
        }
      },
      editorContent: {
        handler: function (val, oldval) {
          console.log(val, 'val')
        }
      },
      conversationList:function(){
        this.$nextTick(function() {
          var div = document.getElementById('content');
          div.scrollTop = div.scrollHeight;
        });
      },
    },
    methods: {
      register(){
        this.$router.push("/register")
      },
      ShowLoginPop: function () {
        this.showDialog = true;
      },
      closeLoginPop: function () {
        if (document.getElementById('pdLogin').value === 'true') {
          this.isLogin = true;
        }
        this.showDialog = false;
        this.$refs.myPeoInfo.getLoggedInfo();
        this.$emit('refresh');
      },
      detailLogin: function () {
        if (document.getElementById('pdLogin').value === 'true') {
          this.isLogin = true;
        } else {
          this.ShowLoginPop()
        }
        // window.location.href = 'http://www.iplaystone.com/static/web/login.html?history=' + encodeURIComponent(window.location.href)
      },
      goBottom: function () {
        const windheight = document.documentElement.scrollHeight
        const gotoTop = function () {
          var currentPosition = document.documentElement.scrollTop || document.body.scrollTop
          currentPosition += 120
          if (currentPosition < windheight - 1600) {
            window.scrollTo(0, currentPosition)
          } else {
            window.scrollTo(0, windheight)
            clearInterval(timer)
            timer = null
          }
        }
        var timer = setInterval(gotoTop, 20)
      },
      goTop: function () {
        const gotoTop = function () {
          var currentPosition1 = document.documentElement.scrollTop || document.body.scrollTop
          currentPosition1 -= 80
          if (currentPosition1 > 0) {
            window.scrollTo(0, currentPosition1)
          } else {
            window.scrollTo(0, 0)
            clearInterval(timer)
            timer = null
          }
        }
        var timer = setInterval(gotoTop, 50)
      },
      onLandlord: function (flag) { // 只看老师
        if (this.DetailLandlord['onLandlord'] === true) {
          this.DetailLandlord['onLandlord'] = false // 全部
          this.isOnLandlord = 0 // 只看楼主按钮标识
          this.cur = 1  // 切换分页为第一页
          this.isOne = true // 将楼主信息显示
          this.LoadHtml(1, 0)
        } else { // 只看老师
          this.DetailLandlord['onLandlord'] = true
          this.isOnLandlord = 1
          this.cur = 1
          this.isOne = true
          this.LoadHtml(1, 1)
        }
      },
      getCurrentTime:function(){
        var vm = this;
        var start_date = new Date(this.startTime);
        var end_date = new Date(this.endTime);
        var now = new Date();
        console.log(now);
        if(now<start_date){
          //console.log("未开始")
          this.isStarted = false;
          this.canChat = true;
        }else if(now<end_date){
          // console.log("进行中")
          this.isStarted = true;
          this.canChat = true;
        }else if(now>end_date){
          console.log("已结束")
          //当聊天已结束,从数据库里获得所有数据并关闭定时器.
          this.isStarted = true;
          this.canChat = false;
          let params = new URLSearchParams();
          params.append('chatRoomId', this.$route.params.chatRoomId);
          this.$axios.post('/chat/getChats',params)
            .then((successResponse)=>{
              if (successResponse.data.code === 200) {
                vm.conversationList = successResponse.data.data;
                clearInterval(this.timer);
              }
            }).catch(failResponse => {})
        }
      }
    },
    computed: {
      indexs: function () {
        var left = 1
        var right = this.all
        var ar = []
        if (this.all >= 11) {
          if (this.curIndex > 5 && this.curIndex < this.all - 4) {
            left = this.curIndex - 5
            right = this.curIndex + 4
          } else {
            if (this.curIndex <= 5) {
              left = 1
              right = 10
            } else {
              right = this.all
              left = this.all - 9
            }
          }
        }
        while (left <= right) {
          ar.push(left)
          left++
        }
        if (ar[ar.length - 1] < this.all) {
          ar[ar.length - 1] = this.all
          ar[ar.length - 2] = 0
        }
        return ar
      }
    },
    created: function () {
      this.$axios.post('/getLoggedInfo')
        .then((successResponse)=>{
          if (successResponse.data.code === 200) {
            this.HdInfoData = successResponse.data.data
            this.isLogin = true
            this.nickname = successResponse.data.data.nickname;
            this.userid = successResponse.data.data.userid;
          }
        }).catch(failResponse => {})
      this.$axios.post('/chat/getEnteredUsers')
        .then((successResponse)=>{
          if (successResponse.data.code === 200) {
            this.onlineNum = successResponse.data.data
          }
        }).catch(failResponse => {})
      let params = new URLSearchParams();
      params.append('chatRoomId', this.$route.params.chatRoomId);
      this.$axios.post('/chat/setChatRoomId',params)
        .then((successResponse)=>{
          if (successResponse.data.code === 200) {
            this.startTime = successResponse.data.data.startTime;
            this.endTime = successResponse.data.data.endTime;
            this.courseName = successResponse.data.data.courseName;
            vm.getCurrentTime();
          }
        }).catch(failResponse => {})
      this.$emit('hidefooter');
      this.cur = this.$route.params.onPage
      window.addEventListener('scroll', this.handleScroll)
      var vm = this;
        if ("WebSocket" in window)
        {
          // 打开一个 web socket
          var ws = new WebSocket("ws://localhost:8000/websocket");
          this.ws = ws;
          ws.onopen = function()
          {
          };

          ws.onmessage = function (evt)
          {
            var data = JSON.parse(evt.data);
            var userid = data.from.userid;
            var isSelf = 0;
            var chatroomId = data.chatRoomId;
            console.log("onMessage:"+chatroomId);
            //如果是其他聊天室的消息则不接收。
            if(chatroomId===vm.$route.params.chatRoomId)
            {
              if(userid===vm.userid){
                isSelf=1;
              }
              vm.conversationList.push({message:data.text,isSelf:isSelf,date:data.date,nickname:data.from.nickname});
            }
            // alert("数据已接收..."+received_msg);
          };
          ws.onclose = function()
          {
            // 关闭 websocket
            // alert("连接已关闭...");
          };
        }

        else
        {
          // 浏览器不支持 WebSocket
          alert("您的浏览器不支持 WebSocket!");
        }
      // }
    },

    mounted: function () {
      var vm =this;
        this.timer = setInterval( () => {
          vm.getCurrentTime();
        }, 1000);
      const s = document.createElement('script')
      s.type = 'text/javascript'
      s.src = '//static.snail.com/js/stone/v2/statistics_ty_v2.source.js'
      document.body.appendChild(s)
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
        _this.editorContent = editor.txt.html()
        if(_this.editorContent=='<p><br></p>'){
          alert("内容不能为空！");
        }else
        {
          _this.ws.send(_this.editorContent);
        }
        // console.log('1')
      })
    },
    destroyed: function () {
      clearInterval(this.timer);
      this.ws.close();
    }
  }
</script>

<style>
  @import '../../sass/stylesheets/SocialDetail.css'
</style>
