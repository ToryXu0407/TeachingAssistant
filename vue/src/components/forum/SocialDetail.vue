<template>
    <div id="SocialDetail" class="SocialDetail">
      <div class="MainList fl" id="MainList">
        <div class="ListPage Page-Top bgWhite">
          <pagination :cur.sync="cur" :all.sync="all" :isJump.sync="isJump"  @listen="monitor"></pagination>
          <router-link class="goBack cur"  :to="{ name: 'social'}" wn_tj_click_href wn_tj_click_gameId wn_tj_click_excel="previous_page" wn_tj_click_id><img src="../../images/icon14.png"/>返回讨论社</router-link>
        </div>
        <div class="ListCont">
          <div class="Jtitle bgWhite" id="Jtitle">
            <h5 class="textOverFlow" :data-tzId="DetailLandlord.id" :title="DetailLandlord.label">{{DetailLandlord.label}}</h5>
            <!--<span :class="['icon-gf',{'none':DetailLandlord.is_official == 0}]" title="官方" v-if="DetailLandlord.is_official !== 0">官方</span>-->
            <!--<span :class="['icon-top',{'none':DetailLandlord.is_top == 0}]" v-if="DetailLandlord.is_top !== 0" title="置顶">置顶</span>-->
            <span class="icon-good" v-for="tags in DetailLandlord.public_tags" :title="tags.name" :class="tags.mark">{{tags.text}}</span>
            <div class="Jtitle-Moudle fr">
              <span @click="onLandlord(1)" :class="{onLandlord:DetailLandlord.onLandlord === true}">只看楼主</span>
              <span @click="goBottom()"><img src="../../images/icon6.png"/>回复</span>
            </div>
          </div>
          <ul class="ListContUl">
            <li :class="['clearfix bgWhite ListContUlLi',{'isSelf':DetailLandlord.is_self === true}]" v-show="isOne" :data-id="DetailLandlord.id">
              <img src="../../images/lz.png" class="user-lz"/>
              <div class="Jitems">
                <div class="JitemsLeft fl">
                  <div class="J-UserInfo">
                    <div class="J-UserInfo-Pic"><img :src="DetailLandlord.headImage" :alt="DetailLandlord.nickname"/></div>
                    <span class="J-UserInfo-Name textOverFlow" :title="DetailLandlord.nickname">{{DetailLandlord.nickname}}</span>
                    <span class="J-UserInfo-adminCall" v-show="DetailLandlord.is_post_by_admin">管理员</span>
                  </div>
                </div>
                <div class="JitemsRight fr">
                  <div class="J-TextCont" v-html="DetailLandlord.content"></div>
                  <div :class="['J-ClickUp',{'J-On' : DetailLandlord.isVoted === true}]" @click="ListVote(DetailLandlord.id)">
                    <p></p>
                    <p>{{DetailLandlord.voteCount}}</p>
                  </div>
                  <div class="J-Modle clearfix">
                    <div class="J-Modle-Hd">
                      <div class="Hd-Floor fl"><span>楼主</span>{{DetailLandlord.createTime}}</div>
                      <div class="Hd-Moudle fr">
                        <ol>
                          <li @click="goBottom()"><span class="Hd-Moudle-Reply">回复</span></li>
                        </ol>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </li>
            <li :class="['clearfix bgWhite ListContUlLi',{'isSelf':list.is_self === true}]" v-for="(list , temp) in DetailList" :data-listId="list.id" v-if="true" :name="list.id">
              <a :name="list.id"></a>
              <img src="../../images/lz.png" class="user-lz" v-show="list.is_owner"/>
              <div class="Jitems">
                <div class="JitemsLeft fl">
                  <div class="J-UserInfo">
                    <div class="J-UserInfo-Pic"><img :src="list.headImage" :alt="list.nickname"/></div>
                    <span class="J-UserInfo-Name textOverFlow" :title="list.nickname" :data-userid="list.userId">{{list.nickname}}</span>
                    <span class="J-UserInfo-adminCall" v-show="list.is_post_by_admin">管理员</span>
                  </div>
                </div>
                <div class="JitemsRight fr">
                  <div class="J-TextCont" v-html="list.content"></div>
                  <div class="J-Modle clearfix">
                    <div class="J-Modle-Hd">
                      <div class="Hd-Floor fl"><span>{{list.position}}楼</span>{{list.createTime}}</div>
                      <div class="Hd-Moudle fr">
                        <ol>
                          <li v-show="isAdmin==='Y'" @click="delPost(list.id)"><span class="Hd-Moudle-Del">删除</span></li>
                          <!--<li v-show="!list.delete_private" v-if="false"><span class="Hd-Moudle-Report">举报</span></li>-->
                          <li>
                            <span class="Hd-Moudle-Reply" v-show="list.isReplay" @click="ShowMoudleList(temp, list.replyNum, list.id)">回复<b v-if="list.replyNum > 0">（{{list.replyNum}}）</b></span>
                            <span class="Hd-Moudle-Reply" v-show="!list.isReplay" @click="HideMoudleList(temp)">收起回复<b v-if="list.replyNum > 0">（{{list.replyNum}}）</b></span>
                          </li>
                        </ol>
                      </div>
                    </div>
                    <div class="J-Moudle-List" v-show="!list.isReplay">
                      <div class="J-Moudle-List-Cont clearfix">
                        <ul class="J-Moudle-List-Cont-Ul"  v-if="list.replyNum > 0">
                          <li class="J-Moudle-List-Cont-Li" v-for="(Mlist, Mtemp) in list.replies">
                            <div class="Jmoude-items clearfix">
                              <div class="Jmoude-items-Pic fl"><img :src="Mlist.headImage" :alt="Mlist.nickname"/></div>
                              <div class="Jmoude-items-Cont fr">
                                <div class="Jmoudle-items-h5">
                                  <span><a href="javascript:;">{{Mlist.nickname}}：</a><b style="float:left">{{ Mlist.toUserId !== Mlist.userId ? '回复' : '' }} </b><a href="javascript:;"  class="twouser" v-if="Mlist.toUserId !== Mlist.userId">{{Mlist.toUserNickName}}：</a>{{Mlist.content}}</span>
                                </div>
                                <div class="Jmoudle-items-info">
                                  <span class="fl">{{Mlist.createTime}}</span>
                                  <span class="fr cur"  @click="JMoudleReply(list.userId, Mlist.userId, Mlist.nickname,temp, $event, list.id)">回复</span>
                                </div>
                              </div>
                            </div>
                          </li>
                        </ul>
                        <div class="J-Moudle-List-Cont-Jet">
                          <span class="J-Moudle-List-More fl cur" v-show="list.replyNum > 5 && list.more === true" @click="showMore(list.id, 1, 10, temp)">查看更多</span>
                          <div class="J-Moudle-Page fl" v-show="list.replyNum > 5 && list.more === false">
                            <ul>
                              <li><a :class="setButtonClass(0,temp)" @click="prvePage(list.current,list.id,temp)"><img src="../../images/prev.png" alt="<"/></a></li>
                              <li v-for="(flag,i) in list.allpage"  :class="[{ active: list.current === flag },{point:flag < 1}]" :data-flag="flag" :data-cur="list.current">
                                <a @click="btnClick(flag,temp,list.id)">{{ flag < 1 ? "..." : flag }}</a>
                              </li>
                              <li class="next"><a :class="setButtonClass(1,temp)" @click="nextPage(list.current,list.all,list.id,temp)"><img src="../../images/next.png" alt=">"/></a></li>
                            </ul>
                          </div>
                          <div class="J-Moudle-Mebtn fr cur" :data-listId="list.id"  :data-listUserId="list.user_id" @click="JReply(list.id, temp)">我也说一句</div>
                        </div>
                      </div>
                      <div class="J-Moudle-List-Input" v-show="list.isMoudleListInput">
                        <div class="J-Moudle-List-Input-Box">
                          <input type="text" :placeholder="list.nickname" :id="'J'+list.id" maxLength="150" autofocus="autofocus"/>
                        </div>
                        <div class="J-Moudle-List-Input-Set">
                          <div class="J-M-l-I-S-Icon cur fl" v-if="false">
                            <img src="../../images/icon13.png"/>
                            <div class="Icon-List none"></div>
                          </div>
                          <span class="J-M-L-Btn cur fr" @click="postComment(list.id, temp)">发表</span>
                        </div>
                        <!--Login Mask-->
                        <div class="loginMask" v-if="!isLogin">
                          <div class="loginMaskBox">
                            <a href="javascript:;" @click="detailLogin()">登录</a>
                            <a href="javascript:;" class="loginMaskBoxline"></a>
                            <a href="http://www.iplaystone.com/static/web/register.html">注册</a>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </li>
          </ul>
        </div>
        <div class="ListPage Page-Bootom">
          <pagination :cur.sync="cur" :all.sync="all" :isJump.sync="isJump"  @listen="monitor"></pagination>
          <router-link class="goBack cur"  :to="{ name: 'social'}" wn_tj_click_href wn_tj_click_gameId wn_tj_click_excel="previous_page" wn_tj_click_id><img src="../../images/icon14.png"/>返回讨论社</router-link>
        </div>
        <div class="LidtEditor bgWhite" id="LidtEditor">
          <div class="Editor">
            <div id="editorElem" style="text-align:left">
            </div>
            <span class="errts" id="postErrts" v-show="iSerrts"></span>
            <span class="PostHtml cur" @click="" id="PostHtml">发表</span>
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
      <div class="MainMoudle fr" id="MainMoudle">
        <PeoInfo ref="myPeoInfo"></PeoInfo>
        <SocialIndexHot></SocialIndexHot>
        <!--<SocialIndexRecommend id ="SocialIndexRecommend"></SocialIndexRecommend>-->
        <span class="goTop cur" v-show="isGoTop" @click="goTop()"></span>
      </div>
      <!--<rulePop :isPopInfo="isPopInfo" :DetailLandlord="DetailLandlord" :cur="cur" @abc="delComment"></rulePop>-->
      <div class="divmask a" v-show="isPostManageMask" @click="closeMask"></div>
      <LoginPop v-show="showDialog" @on-cancel="closeLoginPop" @on-suceess="closeLoginPop"></LoginPop>
    </div>

