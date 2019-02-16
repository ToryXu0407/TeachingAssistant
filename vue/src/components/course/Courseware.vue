<template>
  <div>
    <div v-for="(courseware,o) in coursewares" :key="o" class="text item">
    <el-card class="box-card">
        <img src="../../assets/img/document_on.svg">
        <div slot="header" class="clearfix" >
          <span>{{courseware.name}}</span>
          <i v-if="isTeacher==='Y'" @click="del(courseware.id)" style="font-size:20px;float: right; padding: 3px 0" class="el-icon-delete"></i>
        </div>
        <div >
          已附加文件：<a :href=courseware.url download="">{{courseware.name}}</a>
          </div>
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
    name: "Courseware",
    components:{
      pagination:pagination
    },
    data() {
      return {
        cur: 1,
        all: 1,
        dialogVisible:false,
        isJump: true,
        currentDate: new Date(),
        coursewares: ',',
        isTeacher:'N',
      };
    },
    methods:{
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
        this.$axios.post('/courseware/delCourseware', params)
          .then(function (res) {
            if(res.data.code===200) {
              alert("删除成功!");
              vm.ShowHtml(1);
            }
          })
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
      ShowHtml(page){
        const vm = this;
        let params = new URLSearchParams();
        params.append('id', this.$route.params.courseId);
        params.append("page",page);
        params.append("pagesize",5);
        this.$axios.post('/courseware/getCoursewareByCourseId',params)
          .then((res)=>{
            if (res.data.code === 200) {
              vm.coursewares = res.data.data
              vm.all = res.data.totalPage
            }
          })
      },
    },
    created: function () {
      const vm = this;
      let params = new URLSearchParams();
      params.append('id', this.$route.params.courseId);
      params.append("pagesize",5);
      this.$axios.post('/courseware/getCoursewareByCourseId',params)
        .then((res)=>{
          if (res.data.code === 200) {
            vm.coursewares = res.data.data
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
