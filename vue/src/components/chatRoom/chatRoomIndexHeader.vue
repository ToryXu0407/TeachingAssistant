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
    <div class="SiH-Href fr" v-if="isTeacher==='Y'" >
      <div v-bind:id="HdInfoData.id" class="SiH-Launch2 fr animation" @click="ChatRoomAdd(HdInfoData.id)">
        创建聊天室
    </div>
    </div>
  </div>
</template>

<script>
  import chatRoomAdd from './chatRoomAdd.vue'
  export default {
    name: 'chatRoomIndexHeader',
    components: {
      chatRoomAdd: chatRoomAdd,
    },
    data () {
      return {
        showDialog:false,
        isTeacher:'N',
        HdInfoData: {id: '1', name: '答疑聊天室', descrption: '教学辅助网站论坛', icon: 'http://oss.yiqihappy.top/aa.jpg'}
      }
    },
    watch: {
      HdInfoData: function (value) {}
    },
    methods: {
      ChatRoomAdd: function (chatRoomId) {
        // 发起讨论
          if (document.getElementById('pdLogin').value === 'true') {
            this.$router.push({name: 'ChatRoomAdd'})
          } else {
            this.ShowLoginPop()
          }
        },
    },
    mounted: function () {
      this.isTeacher =  document.getElementById('isTeacher').value;
    }
  }
</script>
<style scoped>
    @import '../../sass/stylesheets/SocialIndexHeader.css';

</style>
