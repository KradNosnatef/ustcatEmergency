<template>
	<div class="maindiv">
		<div class="maindiv">
			<p>---已垫付资金情况---</p>
			<DebtCard v-for="post1 in debtCards.arrived" v-bind:inputDebtEntity="post1"></DebtCard>
			<p>总额：{{debtSum.arrived}}</p>
		</div>
		<br />
		<div class="maindiv">
			<p>---准备垫付的情况---</p>
			<DebtCard v-for="post2 in debtCards.unarrived" v-bind:inputDebtEntity="post2"></DebtCard>
			<p>总额：{{debtSum.unarrived}}</p>
		</div>
	</div>
</template>

<script>
	import DebtCard from './subComponents/DebtCard.vue'
	import jsonp from "jsonp"
	export default {
		data() {
			return {
				debtCards: null,
				debtSum:null
			}
		},
		methods: {
			sumDebts() {
				var url = "/Xapi/Bill/sumDebts";
				jsonp(url, {
						param: "callback",
						timeout: 3000,
						prefix: "callback",
						name: "callback_IOsumDebts"
					},
					(err, data) => {
						if (err) {
							alert("获取垫付数据总额时出错");
						} else {
							console.log(data);
							this.debtSum = data;
						}
					})
			}
		},
		components: {
			DebtCard
		},
		mounted: function() {
			jsonp(
				"/Xapi/Bill/listAllDebts", {
					param: "callback",
					timeout: 10000,
					prefix: "callback",
					name: "callback_IOlistAllDebts"
				},
				(err, data) => {
					if (err) {
						alert("获取全部已实垫付数据时出错");
					} else {
						this.debtCards = data;
						console.log(this.debtCards);
						this.sumDebts();
					}
				}
			)
		}
	}
</script>

<style>
	.maindiv {
		height: auto;
		border: 1px #41B883;
		box-shadow: 10px 10px 5px #888888;
	}

	.loginbox {
		height: 50px;
		position: absolute;
		right: 32px;
		border: 1px #41B883;
	}
</style>
