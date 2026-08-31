import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { RouterProvider } from 'react-router-dom'
import { QueryClient, QueryClientProvider } from '@tanstack/react-query'
import { Toaster } from 'react-hot-toast'
import './styles/globals.css'
import { ThemeProvider } from './context/ThemeContext'
import router from './routes'
import { GlobalClickScrollHandler } from './components/ui/ScrollHandler'

const queryClient = new QueryClient({
  defaultOptions: {
    queries: { retry: 1, staleTime: 5 * 60_000, refetchOnWindowFocus: false },
  },
})

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <ThemeProvider>
      <QueryClientProvider client={queryClient}>
        <GlobalClickScrollHandler>
          <RouterProvider router={router} />
          <Toaster
            position="top-right"
            toastOptions={{
              duration: 4000,
              style: {
                background: 'var(--s2)',
                color: 'var(--t1)',
                border: '1px solid var(--bd)',
                borderRadius: '0.75rem',
                fontSize: '0.875rem',
              },
              success: { iconTheme: { primary: '#0ea5e9', secondary: '#fff' } },
              error:   { iconTheme: { primary: '#ef4444', secondary: '#fff' } },
            }}
          />
        </GlobalClickScrollHandler>
      </QueryClientProvider>
    </ThemeProvider>
  </StrictMode>
)