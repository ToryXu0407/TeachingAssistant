<template>
  <div id="CourseIndex" class="SocialIndex">
     <!--<div class="SocialIndexHeader bgWhite" id="SocialIndexHeader">-->
        <!--<SocialIndexHeader :HdInfoData.sync="HdInfoData" :isSiH.sync="isSiH"  @refresh="refresh"></SocialIndexHeader>-->
     <!--</div>-->
     <div class="SocialIndexMain clearfix">
        <div class="MainList fl bgWhite">
            <CourseIndexList></CourseIndexList>
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
  import CourseIndexList from './CourseIndexList.vue'
  import CourseIndexHot from './CourseIndexHot.vue'
  import PeoInfo from './../PeoInfo.vue'
  export default {
    name: 'CourseIndex',
    components: {
      CourseIndexList: CourseIndexList,
      CourseIndexHot: CourseIndexHot,
      PeoInfo: PeoInfo
    },
    data () {
      return {
        HdInfoData: {},
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
      window.addEventListener('scroll', this.handleScroll)
      this.$emit('showfooter');
    }
  }
</script>

<style>
  .MainMoudle{
    width: 280px;
  }
</style>
