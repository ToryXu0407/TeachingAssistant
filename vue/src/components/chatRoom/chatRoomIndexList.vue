<template>
  <div class="SocialIndexList" id="SocialIndexList">
      <div class="IndexListType clearfix" id="IndexListType">
         <div class="TypeTag fl">
             <ul>
                 <li>
                    <a href="javascript:;"  :class="{current:current == 0}" title="进行中"  @click="GoTagsBtn('', 0)" >进行中</a>
                 </li>
                 <li v-for="(allTags,index) in allTags" >
                    <a href="javascript:;" :title="allTags.name" :data-tagid="allTags.id" @click="GoTagsBtn(allTags.id, index+1)" :class="{current:index+1 == current}" wn_tj_click_href :wn_tj_click_excel="allTags.name" wn_tj_click_id>{{allTags.name}}</a>
                 </li>
               <li>
                 <el-input v-model="input" style="width:100px;margin-left:300px;margin-top: -10px;" placeholder="请输入内容"></el-input>
               </li>
               <li>
                 <el-button icon="el-icon-search" circle @click="search" style="margin-left:330px;margin-top: -10px;"></el-button>
               </li>
             </ul>
        </div>
        <!--<div class="scrrenTag fr">-->
           <!--<h6 class="scrrenTagInput" @click="drownInput"><span>{{ordername}}</span><i :class="['rotatez',{'roate':drown === false}]"></i></h6>-->
           <!--<ul class="InputSelect" v-if="drown">-->
              <!--<li data-order="hot" @click="drownSelect('hot','热门排序')" wn_tj_click_href wn_tj_click_gameId wn_tj_click_excel="hot_ordering" wn_tj_click_id>热门排序</li>-->
              <!--<li data-order="time" @click="drownSelect('time','时间排序')" wn_tj_click_href wn_tj_click_gameId wn_tj_click_excel="time_ordering" wn_tj_click_id>时间排序</li>-->
           <!--</ul>-->
         <!--</div>-->
      </div>
      <div class="IndexListCont">
        <ul>
          <li v-for="(list, i) in list" :data-listId="list.id" :name="list.id">
              <div class="Jitems">
                <div class="Jitems-Title">
                  <router-link :to="{ name: 'chatRoomDetail', params: {'chatRoomId':list.id }}" :title="list.courseName" :listId="list.id">{{list.courseName}}</router-link>
                </div>
                <i   v-if="isAdmin==='Y'" @click="del(list.id)" style="font-size:20px;float: right;padding: 3px 0" class="el-icon-delete"></i>
                <div class="Jitems-Info">
                    <div class="JuserInfo fl">
                        <a href="javascript:;" class="JuserInfo-people default" :title="list.nickname">
                            <img :src="list.headImage"/>
                            {{list.nickname}}
                        </a>
                        <span class="JuserInfo-time" :title="list.createTime">{{list.startTime}} To {{list.endTime}}</span>
                    </div>
                    <div class="JdataInfo fr">
                       <span class="Jview"><img src="../../images/icon5.png"/>{{list.chatsCount}}</span>
                    </div>
                </div>
              </div>
          </li>
        </ul>
      </div>
      <div class="IndexPage">
         <pagination :cur="cur" :all="all"  :isJump="isJump" @listen="monitor" ref="page"></pagination>
      </div>
    <el-dialog
      :modal-append-to-body='false'
      :append-to-body='true'
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose" style="z-index: 200;">
      <span>确认删除?</span>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="delChatRoom()">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>
