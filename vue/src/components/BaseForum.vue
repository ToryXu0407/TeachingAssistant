<template>
  <div class="SocialIndexList" id="SocialIndexList" style="width:400px">
    <a :href="'#/social'" style="font-size: 24px;font-weight: 600;">讨论社</a>
    <div class="IndexListCont">
      <ul>
        <li v-for="(list, i) in list" :data-listId="list.id" :name="list.id">
          <div class="Jitems">
            <div class="Jitems-Title">
              <router-link :to="{ name: 'post', params: {'articleId':list.id ,'onPage':0}}" :title="list.label" :listId="list.id">{{list.label}}</router-link>
              <i class="icon-good" v-if="list.isSticky=='Y'" >精</i>
            </div>
            <div class="Jitems-Info">
              <div class="JuserInfo fl">
                <a href="javascript:;" class="JuserInfo-people default" :title="list.nickname">
                  <img :src="list.headImage"/>
                  {{list.nickname}}
                </a>
                <span class="JuserInfo-time" :title="list.createTime">{{list.createTime}}</span>
              </div>
              <div class="JdataInfo fr">
                <span class="Jview"><img src="../images/icon4.png"/>{{list.viewCount}}</span>
                <span class="Jreply"><img src="../images/icon5.png"/>{{list.commentCount}}</span>
              </div>
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
    export default {
        name: "BaseForum",
      data () {
        return {
          list: [],
        }
      },
      created: function () {
        window.addEventListener('scroll', this.handleScroll)
        const vm = this
        let params = new URLSearchParams();
        params.append('page', 1);
        params.append('pagesize',10);
        params.append("order","hot");
        this.$axios.post('/article/getArticle',params)
          .then(function (res) {
            vm.list = res.data.data
          })
      }
    }
</script>

<style scoped>
  @import '../sass/stylesheets/SocialIndexList.css';

</style>
