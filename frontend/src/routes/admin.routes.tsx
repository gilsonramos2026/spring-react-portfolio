import AdminLogin from "../pages/admin/AdminLogin"


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