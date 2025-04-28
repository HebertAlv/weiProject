<template>
  <div class="login-container">
    <h1>FrogCrew Login</h1>
    <form @submit.prevent="handleLogin">
      <div class="form-group">
        <label>Username</label>
        <input v-model="username" type="text" required>
      </div>
      <div class="form-group">
        <label>Password</label>
        <input v-model="password" type="password" required>
      </div>
      <button type="submit">Login</button>
      <p v-if="error" class="error">{{ error }}</p>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { login, isAuthenticated } from '@/api/auth'

const router = useRouter()
const username = ref('')
const password = ref('')
const error = ref('')

const handleLogin = async () => {
  try {
    await login(username.value, password.value)
    const redirect = router.currentRoute.value.query.redirect || {name: 'home'}
    router.push(redirect)
  } catch (err) {
    error.value = 'Login failed'
  }
}
</script>

<style scoped>
.login-container {
  max-width: 400px;
  margin: 2rem auto;
  padding: 2rem;
  border: 1px solid #ddd;
  border-radius: 8px;
}

.form-group {
  margin-bottom: 1rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
}

.form-group input {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 4px;
}

button {
  background: #4d1979;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
}

.error {
  color: red;
  margin-top: 1rem;
}
</style>