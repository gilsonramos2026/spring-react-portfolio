import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'

export default defineConfig({
  plugins: [tailwindcss(), react()],
  server: {
    port: 3000,
    proxy: {
      // API calls: /api/... → localhost:8080/api/...
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      // Static uploads: /uploads/... → localhost:8080/uploads/...
      // The Spring backend serves uploads OUTSIDE the /api context-path
      '/uploads': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
})

