<template>
  <div>
    <div v-for="(courseware,o) in coursewares" :key="o" class="text item">
    <el-card class="box-card">
        <img src="../../assets/img/document_on.svg">
        <div slot="header" class="clearfix" >
          <span>{{courseware.name}}</span>
        </div>
        <div >
          已附加文件：<a :href=courseware.url download="">{{courseware.name}}</a>
          </div>
    </el-card>
    </div>

    <pagination :cur="cur" :all="all"  :isJump="isJump" @listen="monitor" ref="page"></pagination>
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
        isJump: true,
        currentDate: new Date(),
        coursewares: ''
      };
    },
    methods:{
      monitor: function (data) { // 分页监听
        this.ShowHtml(this.order, data)
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
      ShowHtml(order,page){
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
