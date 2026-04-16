<template>
  <header class="header">
    <div class="logo-box">⚡</div>

    <div>
      <h1>Inventory Platform</h1>
      <p>Vue + Spring Boot FullStack Demo</p>
    </div>
  </header>
</template>

<style scoped>
.header{
  height:80px;
  background:#111418;
  border-bottom:1px solid #2b313a;
  display:flex;
  align-items:center;
  padding:0 30px;
  gap:16px;
}

.logo-box{
  width:46px;
  height:46px;
  border-radius:12px;
  background:#16c784;
  color:black;
  display:flex;
  align-items:center;
  justify-content:center;
  font-size:22px;
  font-weight:700;
}

h1{
  font-size:22px;
}

p{
  color:#999;
  font-size:14px;
}
</style>