<template>
	<div>
		<a>姓名：{{inputDebtEntity.username}}</a>
		<a v-if="inputDebtEntity.arrive==1"> 金额：{{inputDebtEntity.value}}</a>
		<a v-if="inputDebtEntity.arrive==0"> 金额：{{inputDebtEntity.payoff}}</a>
		<br />
	</div>
</template>

<script>
	export default{
		props:[
			"inputDebtEntity"
		]
	}
</script>

<style>
</style>
