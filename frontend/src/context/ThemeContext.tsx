import { createContext, useContext, useEffect, useState, type ReactNode } from 'react';
type Theme = 'dark' | 'light'
interface Ctx { theme: Theme; toggle: () => void }
const ThemeCtx = createContext<Ctx | null>(null)
function getInitial(): Theme {
  if (typeof window === 'undefined') return 'dark'
  const s = localStorage.getItem('theme') as Theme | null
  if (s === 'dark' || s === 'light') return s
  return window.matchMedia('(prefers-color-scheme: light)').matches ? 'light' : 'dark'
}
export function ThemeProvider({ children }: { children: ReactNode }) {
  const [theme, setTheme] = useState<Theme>(getInitial)
  useEffect(() => { document.documentElement.setAttribute('data-theme', theme); localStorage.setItem('theme', theme) }, [theme])
  return <ThemeCtx.Provider value={{ theme, toggle: () => setTheme(t => t === 'dark' ? 'light' : 'dark') }}>{children}</ThemeCtx.Provider>
}
export function useTheme() {
  const c = useContext(ThemeCtx); if (!c) throw new Error('useTheme outside ThemeProvider'); return c
}
