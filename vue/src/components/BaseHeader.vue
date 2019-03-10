<template>
<div>
  <el-header class="me-area">
    <el-row class="me-header">

      <el-col :span="4" class="me-header-left">
        <router-link to="/" class="me-title">
          <img src="http://oss.yiqihappy.top/zuccLogo.jpg"/>
        </router-link>
      </el-col>

      <el-col v-if="!simple" :span="16">
        <el-menu :router=true menu-trigger="click" active-text-color="#5FB878" :default-active="activeIndex"
                 mode="horizontal">
          <el-menu-item index="/"  style="font-size: 16px">首页</el-menu-item>
          <el-menu-item index="/course" style="font-size: 16px">课程</el-menu-item>
          <el-menu-item index="/social" style="font-size: 16px">讨论社</el-menu-item>
          <el-menu-item index="/chatroom" style="font-size: 16px">答疑聊天室</el-menu-item>
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
              <el-button type="text" @click="ShowLoginPop" style="font-size: 16px">登录</el-button>
            </el-menu-item>
            <el-menu-item index="" >
              <el-button type="text" @click="register" style="font-size: 16px">注册</el-button>
            </el-menu-item>
          </template>

          <template v-else="isLogin">
            <el-submenu index="">
              <template slot="title">
                  <img class="me-header-picture" :src="user.headImage"/>
              </template>
              <el-menu-item index="" @click="getInfo()"><i class="el-icon-arrow-left"></i>个人信息</el-menu-item>
              <el-menu-item index="" @click="logout"><i class="el-icon-back"></i>退出</el-menu-item>
            </el-submenu>
          </template>
        </el-menu>
      </el-col>

    </el-row>
    <LoginPop v-show="showDialog" @on-cancel="closeLoginPop" @on-suceess="LoginSuccess"></LoginPop>
  </el-header>
  <el-dialog
    title="添加课程"
    :visible.sync="dialogVisible"
    width="30%"
    :before-close="handleClose">
    <el-form>
      <el-form-item label="昵称" >
        <el-input v-model="nickname"></el-input>
      </el-form-item>
      <el-form-item label="头像:点击修改" >
        <el-upload
          class="avatar-uploader"
          action="#"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload">
          <img width="250px" height="250px" v-if="imageUrl" :src="imageUrl" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
    <el-button @click=handleClose()>取 消</el-button>
    <el-button type="primary" @click="updateInfo()">修 改</el-button>
  </span>
  </el-dialog>
</div>

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
        showDialog:false,
        imageUrl:'',
        nickname:'',
        dialogVisible:false,
      }
    },
    computed: {
    },
    created: function () {

    },
    methods: {
      handleClose(done) {
        this.dialogVisible=false;
        this.imageUrl = this.user.headImage;
      },
      handleAvatarSuccess(res, file) {
        this.imageUrl = URL.createObjectURL(file.raw);
      },
      beforeAvatarUpload(file) {
        const isLt2M = file.size / 1024 / 1024 < 2;
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        this.file = file;
        return isLt2M;
      },
      getInfo(){
        this.dialogVisible=true;
      },
      updateInfo:function(){
        this.dialogVisible=false;
        var vm =this;
        var params = new FormData();
        params.append("id",vm.user.userid);
        params.append('file',this.file);
        params.append('nickname',this.nickname);
        this.$axios.post('/updateUser', params)
          .then(function (res) {
            if(res.data.code===200){
              vm.getLoggedInfo();
            }
          });
      },
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
        this.$emit('refresh');
      },
      getLoggedInfo(){
        var vm = this
        this.$axios.post('/getLoggedInfo')
          .then((successResponse)=>{
            if (successResponse.data.code === 200) {
              document.getElementById('pdLogin').value = 'true'
              vm.user = successResponse.data.data;
              if(vm.user.type===0){
                document.getElementById('isTeacher').value = 'Y'
                document.getElementById('isAdmin').value = 'Y'
              }else if(vm.user.type===1){
                document.getElementById('isTeacher').value = 'Y'
                document.getElementById('isAdmin').value = 'N'
              }else{
                document.getElementById('isTeacher').value = 'N'
                document.getElementById('isAdmin').value = 'N'
              }
              vm.isLogin = true
              vm.imageUrl = vm.user.headImage;
              vm.nickname = vm.user.nickname;
            }else{
              document.getElementById('pdLogin').value = 'false'
              document.getElementById('isTeacher').value = 'N'
              document.getElementById('isAdmin').value = 'N'
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
              document.getElementById('isTeacher').value = 'N'
              document.getElementById('isAdmin').value = 'N'
              vm.$emit('refresh');
              }
        })
      }
    },
    mounted:function () {
      var vm = this;
      vm.getLoggedInfo();
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
