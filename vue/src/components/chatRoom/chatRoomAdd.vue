<template>
  <div id="SocialPost" class="SocialPost">
    <h5 class="SP-Hd">新建聊天室</h5>
    <div class="SP-Main">
      <div class="SPM-Title">
        <div class="SPMT-Input fl">
          <input type="text" placeholder="请输入课程名（或要讨论的内容）(～￣▽￣)～ " class="bgWhite" v-model="items.text" ref="count"/>
          <span>还可以输入<b>{{num}}</b>个字符</span>
        </div>
      </div>
      <div class="block">
        <span class="demonstration">聊天室开启时间区间：</span>
        <el-date-picker
          v-model="time"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
      </div>
      <div class="SPM-Operation">
         <!--  <span class="wordnum fl">还可以输入999字符</span> -->
        <span class="PostHtml cur fr animation" id="PostHtml" @click=postHtml()>创建</span>
      </div>
    </div>
  </div>
</template>
<script>
  export default {
    name: 'SocialPost',
    components: {
    },
    watch: {
    },
    data () {
      return {
        items: {
          text: ''
        },
        num: 30,
        time:'',
      }
    },
    methods: {
      postHtml: function () {
        var vm = this
        let params = new URLSearchParams();
        params.append('content', this.items.text);
        params.append('startTime',this.moment(this.time[0]).format("YYYY-MM-DD HH:mm:ss"));
        params.append('endTime',this.moment(this.time[1]).format("YYYY-MM-DD HH:mm:ss"));
        console.log(this.items.text);
        this.$axios.post('/chat/addChatRoom',params)
          .then((successResponse)=>{
            if (successResponse.data.code === 200) {
              //登陆成功
              alert("创建聊天室成功!");
              this.$router.push({name: 'chatroom'})
            }
          }).catch(failResponse => {})
      }
    },
    created: function () {
      this.$emit('hidefooter');
    },
    mounted:function () {
    }

  }
</script>

<style>
  @import '../../sass/stylesheets/SocialPost.css'
</style>
