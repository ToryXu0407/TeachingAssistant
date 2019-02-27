// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import iView from 'iview';
import locale from 'iview/dist/locale/en-US';
import Moment from 'moment'


Vue.prototype.moment = Moment;
var axios = require('axios')
//本地配置
axios.defaults.baseURL = 'http://localhost:8000'
//服务器配置
// axios.defaults.baseURL = 'http://120.79.213.75:8080/teachingAssistantWebsite'
//docker
// axios.defaults.baseURL = 'http://120.79.213.75:8078/teachingAssistantWebsite'
// 将API方法绑定到全局
axios.defaults.withCredentials=true

var qiniu = require('qiniu-js')
Vue.prototype.$axios = axios
Vue.config.productionTip = false
Vue.use(iView, { locale });
Vue.use(ElementUI);
window.eventBus = new Vue();
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
