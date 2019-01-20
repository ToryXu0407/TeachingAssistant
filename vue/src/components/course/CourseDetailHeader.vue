<template>
  <div class="HdInfo clearfix">
    <div class="SiH-Info fl" :data-QzId="HdInfoData.id">
        <img :src="HdInfoData.image" class="SiH-Info-Pic fl"/>
        <div class="SiH-Info-name fl">
            <div class="Hname">
                <!-- <h5 class="fl" :title="HdInfoData.name" :to="{ name: 'circle', params: {'circleId':HdInfoData.id}}">{{HdInfoData.name}}</h5> -->
                <div class="fl h5">{{HdInfoData.name}}</div>
            </div>
            <h4 class="Htitle" :title="HdInfoData.description">{{HdInfoData.description}}</h4>
        </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'CourseDetailHeader',
    components: {
    },
    props:{
      HdInfoData:'',
    },
    data () {
      return {
        showDialog:false,
      }
    },
    watch: {
      HdInfoData: function (value) {}
    },
    methods: {
      ShowLoginPop: function () {
        this.showDialog = true;
      },
      closeLoginPop: function () {
        this.showDialog = false;
      },
      LoginSuccess: function () {
        this.showDialog = false;
        this.$emit('refresh');
      },

    },
    created: function () {
    }
  }
</script>
<style scoped>
    @import '../../sass/stylesheets/SocialIndexHeader.css';

</style>
