<template>
  <div id="SocialIndex" class="SocialIndex">
     <div class="SocialIndexHeader bgWhite" id="SocialIndexHeader">
        <SocialIndexHeader :HdInfoData.sync="HdInfoData" :isSiH.sync="isSiH"  @refresh="refresh"></SocialIndexHeader>
     </div>
     <div class="SocialIndexMain clearfix">
        <div class="MainList fl bgWhite">
            <SocialIndexList></SocialIndexList>
        </div>
        <div class="MainMoudle fr" id="MainMoudle">
            <PeoInfo ref="myPeoInfo"></PeoInfo>
            <SocialIndexHot></SocialIndexHot>
            <span class="goTop cur" v-show="isGoTop" @click="goTop()"></span>
        </div>
     </div>
  </div>
</template>
<script>
  import SocialIndexHeader from './components/SocialIndexHeader.vue'
  import SocialIndexAdmin from './components/SocialIndexAdmin.vue'
  import SocialIndexHot from './components/SocialIndexHot.vue'
  import SocialIndexList from './components/SocialIndexList.vue'
  import SocialIndexRecommend from './components/SocialIndexRecommend.vue'
  import PeoInfo from './components/PeoInfo.vue'
  export default {
    name: 'SocialIndex',
    components: {
      SocialIndexHeader: SocialIndexHeader,
      SocialIndexAdmin: SocialIndexAdmin,
      SocialIndexHot: SocialIndexHot,
      SocialIndexList: SocialIndexList,
      SocialIndexRecommend: SocialIndexRecommend,
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
        console.log("egtLoggedInfo")
        this.$refs.myPeoInfo.getLoggedInfo();
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
          document.getElementById('SocialIndexRecommend').classList.add('fixed')
          document.getElementById('MainMoudle').children[0].classList.add('hfixed')
        } else if (this.scrolled < 800) {
          document.getElementById('MainMoudle').children[0].classList.remove('hfixed')
          document.getElementById('SocialIndexRecommend').classList.remove('fixed')
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
      this.circleId = this.$route.params.circleId
      window.addEventListener('scroll', this.handleScroll)
      var vm = this
      vm.$http({
        url: '//moment.snail.com/api/v1/circle/info',
        method: 'jsonp',
        params: {
          'circle_id': this.circleId
        },
        jsonp: 'callback',
        emulateJSON: true,
        headers: {
          'Content-Type': 'x-www-from-urlencoded'
        }
      }).then(function (res) {
        if (res.data.code === 200) {
          this.HdInfoData = res.data.info
          console.log(res.data)
        } else if (res.data.code === 404) {
          var url = 'http://stone.snail.com/error/404.html?from=circle&type=1'
          window.location.href = url
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