</template>

<script>
  import SocialIndexHeader from './SocialIndexHeader.vue'
  import SocialIndexAdmin from '../SocialIndexAdmin.vue'
  import SocialIndexHot from './SocialIndexHot.vue'
  import SocialIndexDetail from './SocialIndexDetail.vue'
  import pagination from '../pagination.vue'
  import rulePop from '../rulePop.vue'
  import E from 'wangeditor'
  import PeoInfo from "../PeoInfo";
  import LoginPop from '../LoginPop'
  import BaseFooter from '../BaseFooter'


  export default {
    name: 'SocialDetail',
    components: {
      BaseFooter: BaseFooter,
      LoginPop: LoginPop,
      PeoInfo:PeoInfo,
      SocialIndexHeader: SocialIndexHeader,
      SocialIndexAdmin: SocialIndexAdmin,
      SocialIndexHot: SocialIndexHot,
      SocialIndexDetail: SocialIndexDetail,
      pagination: pagination,
      rulePop: rulePop
      // statistics: statistics
    },
    watch: {
      DetailList: {
        handler: function (val, oldval) {
        // console.log(val)
        }
      },
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
      }
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
        isAdmin:'N',
        postId: '',
        showDialog:false,
        articleId:'',
        HdInfoData: {},
        DetailLandlord: [],
        DetailList: [],
        isSiH: false,
        cur: '',
        all: 1,
        Pagesize: 10,
        isJump: true,
        listId: '',
        userId: '',
        content: '',
        headImage:'',
        nickname: '',
        isOne: true,
        isOnLandlord: 0,
        editorContent: '',
        scrolled: '',
        isGoTop: false,
        isPostManage: false,
        isPostManageMask: false,
        iSerrts: false,
        isPopInfo: {
          isPopShow: false,
          isPopTsShow: 0,
          isMaskShow: false,
          commentId: ''
        },
        isLogin: false
      }
    },
    methods: {
      register(){
        this.$router.push("/register")
      },
      delPost(id){
        var vm = this;
        let params = new URLSearchParams();
        params.append('id',id);
        this.$axios.post('/post/delPost', params)
          .then(function (res) {
            if(res.data.code===200){
              alert("删除成功！");
              vm.getPost();
            }
          })
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
      closeMask: function () {
        this.isPostManage = false
        this.isPostManageMask = false
      },
      showPostManage: function () { // 显示管理列表
        if (this.isPostManage === false) {
          this.isPostManage = true
          this.isPostManageMask = true
        } else {
          this.isPostManage = false
          this.isPostManageMask = false
        }
      },
      handleScroll: function () {
        const windheight = window.innerHeight
        this.scrolled = window.scrollY
        // console.log(this.scrolled, windheight)
        if (this.scrolled > windheight - windheight / 2) {
          document.getElementById('Jtitle').style.position = 'fixed'
        } else {
          document.getElementById('Jtitle').style.position = 'static'
        }
        if (this.scrolled > windheight + 250) {
          this.isShow = true
          this.isGoTop = true
          // document.getElementById('SocialIndexRecommend').classList.add('fixed2')
          document.getElementById('MainMoudle').children[0].classList.add('hfixed')
        } else if (this.scrolled < 800) {
          document.getElementById('MainMoudle').children[0].classList.remove('hfixed')
          // document.getElementById('SocialIndexRecommend').classList.remove('fixed2')
          this.isShow = false
          this.isGoTop = false
        }
        if (document.documentElement.clientHeight < 700) {
          this.isGoTop = false
        }
      },
      delComment: function (commentId) { // 删除评论
        this.monitor(this.cur)
      },
      getContent: function (text) { // 发送评论
        var _this = this
        document.getElementsByClassName('w-e-text')[0].blur()
        if (text !== '') {
          // setTimeout(function () {
          //   document.getElementsByClassName('w-e-text')[0].focus()
          //   _this.postHtml(_this.postId, _this.editorContent)
          // }, 10)
          _this.postHtml(_this.postId, text)
          // console.log(_this.editor.txt.html())
        }
      },
      getPost: function(){
        let params = new URLSearchParams();
        const vm = this;
        params.append('articleId', this.$route.params.articleId);
        params.append('page', this.cur);
        this.$axios.post('/post/getPostsByArticleId',params)
          .then(function (res) {
            vm.DetailList = res.data.data
            vm.all = res.data.totalPage
            if (res.data.totalPage === 0) {
              vm.all = 1
            }
            if (vm.cur > 1) {
              vm.isOne = false // 第二页 楼主不显示
            } else {
              vm.isOne = true
            }
          })
      },
      postHtml: function (postId, editorContent) {
         console.log(editorContent)
        var vm = this
        let params = new URLSearchParams();
        params.append('articleId', this.$route.params.articleId);
        params.append("content",editorContent);
        this.$axios.post('/post/updatePost',params)
          .then((res)=>{
            if (res.data.code === 200) {
              this.monitor(this.all)
                  document.getElementById('postErrts').style.display = 'none'
                  document.getElementsByClassName('w-e-text')[0].innerHTML = '<p><br/></p>'
                  this.editorContent = 'no'
                  this.goBottom()
                  this.getPost();
            } else {
              alert("回帖失败!");
            }
          })
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
      onLandlord: function (flag) { // 只看楼主
        if (this.DetailLandlord['onLandlord'] === true) {
          this.DetailLandlord['onLandlord'] = false // 全部
          this.isOnLandlord = 0 // 只看楼主按钮标识
          this.cur = 1  // 切换分页为第一页
          this.isOne = true // 将楼主信息显示
          this.LoadHtml(1, 0)
        } else { // 只看楼主
          this.DetailLandlord['onLandlord'] = true
          this.isOnLandlord = 1
          this.cur = 1
          this.isOne = true
          this.LoadHtml(1, 1)
        }
      },
      monitor: function (data) { // 分页
        this.cur = data
        this.LoadHtml(data, this.isOnLandlord)
        if (data > 1) {
          this.isOne = false // 第二页 楼主不显示
        } else {
          this.isOne = true
        }
      },
      ShowMoudleList: function (temp, num, commentUserId) { // 显示评论列表
        this.DetailList[temp]['isMoudleListInput'] = true
        this.DetailList[temp]['isReplay'] = false
        this.DetailList[temp]['UserNickName'] = ''
        this.DetailList[temp]['listId'] = commentUserId
        //修改值来完成重新加载组件的功能
        let t = this.DetailList[temp]['userId']
        this.DetailList[temp]['userId'] = '1'
        this.DetailList[temp]['userId'] = t
      },
      HideMoudleList: function (temp) { // 关闭评论列表
        this.DetailList[temp]['isReplay'] = true
        this.DetailList[temp]['UserNickName'] = ''
        this.DetailList[temp]['isMoudleListInput'] = false
        let t = this.DetailList[temp]['userId']
        this.DetailList[temp]['userId'] = '1'
        this.DetailList[temp]['userId'] = t
      },
      JMoudleReply: function (userId, commentUserId, userNickName,temp, event, id) { // 回复
        if (document.getElementById('pdLogin').value === 'true') {
          this.DetailList[temp]['isMoudleListInput'] = true
          this.DetailList[temp]['listId'] = id
          this.DetailList[temp]['toUserId'] = commentUserId
          //需要修改控件值来进行控件的重新加载
          var t =  this.DetailList[temp]['userId']
          this.DetailList[temp]['userId'] = 1
          this.DetailList[temp]['userId'] = t
          setTimeout(function () {
            document.getElementById('J' + id).focus()
          }, 200)
        } else {
          this.ShowLoginPop()
        }
      },
      JReply: function (commentUserId, temp) { // 我也说一句
        this.DetailList[temp]['isMoudleListInput'] = true
        this.DetailList[temp]['UserNickName'] = ''
        this.DetailList[temp]['listId'] = commentUserId
        var t =  this.DetailList[temp]['userId']
        this.DetailList[temp]['userId'] = 1
        this.DetailList[temp]['userId'] = t
        setTimeout(function () {
          document.getElementById('J' + commentUserId).focus()
        }, 200)
      },
      ListVote: function (articleId) { // 楼主贴投票
         if (document.getElementById('pdLogin').value === 'true') {
           var vm = this
           var params = new URLSearchParams();
           params.append('articleId', articleId);
           this.$axios.post('/article/vote',params)
           .then(function (res) {
             if(res.data.code === 200)
               vm.RefreshHdInfo();
           })
         }else{
           this.ShowLoginPop()
         }
      },
      LoadHtml: function (data, flag) { // 刷新HTML 只看楼主
        var vm = this
        var params = new URLSearchParams();
        params.append('articleId', this.$route.params.articleId);
        params.append('page', data);
        params.append('only_owner',flag)
        this.$axios.post('/post/getPostsByArticleId',params)
          .then(function (res) {
            vm.DetailList = res.data.data
            vm.all = res.data.totalPage
            for (var i in res.data.data) {
              res.data.data[i]['UserNickName'] = ''
              res.data.data[i]['isMoudleListInput'] = false
              res.data.data[i]['more'] = true
              if (res.data.data[i].replyNum > 0) {
                res.data.data[i]['isReplay'] = false
              } else {
                res.data.data[i]['isReplay'] = true
              }
              if (res.data.data[i].replies !== undefined) {
                if (res.data.data[i].replies.length > 0) {
                  res.data.data[i].replies = res.data.data[i].replies.splice(0, 5)
                }
              }
            }
            if (res.data.totalPage === 0) {
              vm.all = 1
            }
            if (vm.cur > 1) {
              vm.isOne = false // 第二页 楼主不显示
            } else {
              vm.isOne = true
            }
          })
      },
      RefreshHdInfo: function () { // 刷新楼主投票
        var vm = this
        let params = new URLSearchParams();
        params.append('id', this.$route.params.articleId);
        this.$axios.post('/article/getArticleById',params)
          .then((res)=>{
            if (res.data.code === 200) {
              this.DetailLandlord = res.data.data
              this.DetailLandlord['onLandlord'] = false
            } else if (res.data.code === 404) {

            }
          })
      },
      showMore: function (commentid, page, pagesize, temp) { // 显示评论列表
        var vm = this
        let params = new URLSearchParams();
        params.append('postId', commentid);
        params.append('page', page);
        params.append('pagesize', pagesize);
        this.$axios.post('/post/getPostsByParentPostId',params)
        .then(function (res) {
          if (res.data.code === 200) {
              vm.DetailList[temp]['replies'] = res.data.data
              vm.DetailList[temp]['current'] = page
              vm.DetailList[temp]['more'] = false
              vm.DetailList[temp]['allpage'] = res.data.totalPage
          }
        })
      },
      // 页码点击事件
      btnClick: function (current, index, postId) {
        if (current < 1) return
        if (current !== this.DetailList[index].current) {
          this.DetailList[index].current = current
          this.showMore(postId, current, 10, index)
          // var top = 0
          // top = document.documentElement.scrollTop + event.currentTarget.getBoundingClientRect().top - 430
          // window.scrollTo(0, top)
        }
      },
      // 下一页
      nextPage: function (current, allpage, postId, index) {
        if (current >= allpage) return
        this.btnClick(current + 1, index, postId)
        // var top = 0
        // top = document.documentElement.scrollTop + event.currentTarget.getBoundingClientRect().top - 430
        // window.scrollTo(0, top)
      },
      // 上一页
      prvePage: function (current, postId, index) {
        if (current <= 1) return
        this.btnClick(current - 1, index, postId)
      },
      // 设置按钮禁用样式
      setButtonClass: function (isNextButton, index) {
        if (isNextButton) {
          return this.DetailList[index].current >= this.DetailList[index].allpage ? 'page-button-disabled' : ''
        } else {
          return this.DetailList[index].current <= 1 ? 'page-button-disabled' : ''
        }
      },
      //其实id不传也没事，可以从temp中取
      postComment: function (id, temp) { // 发表评论
         var vm = this
         var content = document.getElementById('J' + id).value
         if (!content) return
        let params = new URLSearchParams();
        params.append('articleId', this.$route.params.articleId);
        params.append("parentPostId",id)
        params.append("toUserId",this.DetailList[temp]['toUserId'])
        params.append("userId",this.DetailList[temp]['userId']);
        params.append("content",content);
        this.$axios.post('/post/updatePost',params)
          .then((res)=>{
            if (res.data.code === 200) {
              //this.monitor(this.all)
              document.getElementById('J' + id).value = ''
              this.editorContent = 'no'
              this.getPost();
            } else {
              alert("回帖失败!");
            }
          })
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
      this.$emit('hidefooter');
      const vm = this
      this.cur = this.$route.params.onPage
      window.addEventListener('scroll', this.handleScroll)
      let params = new URLSearchParams();
      params.append('id', this.$route.params.articleId);
      this.$axios.post('/article/getArticleById',params)
        .then((res)=>{
        if (res.data.code === 200) {
          vm.DetailLandlord = res.data.data
          vm.DetailLandlord['onLandlord'] = false
        }
      })
      this.$axios.post('/getLoggedInfo')
        .then((successResponse)=>{
          if (successResponse.data.code === 200) {
            this.HdInfoData = successResponse.data.data
            this.isLogin = true
          }
        }).catch(failResponse => {})
      params = new URLSearchParams();
      params.append('articleId', this.$route.params.articleId);
      params.append('page', this.cur);
      this.$axios.post('/post/getPostsByArticleId',params)
        .then(function (res) {
          vm.DetailList = res.data.data
          for (var i in res.data.data) {
            res.data.data[i]['UserNickName'] = ''
            res.data.data[i]['isMoudleListInput'] = false
            res.data.data[i]['more'] = true
            if (res.data.data[i].replyNum > 0) {
              res.data.data[i]['isReplay'] = false
            } else {
              res.data.data[i]['isReplay'] = true
            }
            if (res.data.data[i].replies !== undefined) {
              if (res.data.data[i].replies.length > 0) {
                res.data.data[i].replies = res.data.data[i].replies.splice(0, 5)
              }
            }
          }
        vm.all = res.data.totalPage
        if (res.data.totalPage === 0) {
          vm.all = 1
        }
        if (vm.cur > 1) {
          vm.isOne = false // 第二页 楼主不显示
        } else {
          vm.isOne = true
        }
      })
    },
    mounted: function () {
      const s = document.createElement('script')
      s.type = 'text/javascript'
      s.src = '//static.snail.com/js/stone/v2/statistics_ty_v2.source.js'
      document.body.appendChild(s)
      var editor = new E('#editorElem')
      // editor.customConfig.onchange = (html) => {
      //   this.editorContent = html
      // }
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
        _this.getContent(editor.txt.html())
        // console.log('1')
      })
        var vm = this
        this.$axios.post('/getLoggedInfo')
          .then((successResponse) => {
            if (successResponse.data.code === 200) {
              var user = successResponse.data.data;
              document.getElementById('pdLogin').value = 'true'
              if (user.type === 0) {
                document.getElementById('isTeacher').value = 'Y'
                document.getElementById('isAdmin').value = 'Y'
              } else if (user.type === 1) {
                document.getElementById('isTeacher').value = 'Y'
                document.getElementById('isAdmin').value = 'N'
              } else {
                document.getElementById('isTeacher').value = 'N'
                document.getElementById('isAdmin').value = 'N'
              }
            } else {
              document.getElementById('pdLogin').value = 'false'
              document.getElementById('isTeacher').value = 'N'
              document.getElementById('isAdmin').value = 'N'
            }
            this.isAdmin =  document.getElementById('isAdmin').value;
          }).catch(failResponse => {
        })
    }
  }
</script>

<style>
  @import '../../sass/stylesheets/SocialDetail.css'
</style>
