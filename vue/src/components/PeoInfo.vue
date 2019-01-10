<template>
  <div class="PeoInfo bgWhite" id="PeoInfo" style="">
    <div class="onLogin">
      <div class="PeoInfo-Hd">
         <img :src="isLogin === true ? info.headImage : 'http://www.iplaystone.com/static/common/images/loginPic.png'"/>
      </div>
      <a href="javascript:;" class="PeoInfo-Name" :title="info.username">{{isLogin === true ? info.username : '游客'}}</a>
      <h6 :title="info.introduce">{{info.introduce}} </h6>
      <div class="span" v-if="false">
        <a class="home cur" href="javascript:;">
           <span></span>
           <span>主页</span>
        </a>
        <a class="collection cur" href="javascript:;">
           <span></span>
           <span>收藏</span>
        </a>
        <a class="qz cur" href="javascript:;">
           <span></span>
           <span>圈子</span>
        </a>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'PeoInfo',
  data: function () {
    return {
      info: {},
      isLogin: false,
      myCircleListDetail: []
    }
  },
  methods: {
    showMoreMyCircleBtn: function () {
    },
    getLoggedInfo(){
      var vm = this
      this.$axios.post('/getLoggedInfo')
        .then((successResponse)=>{
          this.responseResult = JSON.stringify(successResponse.data)
          console.log(this.responseResult)
          if (successResponse.data.code === 200) {
            this.info = successResponse.data.data
            console.log("info:"+this.info)
            console.log(this.info.username)
            this.isLogin = true
          }
        }).catch(failResponse => {})
    }
  },
  created: function () {
    var vm = this
    this.$axios.post('/getLoggedInfo')
      .then((successResponse)=>{
        this.responseResult = JSON.stringify(successResponse.data)
        console.log(this.responseResult)
        if (successResponse.data.code === 200) {
          this.info = successResponse.data.data
          console.log("info:"+this.info)
          console.log(this.info.username)
          this.isLogin = true
        }
      }).catch(failResponse => {})
    // vm.$http({
    //   url: '//moment.snail.com/api/v1/user/my-circles',
    //   method: 'jsonp',
    //   jsonp: 'callback',
    //   emulateJSON: true,
    //   headers: {
    //     'Content-Type': 'x-www-from-urlencoded'
    //   }
    // }).then(function (res) {
    //   if (res.data.code === 200) {
    //     this.myCircleListDetail = res.data.list
    //     if (res.data.list.length < 7) {
    //       document.getElementById('showMoreMyCircle').style.display = 'none'
    //       document.getElementById('myCircleList').style.height = 'auto'
    //     }
    //   }
    // })
  }
}
</script>
<style scoped>
  @import '../sass/stylesheets/SocialPeoInfo.css'
</style>
