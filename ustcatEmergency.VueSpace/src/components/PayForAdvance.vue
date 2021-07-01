<template>
	<div>
		<p>垫付申报</p>
		<a>我准备垫付：</a><input pattern="[0-99999]" v-model="value" /><a>元人民币</a><button
			v-on:click="readyToPayForAdvance()">确定</button>
	</div>
</template>

<script>
	import jsonp from 'jsonp'
	export default {
		data() {
			return {
				value: 0
			}

		},
		methods: {
			readyToPayForAdvance() {
				var url = this.$URLGenerator("/Xapi/Bill/readyToPayForAdvance", {
					value: this.value
				});
				jsonp(url, {
						param: "callback",
						timeout: 3000,
						prefix: "callback",
						name: "callback_IOreadyToPayForAdvance"
					},
					(err, data) => {
						if (err) {
							alert("发送垫付数据时出错");
						} else {
							console.log(data);
							alert("申报成功！");
						}
					}

				)
			}
		}
	}
</script>

<style>
</style>
