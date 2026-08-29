import PublicLayout from '../components/layout/PublicLayout'
import ErrorBoundary from '../components/ui/ErrorBoundary'
import HomePage from '../pages/public/HomePage'
import ProjectsPage from '../pages/public/ProjectsPage'
import ProjectDetail from '../pages/public/ProjectDetail'
import AboutPage from '../pages/public/AboutPage'
import ContactPage from '../pages/public/ContactPage'
import NotFound from '../pages/public/NotFound'

export const publicRoutes = {
  element: <PublicLayout />,
  errorElement: <ErrorBoundary />,
  children: [
    { path: '/', element: <HomePage /> },
    { path: '/projects', element: <ProjectsPage /> },
    { path: '/projects/:slug', element: <ProjectDetail /> },
    { path: '/about', element: <AboutPage /> },
    { path: '/contact', element: <ContactPage /> },
    { path: '*', element: <NotFound /> },
  ],
}