import { productApi } from './api'

export async function getProducts(params){
  const res = await productApi.get('/products', { params })
  return res.data
}

export async function createProduct(data){
  const res = await productApi.post('/products', data)
  return res.data
}

export async function updateProduct(id,data){
  const res = await productApi.put(`/products/${id}`, data)
  return res.data
}

export async function deleteProduct(id){
  const res = await productApi.delete(`/products/${id}`)
  return res.data
}