<template>
  <div class="HdInfo clearfix">
    <div class="SiH-Info fl" :data-QzId="HdInfoData.id">
        <img :src="HdInfoData.icon" class="SiH-Info-Pic fl"/>
        <div class="SiH-Info-name fl">
            <div class="Hname">
                <!-- <h5 class="fl" :title="HdInfoData.name" :to="{ name: 'circle', params: {'circleId':HdInfoData.id}}">{{HdInfoData.name}}</h5> -->
                <router-link class="fl h5" :to="{ name: 'circle', params: {'circleId':HdInfoData.id}}" :title="HdInfoData.name">{{HdInfoData.name}}</router-link>
            </div>
            <h4 class="Htitle" :title="HdInfoData.description">{{HdInfoData.description}}</h4>
        </div>
    </div>
    <div class="SiH-Href fr">
        <!-- <a :href="HdInfoData.shop_url" class="goShop fr">商店</a> -->
        <div class="SiH-Launch fr animation" @click="goDiscussion(HdInfoData.id)">
          <!-- <img src="../images/icon.png"/>发起讨论 -->
        </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'SocialIndexHeader',
    data () {
      return {
        HdInfoData: {id: '1', name: '讨论社', descrption: '教学辅助网站论坛', icon: 'http://oss.yiqihappy.top/aa.jpg'}
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
      goDiscussion: function (qzId) {
        if (document.getElementById('pdLogin').value === 'true') {
          this.$router.push({name: 'SocialPost', params: {circleId: qzId}})
        } else {
          this.ShowLoginPop()
        }
      },
      // Join quanzi
      JoinQz: function (qzId, temp) {
        if (document.getElementById('pdLogin').value === 'true') {
          let JoinUrl = '//moment.snail.com/api/v1/circle/join'
          this.JoinState(qzId, JoinUrl, 'join', temp)
        } else {
          this.ShowLoginPop()
        }
      },
      QuitQz: function (qzId, temp) {
        if (document.getElementById('pdLogin').value === 'true') {
          let QuitUrl = '//moment.snail.com/api/v1/circle/leave'
          this.JoinState(qzId, QuitUrl, 'quit', temp)
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
