<template>
  <div id="home-template">
   <!-- <h2>home</h2> -->
   <!-- <router-link to="/login">222</router-link> -->
    <div class="home-container left">
         <!-- 轮播 -->
          <Banner></Banner>
          <!-- 主体文章内容 -->
          <Artile></Artile>
    </div> 
    <div class="left">
         右边广告位
    </div>
  </div>
</template>

<script>
import Banner from './banner'
import Artile from './artile'
export default {
  name:'home',
  components:{
    Banner,
    Artile
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


