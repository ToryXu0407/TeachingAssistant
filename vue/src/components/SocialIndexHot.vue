<template>
  <div class="SocialIndexHot bgWhite" id="SocialIndexHot">
    <div class="MoudleHd">
      热门讨论
    </div>
    <div class="HotList clearfix">
      <ul>
        <li v-for="(list,temp) in list">
           <router-link :to="{ name: 'post', params: {'circleId':list.article_id}}" :title="list.label" :listId="list.articleId" wn_tj_click_gameId wn_tj_click_href wn_tj_click_excel="hot_posts" :wn_tj_click_id="list.articleId">
            <i class="fl">{{temp+1}}</i>
            <span class="fl" :title="list.label">{{list.label}}</span>
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
    params.append('page', 0);
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
   @import '../sass/stylesheets/SocialIndexHot.css';
</style>
