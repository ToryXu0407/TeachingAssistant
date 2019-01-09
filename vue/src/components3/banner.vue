<template>
     <div id="banner">
         <slider :pages="pageslist" :sliderinit="sliderinits">
            <!-- <slot>12312</slot>     -->
        </slider>
     </div>
</template>

<script>
import  slider from 'vue-concise-slider'
export default {
  name:'banner',
  data(){
    return{
          //图片列表[arr]
        pageslist:[
          {
            title: '1123123123',
            style:{
                width:300,
                background:'url(../img/xia.png)'
            }
          },
          {
           title: '21231231231231',
           style:{
               width:300,
               background:'url(../img/xia.png)'
            }
          },
          {
            title: 'slide3',
            style:{
              background:'#4bbfc3',
            },
          }
        ],
          //滑动配置[obj]
        sliderinits: {
          currentPage: 1,//当前页码ssss
          thresholdDistance: 300,//滑动判定距离
          thresholdTime: 100,//滑动判定时间
          autoplay:1000,//自动滚动[ms]
          loop:true,//循环滚动
        //   direction:'horizontal',//方向设置，垂直滚动
          infinite:0,//无限滚动前后遍历数
          slidesToScroll:1,//每次滑动项数
        }
    }
  },
   components: {
        slider
    }
}
</script>

<style>
   #banner{
       width:100%;
       height:300px;
   }
</style>

