import {createRouter, createWebHistory} from "vue-router";
import Home from "../views/Home.vue";

const routes = [
    {
        path: "/",
        redirect: "intro"
    }, {
        path: "/home",
        name: "Home",
        component: Home,
        meta: {
            title: 'HOME',
            permission: 3 //患者级别
        },
        children: [
            {
                path: "/dashboard",
                name: "dashboard",
                meta: {
                    title: '系统首页',
                    permission: 3 //患者级别
                },
                component: () => import (
                    /* webpackChunkName: "dashboard" */
                    "../views/Dashboard.vue")
            }, {
                path: "/table",
                name: "basetable",
                meta: {
                    title: '医生信息管理',
                    permission: 2 //医生级别
                },
                component: () => import (
                    /* webpackChunkName: "table" */
                    "../views/BaseTable.vue")
            }, {
                path: "/table_patient",
                name: "baseTablePatient",
                meta: {
                    title: '患者信息管理',
                    permission: 3 //患者级别
                },
                component: () => import (
                    /* webpackChunkName: "table" */
                    "../views/BaseTablePatient.vue")
            }, {
                path: "/table_patient_doctor_info",
                name: "basetablePatientDoctorInfo",
                meta: {
                    title: '医生患者对应表管理',
                    permission: 2 //医生级别
                },
                component: () => import (
                    /* webpackChunkName: "table" */
                    "../views/BaseTablePatientDoctorInfo.vue")
            }, {
                path: "/echarts",
                name: "echarts",
                meta: {
                    title: '数据分析',
                    permission: 1 //管理员级别
                },
                component: () => import (
                    /* webpackChunkName: "charts" */
                    "../views/Echarts.vue")
            }, {
                path: '/404',
                name: '404',
                meta: {
                    title: '找不到页面',
                    permission: 3 //患者级别
                },
                component: () => import (/* webpackChunkName: "404" */
                    '../views/404.vue')
            }, {
                path: '/403',
                name: '403',
                meta: {
                    title: '没有权限',
                    permission: 3 //患者级别
                },
                component: () => import (/* webpackChunkName: "403" */
                    '../views/403.vue')
            }
        ]
    }, {
        path: "/patient_login",
        name: "PatientLogin",
        meta: {
            title: '患者登录',
            permission: 3 //患者级别
        },
        component: () => import (
            /* webpackChunkName: "login" */
            "../views/PatientLogin.vue")
    }, {
        path: "/doctor_login",
        name: "DoctorLogin",
        meta: {
            title: '医生登录',
            permission: 3 //患者级别
        },
        component: () => import (
            /* webpackChunkName: "login" */
            "../views/DoctorLogin.vue")
    }, {
        path: "/admin_login",
        name: "AdminLogin",
        meta: {
            title: '管理员登录',
            permission: 3 //患者级别
        },
        component: () => import (
            /* webpackChunkName: "login" */
            "../views/AdminLogin.vue")
    }, {
        path: "/intro",
        name: "Intro",
        meta: {
            title: '欢迎进入系统',
            permission: 3 //患者级别
        },
        component: () => import (
            /* webpackChunkName: "login" */
            "../views/Intro.vue")
    },{
        path: "/patient_register",
        name: "PatientRegister",
        meta: {
            title: '患者注册',
            permission: 3 //患者级别
        },
        component: () => import (
            /* webpackChunkName: "login" */
            "../views/PatientRegister.vue")
    },{
        path: "/doctor_register",
        name: "DoctorRegister",
        meta: {
            title: '医生注册',
            permission: 1 //管理员级别
        },
        component: () => import (
            /* webpackChunkName: "login" */
            "../views/DoctorRegister.vue")
    },{
        path: "/patient_doctor_register",
        name: "PatientDoctorRegister",
        meta: {
            title: '医生患者对应表注册',
            permission: 2 //医生级别
        },
        component: () => import (
            /* webpackChunkName: "login" */
            "../views/PatientDoctorRegister.vue")
    },{
        path: "/patient_doctor_register_doctor_enter",
        name: "PatientDoctorRegisterDoctorEnter",
        meta: {
            title: '医生患者对应表注册',
            permission: 2 //医生级别
        },
        component: () => import (
            /* webpackChunkName: "login" */
            "../views/PatientDoctorRegisterDoctorEnter.vue")
    },{
        path: "/upload",
        name: "upload",
        meta: {
            title: '影像操作',
            permission: 3 //患者级别
        },
        component: () => import (
            /* webpackChunkName: "upload" */
            "../views/Upload.vue")
    },
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
});

router.beforeEach((to, from, next) => {
    document.title = `${to.meta.title} | vue-manage-system`;
    const role = localStorage.getItem('ms_type');
    console.log(to);
    //如果还没进入 没有role 这个时候如果不是下面这几个网页 会直接跳到intro 如果已经有了role 会继续判断下面的权限
    if (!role && to.path !== '/patient_register' && to.path !== '/intro' && to.path !== '/admin_login' && to.path !== '/patient_login' && to.path !== '/doctor_login') {
        next('/intro'); //防止直接进入 （作为拦截器）
    } else if (to.meta.permission===1) {
        // 如果是管理员权限则可进入
        role == 1
            ? next()
            : next('/403');
    }else if(to.meta.permission===2){
        // 如果是医生/管理员权限则可进入
        role == 1 || role == 2
            ? next()
            : next('/403');

    } else if(to.meta.permission===3){
        // 如果是患者/医生/管理员权限则可进入
            next();
    } else {
        next();
    }
});

export default router;