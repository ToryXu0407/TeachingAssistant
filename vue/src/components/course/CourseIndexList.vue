<template>
  <div class="SocialIndexList" id="SocialIndexList">
      <div class="IndexListType clearfix" id="IndexListType">
         <div class="TypeTag fl">
             <ul>
                 <li>
                    <a href="javascript:;"  :class="{current:current == -1}" title="课程" data-tagid="" @click="GoTagsBtn('', -1)" wn_tj_click_href wn_tj_click_gameId wn_tj_click_excel="all_select" wn_tj_click_id>全部</a>
                 </li>
               <li></li><li></li><li></li>
               <li></li>
               <li>
                 <el-input v-model="input" style="width:100px;margin-left:140px;margin-top: -10px;" placeholder="请输入课程"></el-input>
               </li>
               <li>
                 <el-button icon="el-icon-search" @click="search" circle style="margin-left:170px;margin-top: -10px;"></el-button>
               </li>
             </ul>
         </div>
      </div>
      <div class="IndexListCont">
        <ul>
          <li v-for="(list, i) in list" :data-listId="list.id" :name="list.id">
              <div class="Jitems">
                <div class="Jitems-Title">
                  <router-link :to="{ name: 'courseDetail', params: {'courseId':list.id ,'onPage':0}}" :title="list.name" :listId="list.id">{{list.name}}</router-link>
                </div>
                <i v-if="isAdmin==='Y'" @click="del(list.id)" style="font-size:20px;float: right; padding: 3px 0" class="el-icon-delete"></i>
                <div class="Jitems-Info">
                    <div class="JuserInfo fl">
                        <a href="javascript:;" class="JuserInfo-people default" :title="list.name">
                            <img :src="list.image"/>
                        </a>
                    </div>
                    <div class="JdataInfo fr">
                       <span class="Jview"><img src="../../images/icon4.png"/>{{list.viewCount}}</span>
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
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <span>确认删除?</span>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="delCourse()">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>
<script>
import pagination from '../pagination.vue'
import EnlargePicture from '../EnlargePicture.vue'
export default {
  name: 'CourseIndexList',
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
      allTags: [{id: 1}],
      isAdmin:'N',
      id:'',
      dialogVisible:false,
      cur: 1,
      all: 1,
      pageSize: 20,
      tags: '',
      input:'',
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
    handleClose(done) {
      this.dialogVisible=false;
    },
    del(id){
      this.dialogVisible=true;
      this.id=id;
    },
    delCourse(){
      this.dialogVisible=false;
      const vm = this;
      let params = new URLSearchParams();
      params.append('id', this.id);
      this.$axios.post('/course/delCourse', params)
        .then(function (res) {
          if(res.data.code===200){
            alert("删除成功!");
            vm.CreatedHtml();
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
      this.$axios.post('/course/getCourse', params)
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
      this.$axios.post('/course/getCourse', params)
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
    CreatedHtml:function () {
      const vm = this
      let params = new URLSearchParams();
      params.append('page', 1);
      params.append('pagesize',10);
      this.$axios.post('/course/getCourse',params)
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
    this.$axios.post('/course/getCourse',params)
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
  mounted:function () {
    this.isAdmin =  document.getElementById('isAdmin').value;
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
   @import '../../sass/stylesheets/SocialIndexList.css';
</style>
