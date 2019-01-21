<template>
  <div class="SocialIndexHot bgWhite" id="SocialIndexHot">
    <div class="MoudleHd">
      <slot></slot>
    </div>
    <div class="HotList clearfix">
      <ul>
        <li v-for="(list,temp) in list">
           <router-link :to="{ name: 'courseDetail', params: {'courseId':list.id,'onPage':1}}" :title="list.name" :listId="list.id">
            <i class="fl">{{temp+1}}</i>
            <span class="fl" :title="list.name">{{list.name}}</span>
            <b class="fr">{{list.viewCount}}</b>
          </router-link>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SocialIndexHot',
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
    this.$axios.post('/course/getCourse', params)
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
