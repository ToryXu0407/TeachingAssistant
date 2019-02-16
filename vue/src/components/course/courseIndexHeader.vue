<template>
  <div class="HdInfo clearfix">
    <div class="SiH-Info fl" :data-QzId="HdInfoData.id">
        <img :src="HdInfoData.icon" class="SiH-Info-Pic fl"/>
        <div class="SiH-Info-name fl">
            <div class="Hname">
                <!-- <h5 class="fl" :title="HdInfoData.name" :to="{ name: 'circle', params: {'circleId':HdInfoData.id}}">{{HdInfoData.name}}</h5> -->
                <div class="fl h5">{{HdInfoData.name}}</div>
            </div>
            <h4 class="Htitle" :title="HdInfoData.description">{{HdInfoData.description}}</h4>
        </div>
    </div>
    <div class="SiH-Href fr" v-show="isAdmin==='Y'" >
      <div class="SiH-Launch2 fr animation" @click="Add()">
        创建课程
      </div>
    </div>
    <el-dialog
      title="添加课程"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <span>课程名</span>
      <el-input v-model="courseName" placeholder="请输入课程名"></el-input>
      <span>课程说明</span>
      <el-input v-model="description" placeholder="请输入课程说明"></el-input>
      <span>上传课程图像</span>
      <el-upload
        class="avatar-uploader"
        action="#"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload">
        <img v-if="imageUrl" :src="imageUrl" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="CourseAdd()">添 加</el-button>
  </span>
    </el-dialog>
      <LoginPop v-show="showDialog" @on-cancel="closeLoginPop" @on-suceess="LoginSuccess"></LoginPop>
  </div>
</template>

<script>
  import LoginPop from '../LoginPop.vue'
  export default {
    name: 'chatRoomIndexHeader',
    components: {
      LoginPop: LoginPop,
    },
    data () {
      return {
        dialogVisible:false,
        file:'',
        imageUrl: '',
        showDialog:false,
        isAdmin:'N',
        courseName:'',
        description:'',
        HdInfoData: {id: '1', name: '课程', descrption: '教学辅助网站论坛', icon: 'http://oss.yiqihappy.top/class.jpg'}
      }
    },
    watch: {
      HdInfoData: function (value) {}
    },
    methods: {
      handleClose(done) {
        this.dialogVisible=false;
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
      Add(){
        this.dialogVisible=true;
      },
      CourseAdd:function(){
        this.dialogVisible=false;
        var vm =this;
        var params = new FormData();
        params.append('courseName', this.courseName);
        params.append('file',this.file);
        params.append('description',this.description);
        console.log(this.file);
        this.$axios.post('/course/addCourse', params)
          .then(function (res) {
            if(res.data.code===200){
              alert("添加课程成功!");
              vm.$emit('refresh');
            }
          });
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
      // 发起讨论
      goDiscussion: function (articleId) {
        if (document.getElementById('pdLogin').value === 'true') {
          this.$router.push({name: 'SocialPost', params: {articleId: articleId}})
        } else {
          this.ShowLoginPop()
        }
      },

    },
    mounted: function () {
      var vm = this
      this.$axios.post('/getLoggedInfo')
        .then((successResponse) => {
          if (successResponse.data.code === 200) {
            var user = successResponse.data.data;
            document.getElementById('pdLogin').value = 'true'
            if (user.type === 0) {
              document.getElementById('isTeacher').value = 'Y'
              document.getElementById('isAdmin').value = 'Y'
            } else if (user.type === 1) {
              document.getElementById('isTeacher').value = 'Y'
              document.getElementById('isAdmin').value = 'N'
            } else {
              document.getElementById('isTeacher').value = 'N'
              document.getElementById('isAdmin').value = 'N'
            }
          } else {
            document.getElementById('pdLogin').value = 'false'
            document.getElementById('isTeacher').value = 'N'
            document.getElementById('isAdmin').value = 'N'
          }
          this.isAdmin =  document.getElementById('isAdmin').value;
        }).catch(failResponse => {
      })
    }
  }
</script>
<style scoped>
    @import '../../sass/stylesheets/SocialIndexHeader.css';
    .avatar-uploader .el-upload {
      border: 1px dashed #d9d9d9;
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
      border-color: #409EFF;
    }
    .avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 178px;
      height: 178px;
      line-height: 178px;
      text-align: center;
    }
    .avatar {
      width: 178px;
      height: 178px;
      display: block;
    }
</style>
