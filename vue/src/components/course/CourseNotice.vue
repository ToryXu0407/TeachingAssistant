<template>
  <div>
    <div v-for="(notice,i) in notices" :key="i">
    <el-card class="box-card">
      <div slot="header" class="clearfix" >
        <span>{{notice.name}}</span>
        <i v-if="isTeacher==='Y'" @click="del(notice.id)" style="font-size:20px;float: right; padding: 3px 0" class="el-icon-delete"></i>
      </div>
      <div class="text item" v-html="notice.content">
      </div>
        <time class="time">{{ notice.createTime }}</time>
    </el-card>
    </div>
    <pagination :cur="cur" :all="all"  :isJump="isJump" @listen="monitor" ref="page"></pagination>
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <span>确认删除?</span>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="delnotice()">确 定</el-button>
      </span>
    </el-dialog>

  </div>


</template>

<script>
  import pagination from '../pagination'
    export default {
      name: "CourseNotice",
      components: {
        pagination: pagination
      },
      data() {
        return {
          cur: 1,
          all: 1,
          isJump: true,
          isTeacher:'N',
          order:'',
          currentDate: new Date(),
          id:'',
          notices: '',
          dialogVisible:false,
          dialogVisible2:false
        };
      },
      methods: {
        handleClose(done) {
          this.dialogVisible=false;
        },
        del(id){
          this.dialogVisible=true;
          this.id=id;
        },
        delnotice(){
          this.dialogVisible=false;
          const vm = this;
          let params = new URLSearchParams();
          params.append('id', this.id);
          this.$axios.post('/notice/delNotice', params)
            .then(function (res) {
              if(res.data.code===200) {
                alert("删除成功!");
                vm.ShowHtml(1);
              }
            })
        },
        handleClose2(done) {
          this.dialogVisible2=false;
        },
        monitor: function (data) { // 分页监听
          this.ShowHtml(data)
          this.goTop()
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
        ShowHtml(page) {
          const vm = this;
          let params = new URLSearchParams();
          params.append('id', this.$route.params.courseId);
          params.append("page", page);
          params.append("pagesize", 5);
          params.append("created",false)
          this.$axios.post('/notice/getNoticesByCourseId', params)
            .then((res) => {
              if (res.data.code === 200) {
                vm.notices = res.data.data
                vm.all = res.data.totalPage
              }
            })
        },
      },
      created: function () {
        const vm = this;
        let params = new URLSearchParams();
        params.append('id', this.$route.params.courseId);
        params.append("pagesize", 5);
        //用来判断是否是初次进入，以用作添加viewCount的条件
        params.append("created",true)
        this.$axios.post('/notice/getNoticesByCourseId', params)
          .then((res) => {
            if (res.data.code === 200) {
              vm.notices = res.data.data
              vm.all = res.data.totalPage
            }
          })
      },
      mounted:function () {
        var vm = this
        this.$axios.post('/getLoggedInfo')
          .then((successResponse)=>{
            if (successResponse.data.code === 200) {
              document.getElementById('pdLogin').value = 'true'
              vm.user = successResponse.data.data;
              if(vm.user.type===0){
                document.getElementById('isTeacher').value = 'Y'
                document.getElementById('isAdmin').value = 'Y'
              }else if(vm.user.type===1){
                document.getElementById('isTeacher').value = 'Y'
                document.getElementById('isAdmin').value = 'N'
              }else{
                document.getElementById('isTeacher').value = 'N'
                document.getElementById('isAdmin').value = 'N'
              }
              vm.isLogin = true
            }else{
              document.getElementById('pdLogin').value = 'false'
              document.getElementById('isTeacher').value = 'N'
              document.getElementById('isAdmin').value = 'N'
              vm.isLogin = false
            }
            vm.isTeacher = document.getElementById('isTeacher').value;
          }).catch(failResponse => {})
      }
    }

</script>

<style scoped>
  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 450px;
    margin-bottom: 20px;
  }

</style>
