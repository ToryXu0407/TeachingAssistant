import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/components/forum/Index'
import BaseIndex from '@/components/BaseIndex'
import SocialIndex from '@/components/forum/SocialIndex'
import SocialDetail from '@/components/forum/SocialDetail'
import SocialPost from '@/components/forum/SocialPost'
import Register from '@/components/Register'
import CourseIndex from '@/components/course/CourseIndex'
import CourseDetail from '@/components/course/CourseDetail'
import CourseNotice from '@/components/course/CourseNotice'
import Courseware from '@/components/course/Courseware'
Vue.use(Router)

export default new Router({
  routes: [

    {
      path: '/',
      name: 'index',
      component: BaseIndex,
    },
    {
      path: '/course',
      name: 'course',
      component: CourseIndex,
    },
    {
      path: '/register',
      name: 'register',
      component: Register,
    },
    {
      path: '/social',
      name: 'social',
      component: SocialIndex,
    },
    {
      path: '/post/:articleId/:onPage',
      name: 'post',
      component: SocialDetail,
    },
    {
      path: '/courseDetail/:courseId/:onPage',
      name: 'courseDetail',
      component: CourseDetail,
      children: [
        {path: 'notice', name: '课程通知', component: CourseNotice, },
        {path: 'courseware', name: '课件', component: Courseware, },
      ]
    },
    {
      path: '/SocialPost/:articleId',
      name: 'SocialPost',
      component: SocialPost,
    },


  ]
})
