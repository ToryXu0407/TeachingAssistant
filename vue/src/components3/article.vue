<template>
  <div>
  <button v-on:click="articleIndex">查看所有帖子</button>
    <div class="IndexList" id="IndexList">
      <div class="IndexListType clearfix" id="IndexListType">
        <div class="TypeTag fl">
          <ul>
            <li>
              热门动态
            </li>
          </ul>
        </div>
      </div>
      <div class="IndexListCont">
        <ul>
          <li v-for="(list, i) in list" :data-listId="list.articleId">
            <div class="Jitems">
              <!--<div class="Jitems-Name">-->
                <!--<img :src="list.headImage" class="qzicon fl"/>-->
                <!--<router-link class ="Qzname fl" :to="{ name: 'circle', params: {'circleId':list.articleId}}" :title="list.circle_name" :circleId="list.articleId">{{list.username}}</router-link>-->
              <!--</div>-->
              <div class="Jitems-Title">
                <router-link :to="{ name: 'post', params: {'circleId':list.articleId,'postId': list.id,'onPage':1}}" :title="list.title" :listId="list.id" wn_tj_click_href wn_tj_click_gameId wn_tj_click_excel="news_headlines" :wn_tj_click_id="list.id">{{list.label}}</router-link>
              </div>
              <div class="Jitems-Detail">
                <h5 class="Jitems-Detail-text" v-html="list.brief"></h5>
                <div class="Jitems-Detail-img clearfix">
                  <div class="JDICont">
                    <div class="imgList" v-for="(img, index) in list.images" @click="ShowBigPic($event,index,i)">
                      <div class="imgfd"></div>
                      <img :src="img + '?x-oss-process=image/resize,h_110'" alt="点击加载图片"/>
                    </div>
                  </div>
                  <div class="Jlist-thumb clearfix" v-if="list.images != ''" v-show="list.flag">
                    <div class="thumbHd"><a href="javascript:;" class="j_retract" @click="hidePic($event,i)"><i class="icon_retract"></i>收起</a> </div>
                    <div class="thumbWarp">
                      <div :class="['thumbWarpList',{none: list.showindex != index }]" v-for="(img, index) in list.images" >
                        <div class="thumbPrev" v-if="index != 0" :data-cur="index-1" @click="thumbPrev(index,i)"></div>
                        <div class="thumbsx" @click="hidePic2($event,i)"></div>
                        <img :src="img" alt="刷新查看" />
                        <div class="thumbNext" v-if="list.images.length-1 != index" :data-cur="index+1" @click="thumbNext(index,i)"></div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="Jitems-Info">
                <div class="JuserInfo fl">
                  <a href="javascript:;" class="JuserInfo-people" :title="list.username">
                    {{list.username}}
                  </a>
                  <span class="JuserInfo-time" :title="list.createTime">{{list.createTime}}</span>
                </div>
                <router-link class="JdataInfo fr" :to="{ name: 'post', params: {'circleId':list.articleId,'postId': list.id}}">
                  <span class="Jview"><img src="../images/icon4.png"/>{{list.view_count}}</span>
                  <span class="Jreply"><img src="../images/icon5.png"/>{{list.comment_count}}</span>
                </router-link>
              </div>
            </div>
          </li>
        </ul>
      </div>
      <div class="selectMask" v-show="isSelectMask" @click="closeSelect()"></div>
    </div>
  </div>
</template>

<script>
  import pagination from './pagination'
export default {
  name:'article',
  components: {
    pagination: pagination,
  },
  data(){
    return {
      list: '',
      circleId: '',
      cur: 1,
      all: 1,
      pageSize: 10,
      showFlag: -1,
      tumbCurrent: 0,
      show: false,
      scrolled: false,
      isJump: true,
      isSelectMask: false,
      drownReflushText: true,
      drownReflush: true,
      load: false
    }
  },
	methods: {
    articleIndex(){
      this.$router.push({
        path:'/socialIndex',
        query:{
        }})
    }
	},
  created: function () {
    //this.circleId = this.$route.params.circleId
    window.addEventListener('scroll', this.handleScroll)
    const vm = this
    let params = new URLSearchParams();
    params.append('page', 0);
    this.$axios.post('/article/getArticle',params)
      .then(function (res) {
        vm.list = res.data.data
        console.log(vm.list)
        vm.all = res.data.totalPage
        if (res.data.totalPage === 0 || res.data.totalPage < 2) {
          vm.all = 1
          vm.drownReflush = false
          vm.drownReflushText = false
        }
      })
  }
}
</script>

<style>
  @import '../sass/stylesheets/IndexList.css';
</style>
