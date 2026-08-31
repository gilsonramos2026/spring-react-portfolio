import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'

export default defineConfig({
  plugins: [tailwindcss(), react()],
  server: {
    port: 3000,
    proxy: {
      // CORRIGIDO: O proxy /api agora centraliza e redireciona tanto os dados quanto as mídias (/api/uploads)
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
})
