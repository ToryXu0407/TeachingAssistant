<template>
  <el-header class="me-area">
    <el-row class="me-header">

      <el-col :span="4" class="me-header-left">
        <router-link to="/" class="me-title">
          <img src="../assets/img/zuccLogo.jpg"/>
        </router-link>
      </el-col>

      <el-col v-if="!simple" :span="16">
        <el-menu :router=true menu-trigger="click" active-text-color="#5FB878" :default-active="activeIndex"
                 mode="horizontal">
          <el-menu-item index="/">首页</el-menu-item>
          <el-menu-item index="/course">课程</el-menu-item>
          <el-menu-item index="/social">讨论社</el-menu-item>
          <el-menu-item index="/">答疑聊天室</el-menu-item>
          <!--<el-menu-item index="/category/all">文章分类</el-menu-item>-->
          <!--<el-menu-item index="/tag/all">标签</el-menu-item>-->
          <!--<el-menu-item index="/archives">文章归档</el-menu-item>-->
          <!--<el-menu-item index="/log">日志</el-menu-item>-->
          <!--<el-menu-item index="/messageBoard">留言板</el-menu-item>-->

          <el-col :span="4" :offset="4">
            <!--<el-menu-item index="/write"><i class="el-icon-edit"></i>写文章</el-menu-item>-->
          </el-col>

        </el-menu>
      </el-col>

      <template v-else>
        <slot></slot>
      </template>

      <el-col :span="4">
        <el-menu :router=true menu-trigger="click" mode="horizontal" active-text-color="#5FB878">
          <!--<template v-if="!user.login">-->
          <template v-if="!isLogin">
            <!--<el-menu-item index="/login">-->
            <el-menu-item index="">
              <el-button type="text" @click="ShowLoginPop">登录</el-button>
            </el-menu-item>
            <el-menu-item index="" >
              <el-button type="text" @click="register">注册</el-button>
            </el-menu-item>
          </template>

          <template v-else="isLogin">
            <el-submenu index="">
              <template slot="title">
                  <img class="me-header-picture" :src="user.headImage"/>
              </template>
              <el-menu-item index="" @click="logout"><i class="el-icon-back"></i>退出</el-menu-item>
            </el-submenu>
          </template>
        </el-menu>
      </el-col>

    </el-row>
    <LoginPop v-show="showDialog" @on-cancel="closeLoginPop" @on-suceess="LoginSuccess"></LoginPop>
  </el-header>

</template>

<script>
  import LoginPop from './LoginPop.vue'
  export default {
    name: 'BaseHeader',
    components: {
      LoginPop: LoginPop,
    },
    props: {
      activeIndex: String,
      simple: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        isLogin:false,
        user:'',
        showDialog:false
      }
    },
    computed: {
      // user() {
      //   let login = this.$store.state.account.length != 0
      //   let avatar = this.$store.state.avatar
      //   return {
      //     login, avatar
      //   }
      // }
    },
    created: function () {
      var vm = this
      this.$axios.post('/getLoggedInfo')
        .then((successResponse)=>{
          if (successResponse.data.code === 200) {
            document.getElementById('pdLogin').value = 'true'
            vm.user = successResponse.data.data;
            vm.isLogin = true
          }else{
            document.getElementById('pdLogin').value = 'false'
            vm.isLogin = false
          }
        }).catch(failResponse => {})
    },
    methods: {
      register(){
        this.$router.push("/register")
      },
      ShowLoginPop: function () {
        this.showDialog = true;
      },
      closeLoginPop: function () {
          this.showDialog = false;
      },
      LoginSuccess: function () {
        this.showDialog = false;
        this.$router.push("/");
        this.$emit('refresh');
      },
      getLoggedInfo(){
        var vm = this
        this.$axios.post('/getLoggedInfo')
          .then((successResponse)=>{
            if (successResponse.data.code === 200) {
              document.getElementById('pdLogin').value = 'true'
              vm.user = successResponse.data.data;
              vm.isLogin = true
            }else{
              document.getElementById('pdLogin').value = 'false'
              vm.isLogin = false
            }
          }).catch(failResponse => {})
      },
      logout() {
        let vm = this
        console.log("注销")
        this.$axios.post('/logout')
          .then((successResponse)=>{
            if(successResponse.data.code===200){
              document.getElementById('pdLogin').value = 'false'
              vm.$emit('refresh');
              }
        })
      }
    }
  }
</script>

<style>
  .el-header {
    z-index: 1024;
    min-width: 100%;
    box-shadow: 0 2px 3px hsla(0, 0%, 7%, .1), 0 0 0 1px hsla(0, 0%, 7%, .1);
  }

  .me-title {
    font-size: 24px;
  }

  .me-header-left {
  }

  .me-title img {
    max-width: 100%;
    width:200px;
    height:60px;
  }

  .me-header-picture {
    width: 36px;
    height: 36px;
    border: 1px solid #ddd;
    border-radius: 50%;
    vertical-align: middle;
    background-color: #5fb878;
  }
</style>
