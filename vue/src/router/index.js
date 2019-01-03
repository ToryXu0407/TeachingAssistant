import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/home'
import Login from '@/components/login'
import Header from '@/components/header'
import Register from '@/components/register'

Vue.use(Router)

export default new Router({
  routes: [
		{
			path:'/home',
			name:'home',
			component:Home
		},
		{
		path:'/login',
		name:'login',
		component:Login 
		},
		{
			path:'/',
			name:'header',
			component:Header
		},
		{
			path:'/register',
			name:'reigster',
			component:Register,
		}
  ]
})
