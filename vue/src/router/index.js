import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/components/forum/Index'
import BaseIndex from '@/components/BaseIndex'
import SocialIndex from '@/components/forum/SocialIndex'
import SocialDetail from '@/components/forum/SocialDetail'
import SocialPost from '@/components/forum/SocialPost'
import ChatRoomAdd from '@/components/chatRoom/chatRoomAdd'
import Register from '@/components/Register'
import CourseIndex from '@/components/course/CourseIndex'
import CourseDetail from '@/components/course/CourseDetail'
import CourseNotice from '@/components/course/CourseNotice'
import Courseware from '@/components/course/Courseware'
import ChatRoomIndex from '@/components/chatRoom/chatRoomIndex'
import ChatRoomDetail from '@/components/chatRoom/chatRoomDetail'
import ChatRoomDetail2 from '@/components/chatRoom/chatRoomDetail2'

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
      path:'/chatroom',
      name:'chatroom',
      component: ChatRoomIndex,
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
    {
      path: '/ChatRoomAdd',
      name: 'ChatRoomAdd',
      component: ChatRoomAdd,
    },

    {
      path:'/chatRoomDetail/:chatRoomId',
      name:'chatRoomDetail',
      component:ChatRoomDetail
    },
    {
      path:'/chatRoomDetail2/:chatRoomId',
      name:'chatRoomDetail2',
      component:ChatRoomDetail2
    }



  ]
})