<script>
import pagination from '../pagination.vue'
import EnlargePicture from '../EnlargePicture.vue'
export default {
  name: 'chatRoomIndexHot',
  components: {
    pagination: pagination,
    EnlargePicture: EnlargePicture
  },
  watch: {
    currentPage: 'requestData',
    list: {
      handler: function (val, oldval) {
        // console.log(val)
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
      list: [],
      dialogVisible:false,
      allTags: [{id:1,name:'未开始'},{id: 2, name: '已结束'}],
      input: '',
      id:'',
      cur: 1,
      all: 1,
      pageSize: 20,
      tags: '',
      order: 'hot',
      ordername: '时间排序',
      drown: false,
      current: 0,
      isCurrent: 0,
      showFlag: -1,
      tumbCurrent: 0,
      show: false,
      scrolled: false,
      isJump: true,
      isSelectMask: false
    }
  },
  methods: {
    handleClose(done) {
      this.dialogVisible=false;
    },
    del(id){
      this.dialogVisible=true;
      this.id=id;
    },
    delChatRoom(){
      this.dialogVisible=false;
      const vm = this;
      let params = new URLSearchParams();
      params.append('id', this.id);
      this.$axios.post('/chat/delChatRoom', params)
        .then(function (res) {
          if(res.data.code===200){
            vm.ShowHtml(vm.order, 1,vm.current);
          }
        })
    },
    handleScroll: function (scroll) {
      this.scrolled = scroll
    },
    hidePic: function (event, temp) {
      this.list[temp]['flag'] = false
    },
    hidePic2: function (event, temp) {
      this.list[temp]['flag'] = false
    },
    thumbNext: function (index, temp) { // 缩略图
      this.list[temp]['showindex'] = index + 1
    },
    thumbPrev: function (index, temp) {
      this.list[temp]['showindex'] = index - 1
    },
    ShowBigPic: function (event, index, temp) {
      this.list[temp].flag = true
      this.list[temp]['showindex'] = index
    },
    GoTagsBtn: function (tagId, index) { // 切换tag
      this.current = index
      this.tags = tagId
      this.ShowHtml(this.order, 1,index)
      this.$refs.page.send(1)
    },
    goTop: function () {
      var gotoTop = function () {
        var currentPosition = document.documentElement.scrollTop || document.body.scrollTop
        currentPosition -= 80
        if (currentPosition > 0) {
          window.scrollTo(0, currentPosition)
        } else {
          window.scrollTo(0, 0)
          clearInterval(timer)
          timer = null
        }
      }
      var timer = setInterval(gotoTop, 50)
    },
    monitor: function (data) { // 分页监听
      this.ShowHtml(this.order, data)
      this.goTop()
    },
    closeSelect: function () { // 关闭下拉框
      this.isSelectMask = false
      this.drown = false
    },
    drownInput: function () { // 下拉框排序
      if (this.drown === true) {
        this.drown = false
      } else {
        this.drown = true
        this.isSelectMask = true
      }
    },
    drownSelect: function (order, ordername) {
      this.ordername = ordername
      this.drown = false
      this.isSelectMask = false
      this.order = order
      this.ShowHtml(this.order, 1,this.current)
      this.$refs.page.send(1)
    },
    search(){
      const vm = this;
      let params = new URLSearchParams();
      params.append('input', this.input);
      this.$axios.post('/chat/getChatRooms', params)
        .then(function (res) {
          vm.list = res.data.data
          vm.all = res.data.totalPage
          if (res.data.totalPage === 0 || res.data.totalPage < 2) {
            vm.all = 1
            vm.drownReflush = false
            vm.drownReflushText = false
          }
        })
    },
    ShowHtml: function (order, page,status) {
      const vm = this
      let params = new URLSearchParams();
      params.append('page', page);
      params.append('pagesize', 10);
      params.append('order',order);
      params.append('status',status);
      params.append('input',this.input);
      this.$axios.post('/chat/getChatRooms', params)
        .then(function (res) {
          vm.list = res.data.data
          vm.all = res.data.totalPage
          if (res.data.totalPage === 0 || res.data.totalPage < 2) {
            vm.all = 1
            vm.drownReflush = false
            vm.drownReflushText = false
          }
        })
    }
  },
  created: function () {
    window.addEventListener('scroll', this.handleScroll)
    const vm = this
    let params = new URLSearchParams();
    params.append('page', 1);
    params.append('pagesize',10);
    params.append('status',0);
    this.$axios.post('/chat/getChatRooms',params)
      .then(function (res) {
        vm.list = res.data.data
        vm.all = res.data.totalPage
        if (res.data.totalPage === 0 || res.data.totalPage < 2) {
          vm.all = 1
          vm.drownReflush = false
          vm.drownReflushText = false
        }
      })
  },
  mounted: function () {
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

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
   @import '../../sass/stylesheets/SocialIndexList.css';
</style>
