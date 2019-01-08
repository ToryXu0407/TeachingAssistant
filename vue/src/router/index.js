import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/home'
import Login from '@/components/login'
import Header from '@/components/navheader'
import Register from '@/components/register'
import ArticleIndex from '@/components/articleIndex'
import Base from '@/components/base'

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
      path:'/articleIndex',
      name:'articleIndex',
      component:ArticleIndex
    },
		{
			path:'/',
			name:'base',
			component:Base
		},
		{
			path:'/register',
			name:'reigster',
			component:Register,
		}
  ]
})
