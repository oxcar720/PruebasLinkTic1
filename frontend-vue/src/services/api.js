import axios from 'axios'

export const inventoryApi = axios.create({
  baseURL: 'http://localhost:8082'
})

export const productApi = axios.create({
  baseURL: 'http://localhost:8081'
})