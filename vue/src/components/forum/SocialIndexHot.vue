<template>
  <div class="SocialIndexHot bgWhite" id="SocialIndexHot">
    <div class="MoudleHd">
      热门课程
    </div>
    <div class="HotList clearfix">
      <ul>
        <li v-for="(list,temp) in list">
           <router-link :to="{ name: 'post', params: {'articleId':list.id,'onPage':1}}" :title="list.label" :listId="list.id">
            <i class="fl">{{temp+1}}</i>
            <span class="fl" :title="list.label">{{list.label}}</span>
            <b class="fr">{{list.commentCount}}</b>
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
    params.append('order',"hot");
    this.$axios.post('/article/getArticle', params)
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
