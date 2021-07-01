<template>
	<div id="app">
		<div class="loginbox">
			<a>{{ loginStatus }}</a>
			<br />
			<button v-on:click="login()" v-if="!ifLogin">登录</button>

			<router-link to='/Register'><button v-if="!ifLogin">注册</button></router-link>

			<button v-on:click="logout()" v-if="ifLogin">注销</button>
		</div>
		<div class="footer_guide">
			<a class="guide_item">
				<span class="item_icon">
					<router-link to='/Home' class="iconfont">首页</router-link>
				</span>
			</a>
		</div>
		<!-- v-on:事件标签="函数名" -->
		<router-view style="height:auto;margin-bottom: 7.5rem;overflow-y: hidden;" v-on:setRouterPointer="routerPointer"
			v-bind:inputHorizontalProp="HorizontalProp" v-on:setRouterPointerWithParam="routerPointerWithParam">
		</router-view>
	</div>
</template>

<script>
	import jsonp from "jsonp";
	export default {
		data() {
			return {
				HorizontalProp: 0,
				userName: "",
				ifLogin: false,
				loginStatus: "未登录",
				isAdmin: false
			}
		},
		watch: {
			ifLogin: function(var1) {
				if (var1) this.loginStatus = "欢迎您 " + this.userName;
				else this.loginStatus = "未登录";
				this.adminCheck();
			},
			HorizontalProp:function(var2){
				console.log(var2);
			}
		},
		methods: {
			login() {
				jsonp(
					"/Xapi/login", {
						param: "callback",
						timeout: 30000,
						prefix: "callback",
						name: "callback_IOlogin"
					},
					(err, data) => {
						if (err) {} else {
							console.log(data);
							this.userName = data;
							if (this.userName == "anonymousUser") this.ifLogin = false;
							else this.ifLogin = true;
							location.reload();
						}
					}
				)
			},
			getUsername() {
				jsonp(
					"/Xapi/getUsername", {
						param: "callback",
						timeout: 30000,
						prefix: "callback",
						name: "callback_IOgetUsername"
					},
					(err, data) => {
						if (err) {} else {
							console.log(data);
							this.userName = data;
							if (this.userName == "anonymousUser") this.ifLogin = false;
							else this.ifLogin = true;
						}
					}
				)
			},
			routerPointer(dest) {
				this.$router.push(dest);
			},
			routerPointerWithParam(param) {
				this.HorizontalProp = param.value;
				this.$router.push(param.dest);
			},
			adminCheck() {
				jsonp(
					"/Xapi/adminCheck", {
						param: "callback",
						timeout: 30000,
						prefix: "callback",
						name: "callback_IOadminCheck"
					},
					(err, data) => {
						if (err) {} else {
							console.log(data);
							this.isAdmin = data;
						}
					}
				)

			}
		},
		mounted() {
			this.getUsername();
			this.adminCheck();
			this.$router.push("/Home");
		}
	}
</script>

<style>
	#app {
		font-family: 'Avenir', Helvetica, Arial, sans-serif;
		-webkit-font-smoothing: antialiased;
		-moz-osx-font-smoothing: grayscale;
		text-align: center;
		color: #2c3e50;
		margin-top: 3.75rem;
		overflow-y: scroll;
	}

	.footer_guide {
		border-top: 0.0625rem soild #e4e4e4;
		position: fixed;
		z-index: 6.25rem;
		left: 0;
		right: 0;
		bottom: 0rem;
		background-color: #41b883;
		width: 100%;
		height: 5.125rem;
		display: flex;
	}

	.guide_item {
		display: flex;
		flex: 1;
		text-align: center;
		flex-direction: column;
		align-items: center;
		margin: 0.3125rem;
		color: #999999;
	}

	.on {
		color: #02a774
	}

	span {
		font-size: 0.75rem;
		margin-top: 0.125rem;
		margin-bottom: 0.125rem;
	}

	.iconfont {
		font-size: 1.2rem;
		color: #E4E4E4;
	}

	.item_icon {
		position: absolute;
		border-radius: 1.5625rem;
		background: #0088d2;
		padding: 1.25rem;
		height: 1.5rem;
		width: 4.5rem;
	}

	.loginbox {
		height: 3.125rem;
		position: absolute;
		right: 2rem;
		top: 0.3125rem;
		border: 0.0625rem #41B883;
	}
</style>
