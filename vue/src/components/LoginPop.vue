<template>
  <div class="LoginPop" id="LoginPop">
    <div class="LoginPop_outer">
      <i class="LoginPop_cancle" @click="closepop"></i>
      <div class="LoginPop_title"><img src="http://www.iplaystone.com/static/common/images/loginPic.png"/></div>
      <div class="LoginPop_input"><input  v-model="loginInfoVo.username" placeholder="电子邮箱/手机号" />
        <i class="LoginPop_icon LoginPop_user"></i>
        <div class="LoginPop_error"></div>
      </div>
      <div class="LoginPop_input"><input type="password" v-model="loginInfoVo.password"  placeholder="密码" />
        <i class="LoginPop_icon LoginPop_pwd"></i>
        <div class="LoginPop_error"></div>
      </div>
      <div class="LoginPop_btn"><button id="LoginPop_submit" @click="login">登录</button></div>
      <div class="LoginPop_box"><a href="http://www.iplaystone.com/static/web/resetPwd.html" class="LoginPop_forget">忘记密码?</a><span></span><a href="http://www.iplaystone.com/static/web/register.html" target="_blank" class="LoginPop_register">立即注册</a></div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'LoginPop',
  data: function () {
    return {
      loginInfoVo: { username: '', password: '' },
      info:{}
    }
  },
  methods: {
    login () {
      const vm = this;
      let params = new URLSearchParams();
      params.append('username', this.loginInfoVo.username);
      params.append('password', this.loginInfoVo.password);
      this.$axios.post('/loginSubmit',params)
        .then((successResponse)=>{
          this.info = JSON.stringify(successResponse.data)
          if (successResponse.data.code === 200) {
            //登陆成功
            console.log("登陆成功");
            console.log(document.getElementById('pdLogin').value);
            document.getElementById('pdLogin').value = 'true'
            vm.$emit('on-suceess');
          }
        }).catch(failResponse => {})
    },
    closepop: function () {
      this.$emit('on-cancel');
    }
  },
   created: function () {
  //   var vm = this
  //   vm.$http({
  //     url: '//moment.snail.com/api/v1/user/info',
  //     method: 'jsonp',
  //     jsonp: 'callback',
  //     emulateJSON: true,
  //     headers: {
  //       'Content-Type': 'x-www-from-urlencoded'
  //     }
  //   }).then(function (res) {
  //     if (res.data.code === 200) {
  //       this.info = res.data.info
  //       this.isLogin = true
  //     }
  //   })
  //   vm.$http({
  //     url: '//moment.snail.com/api/v1/user/my-circles',
  //     method: 'jsonp',
  //     jsonp: 'callback',
  //     emulateJSON: true,
  //     headers: {
  //       'Content-Type': 'x-www-from-urlencoded'
  //     }
  //   }).then(function (res) {
  //     if (res.data.code === 200) {
  //       this.myCircleListDetail = res.data.list
  //     }
  //   })
   }
}
</script>
<style scoped>
  @import '../sass/stylesheets/LoginPop.css';
</style>
