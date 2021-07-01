import Vue from 'vue'
import Router from 'vue-router'
import Home from '../components/Home.vue'
import Register from '../components/Register.vue'
import PayForAdvance from '../components/PayForAdvance.vue'

Vue.use(Router)
const router = new Router({
	mode: "history",
	routes: [{
			path: '/',
			redirect: Home
		},
		{
			path: '/Home',
			name: 'Home',
			component: Home
		},
		{
			path: '/Register',
			name: 'Register',
			component: Register
		},{
			path:'/PayForAdvance',
			name:'PayForAnvanced',
			component:PayForAdvance
		}]
})
export default router
