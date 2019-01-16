<template>
  <div>
    <base-header ref="myBaseHeader" @refresh="refresh"></base-header>
    <div id="app" class="app clearfix">
      <input type="hidden" id="pdLogin" value="sdd"/>
      <router-view :key="key" @refresh="refresh" v-if="isRouterAlive"></router-view>
      <base-footer></base-footer>
    </div>
  </div>

</template>

<script>
  import BaseFooter from './components/BaseFooter'
  import BaseHeader from './components/BaseHeader'
// import statistics from 'http://static.snail.com/js/stone/v2/statistics_ty_v2.source.js'
export default {
  name: 'app',
  data(){
    return {
      isRouterAlive:true,
    }
  },
  computed: {
    key () {
      return this.$route.query + new Date()
    }
  },
  components:{
    'base-header':BaseHeader,
    'base-footer':BaseFooter
  },
  methods:{
    refresh(){
      this.reload();
      this.$refs.myBaseHeader.getLoggedInfo();
    },
    reload(){
      this.isRouterAlive = false;
      this.$nextTick(function () {
        this.isRouterAlive = true
      });
    },
  },
  mounted () {
    document.getElementById('pdLogin').value = 'false'
    // const s = document.createElement('script')
    // s.type = 'text/javascript'
    // s.src = 'http://static.snail.com/js/stone/v2/statistics_ty_v2.source.js'
    // document.body.appendChild(s)
  },
  created: function () {
    // var vm = this
    // this.$axios.post('/getLoggedInfo')
    //   .then((successResponse)=>{
    //     if (successResponse.data.code === 200) {
    //       document.getElementById('pdLogin').value = 'true'
    //     }else{
    //       document.getElementById('pdLogin').value = 'false'
    //     }
    //   }).catch(failResponse => {})
  }
}
</script>

<style >
   @import './sass/stylesheets/SocialPublic.css'
</style>
