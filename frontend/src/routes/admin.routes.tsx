import AdminLayout from "../components/layout/AdminLayout"
import ErrorBoundary from "../components/ui/ErrorBoundary "
import AdminCertifications from "../pages/admin/AdminCertifications"
import AdminContacts from "../pages/admin/AdminContacts"
import AdminEducations from "../pages/admin/AdminEducations"
import AdminExperiences from "../pages/admin/AdminExperiences"
import AdminLogin from "../pages/admin/AdminLogin"
import AdminProfile from "../pages/admin/AdminProfile"
import AdminSkills from "../pages/admin/AdminSkills"
import AdminTestimonials from "../pages/admin/AdminTestimonials"


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