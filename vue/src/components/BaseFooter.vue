<template>
  <div>
    <hr style="height:1px;border:none;border-top:1px solid #CCCCCC;" />
    <el-footer class="me-area">
      <div class="me-footer">
        <p>Designed by
          <strong>
            <router-link to="/" class="me-login-design-color">ToryXu</router-link>
          </strong>
        </p>
      </div>
    </el-footer>
  </div>

</template>

<script>

  export default {
    name: 'BaseFooter',
    data() {
      return {}
    },
    methods: {},
    mounted() {
    }
  }
</script>

<style>

  .me-area {
    width: 100%;
    bottom: 0;
    left: 0;
    z-index: 1024;
    height:40px;
  }

  .me-footer {
    text-align: center;
    line-height: 60px;
    font-family: 'Open Sans', sans-serif;
    font-size: 18px;
  }

  .me-login-design-color {
    color: #5FB878 !important;
  }

</style>
