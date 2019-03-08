<template>
  <div class="SocialIndexHot bgWhite" id="SocialIndexHot">
    <div class="MoudleHd">
      热门聊天
    </div>
    <div class="HotList clearfix">
      <ul>
        <li v-for="(list,temp) in list">
          <!--<router-link :to="{ name: 'chatRoomDetail', params: {'chatRoomId':list.id }}" :title="list.courseName" :listId="list.id">-->
            <a href="#" @click="initializeWebRtc(list.id,list.teacherId)">
          <i class="fl">{{temp+1}}</i>
            <span class="fl" :title="list.label">{{list.courseName}}</span>
            <b class="fr">{{list.chatsCount}}</b>
            </a>
          <!--</router-link>-->
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import SimpleWebRTC from 'simplewebrtc'
export default {
  name: 'chatRoomIndexHot',
  data () {
    return {
      list: [ ],
      userId:'',
    }
  },
  methods:{
    initializeWebRtc(chatRoomId,teacherId){
      var userid = this.userId;
      if (userid === teacherId && userid !== '') {
        console.log("老师老师");
        window.webrtc = new SimpleWebRTC({
          localVideoEl: '',
          remoteVideosEl: '',
          autoRequestMedia: true,
          nick: userid
        })
      } else {
        window.webrtc = new SimpleWebRTC({
          localVideoEl: '',
          remoteVideosEl: '',
          autoRequestMedia: true,
          media: {
            video: false,
            audio: false
          },
          nick: userid
        })
      }
      this.$router.push({name:"chatRoomDetail2",params:{chatRoomId:chatRoomId}});
    },
  },
  created: function () {
    const vm = this
    let params = new URLSearchParams();
    params.append('page', 1);
    params.append('pagesize', 10);
    params.append('order',"hot");
    this.$axios.post('/chat/getChatRooms', params)
      .then(function (res) {
        vm.list = res.data.data
      })
  },
  mounted:function () {
    var vm = this
    this.$axios.post('/getLoggedInfo')
      .then((successResponse) => {
        if (successResponse.data.code === 200) {
          var user = successResponse.data.data;
          vm.userId = user.userid;
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

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
   @import '../../sass/stylesheets/SocialIndexHot.css';
</style>
