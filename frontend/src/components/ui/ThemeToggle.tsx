import { Sun, Moon } from 'lucide-react'
import { useTheme } from '../../context/ThemeContext'

export default function ThemeToggle() {
  const { theme, toggle } = useTheme()
  return (
    <button type="button" onClick={toggle} className="theme-toggle"
      aria-label={theme==='dark'?'Tema claro':'Tema escuro'}>
      <span className="theme-knob">
        {theme==='dark'?<Moon size={11} className="text-white"/>:<Sun size={11} className="text-white"/>}
      </span>
    </button>
  )
}

