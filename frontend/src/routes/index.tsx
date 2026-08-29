import { createBrowserRouter } from 'react-router-dom'
import { publicRoutes } from './public.routes'
import { adminLoginRoute, adminRoutes } from './admin.routes'

const router = createBrowserRouter([
  publicRoutes,
  adminLoginRoute,
  adminRoutes,
])

export default router