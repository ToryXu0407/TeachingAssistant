<template>
  <div class="PeoInfo bgWhite" id="PeoInfo" style="">
    <div class="onLogin">
      <div class="PeoInfo-Hd">
         <img :src="isLogin === true ? info.headImage : 'http://www.iplaystone.com/static/common/images/loginPic.png'"/>
      </div>
      <a href="javascript:;" class="PeoInfo-Name" :title="info.nickname">{{isLogin === true ? info.nickname : '游客'}}</a>
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
            document.getElementById('pdLogin').value = 'true'
            if(successResponse.data.data.isTeacher==='Y')
              document.getElementById('isTeacher').value = 'Y'
            else{
              document.getElementById('isTeacher').value = 'N'
            }
            this.isLogin = true
          }else{
            document.getElementById('pdLogin').value = 'false'
            document.getElementById('isTeacher').value = 'N'
            this.isLogin = false
          }
        }).catch(failResponse => {})
    }
  },
  created: function () {
    var vm = this
    this.$axios.post('/getLoggedInfo')
      .then((successResponse)=>{
        if (successResponse.data.code === 200) {
          vm.info = successResponse.data.data
          document.getElementById('pdLogin').value = 'true'
          if(successResponse.data.data.isTeacher==='Y')
            document.getElementById('isTeacher').value = 'Y'
          else{
            document.getElementById('isTeacher').value = 'N'
          }
          vm.isLogin = true
        }else{
          document.getElementById('pdLogin').value = 'false'
          document.getElementById('isTeacher').value = 'N'
        }
      }).catch(failResponse => {})
  }
}
</script>
<style scoped>
  @import '../sass/stylesheets/SocialPeoInfo.css'
</style>
