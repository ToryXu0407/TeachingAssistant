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
        showDialog:false,
        HdInfoData: {id: '1', name: '答疑聊天室', descrption: '教学辅助网站论坛', icon: 'http://oss.yiqihappy.top/aa.jpg'}
      }
    },
    watch: {
      HdInfoData: function (value) {}
    },
    methods: {
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
    created: function () {

    }
  }
</script>
<style scoped>
    @import '../../sass/stylesheets/SocialIndexHeader.css';

</style>
