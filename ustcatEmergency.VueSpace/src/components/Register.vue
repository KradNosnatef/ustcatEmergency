<template>
	<div>
		<p style="font-size: 3rem;">注册</p>
		<div>
			<p><a style="width: 1.25rem;">*登录名</a><input style="width: 7.5rem;" v-model="username" />
				<p />
			<p><a style="width: 1.25rem;">*密码</a><input style="width: 7.5rem;" type="password" v-model="password" />
				<p />
			<p><a style="width: 1.25rem;">*确认密码</a><input style="width: 7.5rem;" type="password" v-model="password2" />
				<p />
			<p v-if="!passwordSame">密码不一致</p>
			<p><a style="width: 1.25rem;">授权码</a><input style="width: 7.5rem;" v-model="authKey" />
				<p />
				<button v-on:click="regist()">确定</button>
			<p>*为必填项</p>
			<p>注册为管理员？请填写授权码（留空以注册为普通用户）</p>
		</div>
	</div>
</template>

<script>
	import jsonp from "jsonp";
	export default {
		data() {
			return {
				username: "",
				password: "",
				password2: "",
				passwordSame: true,
				authKey: null
			}
		},
		methods: {
			regist() {
				if (!this.passwordSame) alert("注册失败：两次密码不一致");
				else {
					var url = this.$URLGenerator("/Xapi/regist", {
						username: this.username,
						password: this.password,
						authKey: this.authKey
					});
					jsonp(url, {
						param: "callback",
						timeout: 3000,
						prefix: "callback",
						name: "callback_IOregist"
					}, (err, data) => {
						if (err) {
							alert(未知错误);
						} else {
							console.log(data);
							if (data == 0) alert("注册成功！");
							if (data == 1) alert("注册失败：登录名与他人重复");
							if (data == 2) alert("注册失败：授权码不正确");
						}
					})
				}
			}
		},
		watch: {
			password2: function(var1) {
				this.passwordSame = (var1 == this.password);
			},
			password: function(var1) {
				if (this.password2 != "") {
					this.passwordSame = (var1 == this.password2);
				}
			}
		}
	}
</script>

<style>
</style>
