<template>
  <div>
    <div v-for="(notice,i) in notices" :key="i">
    <el-card class="box-card">
      <div slot="header" class="clearfix" >
        <span>{{notice.name}}</span>
      </div>
      <div class="text item"v-html="notice.content">
      </div>
        <time class="time">{{ currentDate }}</time>
    </el-card>
    </div>
    <pagination :cur="cur" :all="all"  :isJump="isJump" @listen="monitor" ref="page"></pagination>
  </div>


</template>

<script>
  import pagination from '../pagination'
    export default {
      name: "CourseNotice",
      components:{
        pagination:pagination
      },
      data() {
        return {
          cur: 1,
          all: 1,
          isJump: true,
          currentDate: new Date(),
          notices: ''
        };
      },
      methods:{
        monitor: function (data) { // 分页监听
          this.ShowHtml(this.order, data)
          this.goTop()
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
        ShowHtml(order,page){
          const vm = this;
          let params = new URLSearchParams();
          params.append('id', this.$route.params.courseId);
          params.append("page",page);
          params.append("pagesize",5);
          this.$axios.post('/notice/getNoticesByCourseId',params)
            .then((res)=>{
              if (res.data.code === 200) {
                vm.notices = res.data.data
                vm.all = res.data.totalPage
              }
            })
        },
      },
      created: function () {
        const vm = this;
        let params = new URLSearchParams();
        params.append('id', this.$route.params.courseId);
        params.append("pagesize",5);
        this.$axios.post('/notice/getNoticesByCourseId',params)
          .then((res)=>{
            if (res.data.code === 200) {
              vm.notices = res.data.data
              vm.all = res.data.totalPage
            }
          })
      },
      mounted: function () {
        const s = document.createElement('script')
        s.type = 'text/javascript'
        s.src = '//static.snail.com/js/stone/v2/statistics_ty_v2.source.js'
        document.body.appendChild(s)
        var editor = new E('#editorElem')
        // editor.customConfig.onchange = (html) => {
        //   this.editorContent = html
        // }
        editor.customConfig.menus = [
          // 'head',  // 标题
          'bold',  // 粗体
          'italic',  // 斜体
          'underline',  // 下划线
          // 'strikeThrough',  // 删除线
          // 'foreColor',  // 文字颜色
          // 'backColor',  // 背景颜色
          'link',  // 插入链接
          // 'list',  // 列表
          // 'justify',  // 对齐方式
          // 'quote',  // 引用
          'emoticon',  // 表情
          'image' // 插入图片
          // 'table',  // 表格
          // 'video',  // 插入视频
          // 'code',  // 插入代码
          // 'undo',  // 撤销
          // 'redo'  // 重复
        ]
        // editor.customConfig.uploadImgShowBase64 = true   // 使用 base64 保存图片
        editor.customConfig.pasteFilterStyle = true
        editor.customConfig.uploadImgServer = '//moment.snail.com/api/v1/upload/image'  // 上传图片到服务器
        editor.customConfig.uploadImgMaxSize = 2 * 1024 * 1024
        editor.customConfig.uploadImgMaxLength = 50
        editor.customConfig.withCredentials = true
        editor.customConfig.uploadImgParams = {
          from: 'comment'   // 属性值会自动进行 encode ，此处无需 encode
        }
        editor.customConfig.uploadFileName = 'file'
        editor.customConfig.uploadImgHeaders = {
          'Accept': 'text/x-json'
        }
        editor.customConfig.customAlert = function (info) {
          // info 是需要提示的内容
          // alert('info')
          document.getElementById('postErrts').style.display = 'inline-block'
          document.getElementById('postErrts').innerHTML = '图片过大，请上传小于2M的图片'
          // console.log(info)
        }
        editor.customConfig.uploadImgHooks = {
          fail: function (xhr, editor, result) {
            // 图片上传并返回结果，但图片插入错误时触发
            // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
            document.getElementById('postErrts').style.display = 'inline-block'
            document.getElementById('postErrts').innerHTML = '图片插入错误，请重新选择图片'
          },
          error: function (xhr, editor) {
            // 图片上传出错时触发
            // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象
            document.getElementById('postErrts').style.display = 'inline-block'
            document.getElementById('postErrts').innerHTML = '图片上传错误，请重新选择图片'
          },
          customInsert: function (insertImg, result, editor) {
            // 图片上传并返回结果，自定义插入图片的事件（而不是编辑器自动插入图片！！！）
            // insertImg 是插入图片的函数，editor 是编辑器对象，result 是服务器端返回的结果
            // 举例：假如上传图片成功后，服务器端返回的是 {url:'....'} 这种格式，即可这样插入图片：
            if (result.code === 200) {
              document.getElementById('postErrts').style.display = 'none'
              var url = result.url
              insertImg(url)
            } else {
              document.getElementById('postErrts').style.display = 'inline-block'
              document.getElementById('postErrts').innerHTML = result.message
            }
            // console.log(result)
            // result 必须是一个 JSON 格式字符串！！！否则报错
          }
        }
        editor.customConfig.linkImgCallback = function (url) {
          // console.log(url) // url 即插入图片的地址
        }
        editor.create()
        // editor.txt.html('<p style="font-size:12px;color:#aaa;">请输入内容(～￣▽￣)～</p>')
        var _this = this
        document.getElementById('PostHtml').addEventListener('click', function () {
          this.editorContent = editor.txt.html()
          console.log(this.editorContent)
          _this.getContent(editor.txt.html())
          // console.log('1')
        })
      }
    }
</script>

<style scoped>
  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 450px;
    margin-bottom: 20px;
  }

</style>
