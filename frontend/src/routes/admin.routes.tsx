import AdminLayout from '../components/layout/AdminLayout'
import ErrorBoundary from '../components/ui/ErrorBoundary'
import AdminLogin from '../pages/admin/AdminLogin'
import AdminDashboard from '../pages/admin/AdminDashboard'
import AdminProfile from '../pages/admin/AdminProfile'
import AdminProjects from '../pages/admin/AdminProjects'
import AdminSkills from '../pages/admin/AdminSkills'
import AdminExperiences from '../pages/admin/AdminExperiences'
import AdminEducations from '../pages/admin/AdminEducations'
import AdminCertifications from '../pages/admin/AdminCertifications'
import AdminTestimonials from '../pages/admin/AdminTestimonials'
import AdminContacts from '../pages/admin/AdminContacts'

export const adminLoginRoute = {
  path: '/admin/login',
  element: <AdminLogin />,
  errorElement: <ErrorBoundary />,
}

export const adminRoutes = {
  path: '/admin',
  element: <AdminLayout />,
  errorElement: <ErrorBoundary />,
  children: [
    { index: true, element: <AdminDashboard /> },
    { path: 'profile', element: <AdminProfile /> },
    { path: 'projects', element: <AdminProjects /> },
    { path: 'skills', element: <AdminSkills /> },
    { path: 'experiences', element: <AdminExperiences /> },
    { path: 'educations', element: <AdminEducations /> },
    { path: 'certifications', element: <AdminCertifications /> },
    { path: 'testimonials', element: <AdminTestimonials /> },
    { path: 'contacts', element: <AdminContacts /> },
  ],
}