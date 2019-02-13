<template>
  <div class="SocialIndexHot bgWhite" id="SocialIndexHot">
    <div class="MoudleHd">
      在线用户
    </div>
    <div class="HotList clearfix">
      <ul>
        <li v-for="(list,temp) in users">
           <!--<router-link :to="{ name: 'post', params: {'articleId':list.id,'onPage':1}}" :title="list.label" :listId="list.id">-->
            <i class="fl"><img src="list.headImage"></i>
            <span class="fl" :title="list.nickname">{{list.nickname}}</span>
          <!--</router-link>-->
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: 'chatRoomUserList',
  data() {
    return {
      list: [],
      users: '',
    }
  },
  methods: {

    publishMessage() {

    }
  },
  created: function () {

  },
  mounted: function () {

  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
   @import '../../sass/stylesheets/SocialIndexHot.css';
</style>
