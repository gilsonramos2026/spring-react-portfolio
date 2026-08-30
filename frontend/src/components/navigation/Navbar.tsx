import { useState, useEffect } from 'react'
import { NavLink, Link } from 'react-router-dom'
import { useProfile } from '../../hooks/useProfile'
import ThemeToggle from '../ui/ThemeToggle'
import { Menu, X, Code2, Mail } from 'lucide-react'
import clsx from 'clsx'

const LINKS = [
  {to:'/',label:'Início',end:true},{to:'/projects',label:'Projetos'},
  {to:'/about',label:'Sobre'},{to:'/contact',label:'Contato'},
]

export default function Navbar() {
  const [open,setOpen]=useState(false)
  const [scrolled,setScrolled]=useState(false)
  const {data:profile}=useProfile()

  useEffect(()=>{
    const fn=()=>setScrolled(window.scrollY>24)
    window.addEventListener('scroll',fn,{passive:true}); return()=>window.removeEventListener('scroll',fn)
  },[])

  return (
    <header className={clsx('fixed top-0 inset-x-0 z-50 transition-all duration-300',
      scrolled?'bg-[var(--s1)]/90 backdrop-blur-md border-b border-[var(--bd)] shadow-lg':'bg-transparent')}>
      <nav className="max-w-7xl mx-auto px-4 sm:px-6 h-14 sm:h-16 flex items-center justify-between gap-4">
        <Link to="/" onClick={()=>setOpen(false)} className="flex items-center gap-2 group shrink-0">
          <div className="w-7 h-7 sm:w-8 sm:h-8 rounded-lg bg-[var(--color-brand-500)] flex items-center justify-center group-hover:bg-[var(--color-brand-400)] transition-colors">
            <Code2 size={14} className="text-white"/>
          </div>
          <span className="font-semibold text-base sm:text-lg text-[var(--t1)]">{profile?.name?.split(' ')[0]??'Dev'}</span>
        </Link>
        <ul className="hidden md:flex items-center gap-6 lg:gap-8 list-none">
          {LINKS.map(l=>(
            <li key={l.to}><NavLink to={l.to} end={l.end} className={({isActive})=>clsx('nav-link',isActive&&'active')}>{l.label}</NavLink></li>
          ))}
        </ul>
        <div className="hidden md:flex items-center gap-3">
          <ThemeToggle/>
          <Link to="/contact" className="btn-primary text-sm py-2 px-4"><Mail size={14}/> Falar comigo</Link>
        </div>
        <div className="md:hidden flex items-center gap-2">
          <ThemeToggle/>
          <button className="tap text-[var(--t3)] hover:text-[var(--t1)] transition-colors" onClick={()=>setOpen(o=>!o)} aria-expanded={open}>
            {open?<X size={22}/>:<Menu size={22}/>}
          </button>
        </div>
      </nav>
      <div className={clsx('md:hidden overflow-hidden transition-all duration-300',open?'max-h-72 opacity-100':'max-h-0 opacity-0 pointer-events-none')}>
        <div className="bg-[var(--s1)]/95 backdrop-blur-md border-b border-[var(--bd)] px-4 pt-2 pb-4 space-y-1">
          {LINKS.map(l=>(
            <NavLink key={l.to} to={l.to} end={l.end} onClick={()=>setOpen(false)}
              className={({isActive})=>clsx('tap px-3 rounded-xl text-sm font-medium transition-colors justify-start w-full',
                isActive?'text-[var(--t1)] bg-[var(--cb)]':'text-[var(--t3)] hover:text-[var(--t1)] hover:bg-[var(--cb)]')}>
              {l.label}
            </NavLink>
          ))}
          <div className="pt-2 border-t border-[var(--bd)]">
            <Link to="/contact" onClick={()=>setOpen(false)} className="btn-primary w-full justify-center text-sm"><Mail size={14}/> Falar comigo</Link>
          </div>
        </div>
      </div>
    </header>
  )
}
