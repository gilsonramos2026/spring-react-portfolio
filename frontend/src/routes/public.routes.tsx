import PublicLayout from "../components/layout/PublicLayout";
import ErrorBoundary from "../components/ui/ErrorBoundary ";
import AboutPage from "../pages/public/AboutPage";
import ContactPage from "../pages/public/ContactPage";
import HomePage from "../pages/public/HomePage";
import NotFound from "../pages/public/NotFound";
import ProjectDetail from "../pages/public/ProjectDetail";
import ProjectsPage from "../pages/public/ProjectsPage";


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