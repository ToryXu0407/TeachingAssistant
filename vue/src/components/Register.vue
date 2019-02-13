<template>
  <div id="register" data-title="注册 - 教学辅助网站">
    <!--<video preload="auto" class="me-video-player" autoplay="autoplay" loop="loop">
          <source src="../../static/vedio/sea.mp4" type="video/mp4">
      </video>-->

    <div class="me-login-box me-login-box-radius">
      <h1>教学辅助网站 注册</h1>

      <el-form ref="userForm" :model="userForm" :rules="rules">
        <el-form-item prop="account">
          <el-input placeholder="手机号" v-model="userForm.account"></el-input>
        </el-form-item>

        <el-form-item prop="nickname">
          <el-input placeholder="昵称" v-model="userForm.nickname"></el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input placeholder="密码" v-model="userForm.password"></el-input>
        </el-form-item>

        <el-form-item>
          <el-radio v-model="isTeacher" label="N">学生</el-radio>
          <el-radio v-model="isTeacher" label="Y">老师</el-radio>
        </el-form-item>

        <el-form-item size="small" class="me-login-button">
          <el-button type="primary" @click.native.prevent="register()">注册</el-button>
        </el-form-item>
      </el-form>

      <div class="me-login-design">
        <p>Designed by
          <strong>
            <router-link to="/" class="me-login-design-color">ToryXu</router-link>
          </strong>
        </p>
      </div>

    </div>
  </div>
</template>

<script>

  export default {
    name: 'Register',
    data() {
      return {
        userForm: {
          account: '',
          nickname: '',
          password: '',
        },
        isTeacher:'N',
        rules: {
          account: [
            {required: true, message: '请输入手机号', trigger: 'blur'},
            {max: 10, message: '不能大于10个字符', trigger: 'blur'}
          ],
          nickname: [
            {required: true, message: '请输入昵称', trigger: 'blur'},
            {max: 10, message: '不能大于10个字符', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'},
            {max: 10, message: '不能大于10个字符', trigger: 'blur'}
          ]
        }

      }
    },
    methods: {
      register(){
        const vm = this;
        let params = new URLSearchParams();
        params.append('username', this.userForm.account);
        params.append('nickname', this.userForm.nickname);
        params.append('password', this.userForm.password);
        params.append('isTeacher',this.isTeacher);
        this.$axios.post('/register/RegisterSubmit',params)
          .then((successResponse)=>{
            this.info = JSON.stringify(successResponse.data)
            if (successResponse.data.code === 200) {
              //登陆成功
              console.log("注册成功");
              this.$emit('refresh');
              vm.$router.push({path: '/'})
              // console.log(document.getElementById('pdLogin').value);
              // document.getElementById('pdLogin').value = 'true'
              // vm.$emit('on-suceess');
            }
          }).catch(failResponse => {})
      },
    },
    created:function(){
      this.$emit('hidefooter');
    }
  }
</script>

<style scoped>
  #login {
    min-width: 100%;
    min-height: 100%;
  }

  .me-video-player {
    background-color: transparent;
    width: 100%;
    height: 100%;
    object-fit: fill;
    display: block;
    position: absolute;
    left: 0;
    z-index: 0;
    top: 0;
  }

  .me-login-box {
    position: absolute;
    width: 300px;
    height: 320px;
    background-color: white;
    margin-top: 150px;
    margin-left: -180px;
    left: 50%;
    padding: 30px;
  }

  .me-login-box-radius {
    border-radius: 10px;
    box-shadow: 0px 0px 1px 1px rgba(161, 159, 159, 0.1);
  }

  .me-login-box h1 {
    text-align: center;
    font-size: 24px;
    margin-bottom: 20px;
    vertical-align: middle;
  }

  .me-login-design {
    text-align: center;
    font-family: 'Open Sans', sans-serif;
    font-size: 18px;
  }

  .me-login-design-color {
    color: #5FB878 !important;
  }

  .me-login-button {
    text-align: center;
  }

  .me-login-button button {
    width: 100%;
  }

</style>
