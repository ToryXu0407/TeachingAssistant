<template>
  <div class="SocialIndexList" id="SocialIndexList">
      <div class="IndexListType clearfix" id="IndexListType">
         <div class="TypeTag fl">
             <ul>
                 <li>
                    <a href="javascript:;"  :class="{current:current == -1}" title="进行中"  @click="GoTagsBtn('', -1)" >进行中</a>
                 </li>
                 <li v-for="(allTags,index) in allTags" >
                    <a href="javascript:;" :title="allTags.name" :data-tagid="allTags.id" @click="GoTagsBtn(allTags.id, index)" :class="{current:index == current}" wn_tj_click_href :wn_tj_click_excel="allTags.name" wn_tj_click_id>{{allTags.name}}</a>
                 </li>
             <li></li><li></li>
               <li>
                 <el-input v-model="input" style="width:100px;margin-left:30px;margin-top: -10px;" placeholder="请输入内容"></el-input>
               </li>
               <li>
                 <el-button icon="el-icon-search" circle @click="search" style="margin-left:60px;margin-top: -10px;"></el-button>
               </li>
             </ul>
        </div>
        <div class="scrrenTag fr">
           <h6 class="scrrenTagInput" @click="drownInput"><span>{{ordername}}</span><i :class="['rotatez',{'roate':drown === false}]"></i></h6>
           <ul class="InputSelect" v-if="drown">
              <li data-order="hot" @click="drownSelect('hot','热门排序')" wn_tj_click_href wn_tj_click_gameId wn_tj_click_excel="hot_ordering" wn_tj_click_id>热门排序</li>
              <li data-order="time" @click="drownSelect('time','时间排序')" wn_tj_click_href wn_tj_click_gameId wn_tj_click_excel="time_ordering" wn_tj_click_id>时间排序</li>
           </ul>
         </div>
      </div>
      <div class="IndexListCont">
        <ul>
          <li v-for="(list, i) in list" :data-listId="list.id" :name="list.id">
              <div class="Jitems">
                <div class="Jitems-Title">
                  <router-link :to="{ name: 'chatRoomDetail', params: {'chatRoomId':list.id }}" :title="list.label" :listId="list.id">{{list.label}}</router-link>
                  <i class="icon-good" v-if="list.isSticky=='Y'" >精</i>
                </div>
                <div class="Jitems-Info">
                    <div class="JuserInfo fl">
                        <a href="javascript:;" class="JuserInfo-people default" :title="list.nickname">
                            <img :src="list.headImage"/>
                            {{list.nickname}}
                        </a>
                        <span class="JuserInfo-time" :title="list.createTime">{{list.createTime}} To {{list.createTime}}</span>
                    </div>
                    <div class="JdataInfo fr">
                       <span class="Jview"><img src="../../images/icon4.png"/>{{list.viewCount}}</span>
                       <span class="Jreply"><img src="../../images/icon5.png"/>{{list.commentCount}}</span>
                    </div>
                </div>
              </div>
          </li>
        </ul>
      </div>
      <div class="IndexPage">
         <pagination :cur="cur" :all="all"  :isJump="isJump" @listen="monitor" ref="page"></pagination>
      </div>
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
      list: [],
      allTags: [{id:2,name:'未开始'},{id: 1, name: '已结束'}],
      input: '',
      cur: 1,
      all: 1,
      pageSize: 20,
      tags: '',
      order: 'time',
      ordername: '时间排序',
      drown: false,
      current: -1,
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
      console.log("tags1:"+this.tags)
      this.ShowHtml(this.order, 1)
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
      this.ShowHtml(this.order, 1)
      this.$refs.page.send(1)
    },
    search(){
      const vm = this;
      let params = new URLSearchParams();
      params.append('input', this.input);
      this.$axios.post('/article/getArticle', params)
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
    ShowHtml: function (order, page) {
      const vm = this
      let params = new URLSearchParams();
      params.append('page', page);
      params.append('pagesize', 10);
      params.append('order',order);
      if(this.tags===1){
        params.append('isSticky','Y');
      }
      this.$axios.post('/article/getArticle', params)
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
    this.$axios.post('/article/getArticle',params)
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
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
   @import '../../sass/stylesheets/SocialIndexList.css';
</style>
