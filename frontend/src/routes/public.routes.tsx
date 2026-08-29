

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