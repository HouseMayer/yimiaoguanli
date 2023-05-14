import Vue from 'vue'
import Router from 'vue-router'
import Layout from '@/components/Layout'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Layout',
      component: Layout,
      //重定向到list
      redirect: "userList",
      children: [
        {
          path: '/list',
          name: 'list',
          component: () => import('@/view/List')
        },
        {
          path: '/userList',
          name: 'userList',
          component: () => import('@/view/UserList')
        },
        {
          path: '/userInfoList',
          name: 'userInfoList',
          component: () => import('@/view/UserInfoList')
        },
        {
          path: '/vaccinumlist',
          name: 'vaccinumlist',
          component: () => import('@/view/VaccinumList')
        },
        {
          path: '/vaccinumtypelist',
          name: 'vaccinumtypelist',
          component: () => import('@/view/VaccinumTypeList')
        },
        {
          path: '/appointablelist',
          name: 'appointablelist',
          component: () => import('@/view/AppointableList')
        },
        {
          path: '/appointment',
          name: 'appointment',
          component: () => import('@/view/Appointment')
        },
        
      ]
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/view/login')

    }
  ]
})
