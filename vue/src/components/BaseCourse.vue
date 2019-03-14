<template>
  <div class="SocialIndexList" id="SocialIndexList" style="width:460px;">
    <a :href="'#/course'" style="font-size: 24px;font-weight: 600;">课程</a>
    <div class="IndexListCont">
      <ul>
        <li v-for="(list, i) in list" :data-listId="list.id" :name="list.id">
          <div class="Jitems">
            <div class="Jitems-Title">
              <router-link :to="{ name: 'courseDetail', params: {'courseId':list.id ,'onPage':0}}" :title="list.name" :listId="list.id">{{list.name}}</router-link>
            </div>
            <div class="Jitems-Info">
              <div class="JuserInfo fl">
                <a href="javascript:;" class="JuserInfo-people default" :title="list.name">
                  <img :src="list.image"/>
                </a>
              </div>
              <div class="JdataInfo fr">
                <span class="Jview"><img src="../images/icon4.png"/>{{list.viewCount}}</span>
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
        name: "BaseCourse",
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
        params.append('pagesize',5);
        this.$axios.post('/course/getCourse',params)
          .then(function (res) {
            vm.list = res.data.data
          })
      }
    }
</script>

<style scoped>
  @import '../sass/stylesheets/SocialIndexList.css';
</style>
