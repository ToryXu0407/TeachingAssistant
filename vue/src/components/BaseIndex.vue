<template>
  <el-container>
    <el-header height="0px" width="0px">
      <el-carousel :interval="4000" type="card" height="300px" autoplay="autoplay" loop="loop">
        <el-carousel-item v-for="(list,i) in courses" :key="i">
          <router-link :to="{ name: 'courseDetail', params: {'courseId':list.id ,'onPage':0}}" >
            <img :src="list.image">
          </router-link>
        </el-carousel-item>
      </el-carousel>
    </el-header>
    <el-container>
    <el-aside style="width: 400px;margin-top: 315px;">
      <BaseForum></BaseForum>
    </el-aside>
    <el-main style="width: 200px;margin-top: 300px;">
      <BaseCourse style="margin-left: 100px;"></BaseCourse>
    </el-main>
    </el-container>
  </el-container>
</template>

<script>
  import BaseForum from '@/components/BaseForum'
  import BaseCourse from '@/components/BaseCourse'

  export default {
    name: "BaseIndex",
    components: {
      BaseForum: BaseForum,
      BaseCourse: BaseCourse,
    },
    data() {
      return {
        courses: '',
      }
    },
    created: function () {
      const vm = this
      let params = new URLSearchParams();
      this.$axios.post('/course/getCourselArticle',params)
        .then(function (res) {
          if(res.data.code===200)
          vm.courses = res.data.data
        })
    }
  }
</script>

<style scoped>
  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 400px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
</style>
