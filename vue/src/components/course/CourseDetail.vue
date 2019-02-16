<template>
  <div id="SocialIndex" class="SocialIndex">
    <div class="SocialIndexHeader bgWhite" id="SocialIndexHeader">
      <CourseDetailHeader :HdInfoData.sync="HdInfoData" :isSiH.sync="isSiH"  @refresh="refresh"></CourseDetailHeader>
    </div>
    <div class="SocialIndexMain clearfix">
      <div class="MainList fl bgWhite">
        <el-container style="height: 500px; border: 1px solid #eee">

          <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
            <el-menu :default-openeds="['1', '2']">
              <el-menu-item index="" @click.native="switchComp(0)">
                <template slot="title" ><i class="el-icon-menu"></i>课程通知</template>
              </el-menu-item>
              <el-menu-item index="" @click.native="switchComp(1)">
                <template slot="title" ><i class="el-icon-menu"></i>课件</template>
              </el-menu-item>
            </el-menu>
          </el-aside>

          <el-container>
            <el-header style="text-align: right;">
              <span style="font:18px large;font-family: Hiragino Sans GB;color: #409EFF;" >{{componentName}}</span>
              <br>
              <i v-show="!isStudent" style="font-size:30px" class="blue el-icon-circle-plus-outline"  @click="add(componentName)"></i>
            </el-header>
            <el-main>
              <component ref="sonComponent" v-bind:is="component" :courseId="courseId"></component>
            </el-main>
          </el-container>
        </el-container>
      </div>
      <div class="MainMoudle fr" id="MainMoudle">
        <PeoInfo ref="myPeoInfo"></PeoInfo>
        <CourseIndexHot>热门点击</CourseIndexHot>
        <span class="goTop cur" v-show="isGoTop" @click="goTop()"></span>
      </div>
    </div>
    <el-dialog
      title="添加课程通知"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <el-form>
      <el-form-item label="通知名">
        <el-input v-model="noticeInput" placeholder="请输入通知名"></el-input>
      </el-form-item>
      <el-form-item label="通知内容" >
        <el-input
          type="textarea"
          :rows="2"
          placeholder="请输入通知内容"
          v-model="noticeTextarea">
        </el-input>
      </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="addnotice()">添 加</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="添加课件"
      :visible.sync="dialogVisible2"
      width="30%"
      :before-close="handleClose2">
      <el-form>
        <el-form-item label="课件" >
          <el-upload
            class="upload-demo"
            action="#"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible2 = false">取 消</el-button>
    <el-button type="primary" @click="addCourseWare()">添 加</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
  import CourseDetailHeader from './CourseDetailHeader.vue'
  import CourseIndexHot from './CourseIndexHot.vue'
  import CourseNotice from './CourseNotice.vue'
  import Courseware from './Courseware.vue'
  import PeoInfo from '../PeoInfo.vue'
  export default {
    name: 'SocialIndex',
    components: {
      CourseDetailHeader: CourseDetailHeader,
      CourseIndexHot: CourseIndexHot,
      CourseNotice: CourseNotice,
      Courseware: Courseware,
      PeoInfo: PeoInfo

    },
    data () {
      return {
        HdInfoData: {},
        component:'CourseNotice',
        componentName:'课程通知',
        fileList:[],
        file:'',
        dialogVisible2:false,
        dialogVisible:false,
        courseWareInput:'',
        noticeInput:'',
        noticeTextarea:'',
        isStudent:true,
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
      handleAvatarSuccess(res, file) {
        // this.imageUrl = URL.createObjectURL(file.raw);
      },
      beforeAvatarUpload(file) {
        const isLt2M = file.size / 1024 / 1024 < 4;
        if (!isLt2M) {
          this.$message.error('上传文件大小不能超过 4MB!');
        }
        this.file = file;
        return isLt2M;
      },
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
        this.file = file;
      },
      handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${ file.name }？`);
      },
      handleClose(done) {
        this.dialogVisible=false;
      },
      handleClose2(done) {
        this.dialogVisible2=false;
      },
      add(ComponentName){
        if(ComponentName==='课程通知'){
          this.dialogVisible=true;
        }else{
          this.dialogVisible2=true;
        }
      },
      addnotice(){
        var vm = this;
        let params = new URLSearchParams();
        params.append('name', this.noticeInput);
        params.append('content', this.noticeTextarea);
        params.append('courseId', this.$route.params.courseId);
        this.$axios.post('/notice/addNotice',params)
          .then((res)=>{
            if (res.data.code === 200) {
              alert("添加课程通知成功！");
              vm.dialogVisible = false;
              vm.$emit('refresh');
              this.$refs.sonComponent.ShowHtml(1);
            }
          });
      },
      addCourseWare(){
        var vm = this;
        let params = new FormData();
        params.append('file', this.file);
        params.append('courseId', this.$route.params.courseId);
        this.$axios.post('/courseware/addCourseWare',params)
          .then((res)=>{
            if (res.data.code === 200) {
              alert("添加课件成功！");
              vm.dialogVisible2 = false;
              this.component = 'Courseware';
              this.componentName = '课件'
              this.$refs.sonComponent.ShowHtml(1);
            }
          });
      },
      switchComp(index){
        if(index===0){
          this.component = 'CourseNotice';
          this.componentName = '课程通知';
        }else if (index===1){
          this.component = 'Courseware';
          this.componentName = '课件'
        }
      },
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
      getHtml(){
        var vm = this;
        let params = new URLSearchParams();
        params.append('id', this.$route.params.courseId);
        this.$axios.post('/course/getCourseById',params)
          .then((res)=>{
            if (res.data.code === 200) {
              vm.HdInfoData = res.data.data
            }
          });
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
      const vm = this;
      this.courseId = this.$route.params.courseId
      window.addEventListener('scroll', this.handleScroll)
      let params = new URLSearchParams();
      params.append('id', this.$route.params.courseId);
      this.$axios.post('/course/getCourseById',params)
        .then((res)=>{
          if (res.data.code === 200) {
            vm.HdInfoData = res.data.data
          }
        });
      this.$axios.post('/getLoggedInfo')
        .then((successResponse)=>{
          this.responseResult = JSON.stringify(successResponse.data)
          if (successResponse.data.code === 200) {
            if(successResponse.data.data.type!=2){
              vm.isStudent = false;
            }else{
              vm.isStudent = true;
            }
          }
        }).catch(failResponse => {})
    }
  }
</script>

<style>
  .MainMoudle{
    width: 280px;
  }
</style>
