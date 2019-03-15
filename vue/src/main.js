// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import iView from 'iview';
import locale from 'iview/dist/locale/en-US';
import Moment from 'moment'


Vue.prototype.moment = Moment;
var axios = require('axios')
//本地配置
// axios.defaults.baseURL = 'http://localhost:8000'
//服务器配置
axios.defaults.baseURL = 'https://yiqihappy.top/teachingAssistantWebsite'
//docker
// axios.defaults.baseURL = 'http://120.79.213.75:8078/teachingAssistantWebsite'
// 将API方法绑定到全局
axios.defaults.withCredentials=true
import VideoPlayer from 'vue-video-player'
require('video.js/dist/video-js.css')
require('vue-video-player/src/custom-theme.css')
import VueParticles from 'vue-particles'
Vue.use(VideoPlayer)
Vue.use(VueParticles)

Vue.prototype.$axios = axios
Vue.config.productionTip = false
Vue.use(iView, { locale });
Vue.use(ElementUI);
window.eventBus = new Vue();
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
