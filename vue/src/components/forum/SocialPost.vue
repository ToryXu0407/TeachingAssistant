<template>
  <div id="SocialPost" class="SocialPost">
    <h5 class="SP-Hd">发起讨论</h5>
    <div class="SP-Main">
      <div class="SPM-Title">
        <div class="SPMT-Input fl">
          <input type="text" placeholder="请输入标题(～￣▽￣)～ " class="bgWhite" v-model="items.text" ref="count"/>
          <span>还可以输入<b>{{num}}</b>个字符</span>
        </div>

      </div>
      <div class="SPM-Editor">
        <div id="editorElem" style="text-align:left"></div>
      </div>
      <div class="SPM-Operation">
         <!--  <span class="wordnum fl">还可以输入999字符</span> -->
        <span class="PostHtml cur fr animation" id="PostHtml">发表</span>
        <span class="errts fr" id="errts" v-show="errts">{{errtsText}}</span>
      </div>
    </div>
    <div class="divmask" v-show="isDivmask" @click="closeDrown()"></div>
    <div class="divmask" v-show="isTops" @click="clsowisTops()"></div>
  </div>
</template>
<script>
  import E from 'wangeditor'
  export default {
    name: 'SocialPost',
    components: {
    },
    watch: {
      items: {
        handler: function () {
          var _this = this
          var _sum = 30
          _this.$refs.count.setAttribute('maxlength', _sum)
          _this.number = _sum - _this.$refs.count.value.length
          _this.num = _sum - _this.$refs.count.value.length
          _this.title = _this.$refs.count.value
          document.getElementById('errts').style.display = 'none'
        },
        deep: true
      }
    },
    data () {
      return {
        tags: [],
        topics: [],
        articleId: '',
        title: '',
        content: '',
        tag: '',
        validate_code: '',
        SelectInput: '话题分类',
        isSelect: false,
        tagId: '',
        tagName: '',
        items: {
          text: ''
        },
        num: 30,
        errtsText: '',
        errts: false,
        isDivmask: false,
        isTops: false,
        isTopsList: true,
        classItem: [],
        topicsId: [],
        topicsList: [],
        topicNum: 5
      }
    },
    methods: {
      getContent: function (text) { // 发送评论
        var _this = this
        document.getElementsByClassName('w-e-text')[0].blur()
        if (this.title !== '' && text !== '') {
          document.getElementById('errts').style.display = 'none'
          // this.postHtml(this.articleId, this.title, this.editorContent, this.tagId)
          _this.postHtml(this.articleId, this.title, text)
        } else {
          document.getElementById('errts').style.display = 'inline-block'
          document.getElementById('errts').innerHTML = '请填写相关选项'
        }
      },
      postHtml: function (articleId, title, text) {
        var vm = this
        let params = new URLSearchParams();
        params.append('content', text);
        params.append('label', title);
        this.$axios.post('/article/updateArticle',params)
          .then((successResponse)=>{
            if (successResponse.data.code === 200) {
              //登陆成功
              console.log("发帖成功");
              this.$router.push({name: 'social'})
            }
          }).catch(failResponse => {})
      }
    },
    created: function () {
      this.$emit('hidefooter');

    },
    mounted () {
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
      editor.customConfig.uploadImgServer = '//moment.snail.com/api/v1/upload/image'  // 上传图片到服务器
      editor.customConfig.uploadImgMaxSize = 2 * 1024 * 1024
      editor.customConfig.uploadImgMaxLength = 50
      editor.customConfig.withCredentials = true
      editor.customConfig.uploadImgParams = {
        from: 'post'   // 属性值会自动进行 encode ，此处无需 encode
      }
      editor.customConfig.uploadFileName = 'file'
      // editor.customConfig.uploadFileName = 'yourFileName'
      editor.customConfig.uploadImgHeaders = {
        'Accept': 'text/x-json'
      }
      editor.customConfig.customAlert = function (info) {
        // info 是需要提示的内容
        document.getElementById('errts').style.display = 'inline-block'
        document.getElementById('errts').innerHTML = '图片过大，请上传小于2M的图片'
      }
      editor.customConfig.uploadImgHooks = {
        before: function (xhr, editor, files) {
          // 图片上传之前触发
          // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，files 是选择的图片文件
          // 如果返回的结果是 {prevent: true, msg: 'xxxx'} 则表示用户放弃上传
          // return {
          //     prevent: true,
          //     msg: '放弃上传'
          // }
        },
        success: function (xhr, editor, result) {
          // 图片上传并返回结果，图片插入成功之后触发
          // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
        },
        fail: function (xhr, editor, result) {
          document.getElementById('errts').style.display = 'inline-block'
          document.getElementById('errts').innerHTML = '图片插入错误，请重新选择图片'
          // 图片上传并返回结果，但图片插入错误时触发
          // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
        },
        error: function (xhr, editor) {
          // 图片上传出错时触发
          document.getElementById('errts').style.display = 'inline-block'
          document.getElementById('errts').innerHTML = '图片上传错误，请重新选择图片'
          // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象
        },
        timeout: function (xhr, editor) {
          // 图片上传超时时触发
          // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象
        },
          // 如果服务器端返回的不是 {errno:0, data: [...]} 这种格式，可使用该配置
          // （但是，服务器端返回的必须是一个 JSON 格式字符串！！！否则会报错）
        customInsert: function (insertImg, result, editor) {
          // 图片上传并返回结果，自定义插入图片的事件（而不是编辑器自动插入图片！！！）
          // insertImg 是插入图片的函数，editor 是编辑器对象，result 是服务器端返回的结果
          // 举例：假如上传图片成功后，服务器端返回的是 {url:'....'} 这种格式，即可这样插入图片：
          if (result.code === 200) {
            var url = result.url
            insertImg(url)
            document.getElementById('errts').style.display = 'none'
          } else {
            document.getElementById('errts').style.display = 'inline-block'
            document.getElementById('errts').innerHTML = result.message
          }
          // result 必须是一个 JSON 格式字符串！！！否则报错
        }
      }
      editor.customConfig.linkImgCallback = function (url) {
        console.log(url, 'url') // url 即插入图片的地址
      }
      editor.create()
      // editor.txt.html('<p style="font-size:12px;color:#aaa;">请输入内容(～￣▽￣)～</p>')
      var _this = this
      document.getElementById('PostHtml').addEventListener('click', function () {
        // this.editorContent = editor.txt.html()
        _this.getContent(editor.txt.html())
      })
    }
  }
</script>

<style>
  @import '../../sass/stylesheets/SocialPost.css'
</style>
