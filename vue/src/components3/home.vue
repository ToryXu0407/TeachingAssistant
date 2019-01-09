<template>
  <div id="Index" class="Index">
    <div class="SocialIndexMain clearfix">
         <!-- 轮播 -->
          <Banner></Banner>
          <!-- 主体文章内容 -->
          <Article></Article>
    </div>
    <div class="left">
         右边广告位
    </div>
  </div>
</template>

<script>
import Banner from './banner'
import Article from './article'
export default {
  name:'home',
  components:{
    Banner,
    Article
  },
  data(){
    return  {
      sel:''
    }
  }
}
</script>

<style>
     #home-template{width:1280px;margin:0 auto;margin-top:50px;overflow: hidden;}
     .home-container{width:80%;box-sizing: border-box;}
     .left{float: left;}

</style>


