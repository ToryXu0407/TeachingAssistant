<template>
  <div id="SocialIndex" class="SocialIndex">
    <div class="SocialIndexHeader bgWhite" id="SocialIndexHeader">
      <CourseDetailHeader :HdInfoData.sync="HdInfoData" :isSiH.sync="isSiH"  @refresh="refresh"></CourseDetailHeader>
    </div>
    <div class="SocialIndexMain clearfix">
      <div class="MainList fl bgWhite">
        <el-container style="height: 500px; border: 1px solid #eee">

          <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
            <el-menu :default-openeds="['1', '2']">
              <el-menu-item index="" @click.native="switchComp(0)">
                <template slot="title" ><i class="el-icon-menu"></i>课程通知</template>
              </el-menu-item>
              <el-menu-item index="" @click.native="switchComp(1)">
                <template slot="title" ><i class="el-icon-menu"></i>课件</template>
              </el-menu-item>
            </el-menu>
          </el-aside>

          <el-container>
            <el-header style="text-align: right; font-size: 12px">
              <span style="font: 18px large;font-family: Hiragino Sans GB;color: #409EFF;" >{{componentName}}</span>
            </el-header>

            <el-main>
              <component v-bind:is="component" :courseId="courseId"></component>
            </el-main>
          </el-container>
        </el-container>
      </div>
      <div class="MainMoudle fr" id="MainMoudle">
        <PeoInfo ref="myPeoInfo"></PeoInfo>
        <CourseIndexHot></CourseIndexHot>
        <span class="goTop cur" v-show="isGoTop" @click="goTop()"></span>
      </div>
    </div>
  </div>
</template>
<script>
  import CourseDetailHeader from './CourseDetailHeader.vue'
  import CourseIndexHot from './CourseIndexHot.vue'
  import CourseNotice from './CourseNotice.vue'
  import Courseware from './Courseware.vue'
  import PeoInfo from '../PeoInfo.vue'
  export default {
    name: 'SocialIndex',
    components: {
      CourseDetailHeader: CourseDetailHeader,
      CourseIndexHot: CourseIndexHot,
      CourseNotice: CourseNotice,
      Courseware: Courseware,
      PeoInfo: PeoInfo

    },
    data () {
      return {
        HdInfoData: {},
        component:'CourseNotice',
        componentName:'课程通知',
        isShow: false,
        scrolled: false,
        isSiH: true,
        circleId: '',
        isGoTop: false
      }
    },
    ready () {
      window.addEventListener('scroll', this.handleScroll)
    },
    methods: {
      switchComp(index){
        if(index===0){
          this.component = 'CourseNotice';
          this.componentName = '课程通知';
        }else if (index===1){
          this.component = 'Courseware';
          this.componentName = '课件'
        }
      },
      refresh(){
        this.$refs.myPeoInfo.getLoggedInfo();
        this.$emit('refresh');
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
      handleScroll () {
        this.scrolled = window.scrollY
        if (this.scrolled > 800) {
          document.getElementById('IndexListType').style.position = 'fixed'
        } else {
          document.getElementById('IndexListType').style.position = 'static'
        }
        if (this.scrolled > 1236) {
          this.isShow = true
          this.isGoTop = true
          // document.getElementById('SocialIndexRecommend').classList.add('fixed')
          document.getElementById('MainMoudle').children[0].classList.add('hfixed')
        } else if (this.scrolled < 800) {
          document.getElementById('MainMoudle').children[0].classList.remove('hfixed')
          // document.getElementById('SocialIndexRecommend').classList.remove('fixed')
          this.isShow = false
          this.isGoTop = false
        }
        if (document.documentElement.clientHeight < 700) {
          this.isGoTop = false
        }
      }
    },
    destroyed () {
      window.removeEventListener('scroll', this.handleScroll)
    },
    created: function () {
      const vm = this;
      this.courseId = this.$route.params.courseId
      window.addEventListener('scroll', this.handleScroll)
      let params = new URLSearchParams();
      params.append('id', this.$route.params.courseId);
      this.$axios.post('/course/getCourseById',params)
        .then((res)=>{
          if (res.data.code === 200) {
            vm.HdInfoData = res.data.data
          }
        })
    }
  }
</script>

<style>
  .MainMoudle{
    width: 280px;
  }
</style>
