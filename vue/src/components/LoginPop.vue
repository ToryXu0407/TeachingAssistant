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
      <div class="LoginPop_box"><a href="javascript:void(0);" class="LoginPop_forget">忘记密码?</a><span></span><a href="javascript:void(0);" @click="register" target="_blank" class="LoginPop_register">立即注册</a></div>
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
            document.getElementById('pdLogin').value = 'true'
            if(successResponse.data.data.isTeacher==='Y')
              document.getElementById('isTeacher').value = 'Y'
            else{
              document.getElementById('isTeacher').value = 'N'
            }
            // this.$router.push("/")
            vm.$emit('on-suceess');
          }
        }).catch(failResponse => {})
    },
    register(){
      this.$emit('on-cancel');
      this.$router.push("/register")
    },
    closepop: function () {
      this.$emit('on-cancel');
    }
  },
   created: function () {
   }
}
</script>
<style scoped>
  @import '../sass/stylesheets/LoginPop.css';
</style>
