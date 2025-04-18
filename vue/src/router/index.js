import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter)

const routes = [

    {
      path: '/register',
      name:'RegisterView',
      component:() => import('../views/Register')
    },

    {
        path: '/login',
        name:'LoginView',
        component:() => import('../views/LoginView')
    },
    {
        path:'/',
        name:'Layout',
        component:() => import('../views/Layout'),
        children:[
            {
                path:'',
                name:'HomeView',
                component:() => import('../views/HomeView')
            },
            {
                path: 'admin',
                name:'admin',
                component:() => import('../views/AdminView')
            },
            {
                path: 'vaccine',
                name:'vaccine',
                component:() => import('../views/Vaccine')
            },
            {
                path: 'gonggao',
                name:'gonggao',
                component:() => import('../views/gonggao')
            },
            {
                path:'OrderView',
                name:'OrderView',
                component:() => import('../views/OrderView')
            }
        ]
    }

]

const router = new VueRouter({
    routes
})

//路由守卫
router.beforeEach((to, from, next) => {
    if(to.path === '/register'){
        next();
    }
    if(to.path === '/login'){
        next();
    }
    const user = localStorage.getItem("AdminName");
    if(!user && to.path !== '/login' && to.path !== '/register'){
        return next("/login");
    }
    next();
})


export default router
