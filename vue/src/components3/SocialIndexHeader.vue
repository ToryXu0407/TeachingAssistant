<template>
  <div class="SocialIndexHeader bgWhite" id="SocialIndexHeader">
  <div class="HdInfo clearfix">
    <div class="SiH-Info fl">
      <img :src="HdInfoData.icon" class="SiH-Info-Pic fl"/>
      <div class="SiH-Info-name fl">
        <div class="Hname">
          {{HdInfoData.name}}
        </div>
        <h4 class="Htitle" :title="HdInfoData.description">{{HdInfoData.description}}</h4>
      </div>
    </div>
    <div class="SiH-Href fr">
      <div class="SiH-Launch fr animation" @click="goDiscussion()">
        <!--<img src="../images/icon.png"/>发起讨论-->
      </div>
    </div>
  </div>
  </div>
</template>

<script>
  export default {
    name: 'SocialIndexHeader',
    data () {
      return {
        HdInfoData:{icon: 'http://oss.yiqihappy.top/book.png',name:'普通论坛',description:'城院教学辅助网站'}
      }
    },
    watch: {
      HdInfoData: function (value) {}
    },
    methods: {
      ShowLoginPop: function () {
        document.getElementById('login_pop').style.display = 'block'
        document.getElementById('stone_mask').style.display = 'block'
      },
      dataDetails: function () {
        this.joinText = '退出'
      },
      // 发起讨论
      goDiscussion: function () {
        if (document.getElementById('pdLogin').value === 'true') {
          this.$router.push({name: 'SocialPost'})
        } else {
          this.ShowLoginPop()
        }
      },
      JoinState: function (qzId, url, flag) {
        const vm = this
        vm.$http({
          url: url,
          method: 'jsonp',
          params: {'circle_id': qzId},
          jsonp: 'callback',
          emulateJSON: true,
          headers: {
            'Content-Type': 'x-www-from-urlencoded'
          }
        }).then(function (res) {
          if (flag === 'join') {
            this.HdInfoData.is_joined = true
          } else if (flag === 'quit') {
            this.HdInfoData.is_joined = false
          }
        })
      }
    },
    created: function () {}
  }
</script>
<style scoped>
    @import '../sass/stylesheets/SocialIndexHeader.css';
</style>
