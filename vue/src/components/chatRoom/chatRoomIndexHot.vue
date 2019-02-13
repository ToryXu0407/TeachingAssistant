<template>
  <div class="SocialIndexHot bgWhite" id="SocialIndexHot">
    <div class="MoudleHd">
      热门聊天
    </div>
    <div class="HotList clearfix">
      <ul>
        <li v-for="(list,temp) in list">
          <router-link :to="{ name: 'chatRoomDetail', params: {'chatRoomId':list.id }}" :title="list.courseName" :listId="list.id">
          <i class="fl">{{temp+1}}</i>
            <span class="fl" :title="list.label">{{list.courseName}}</span>
            <b class="fr">{{list.chatsCount}}</b>
          </router-link>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: 'chatRoomIndexHot',
  data () {
    return {
      list: [ ]
    }
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
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
   @import '../../sass/stylesheets/SocialIndexHot.css';
</style>
