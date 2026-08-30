import { Outlet, NavLink, useNavigate } from 'react-router-dom'
import { useEffect, useState } from 'react'
import ThemeToggle from '../ui/ThemeToggle'
import { LayoutDashboard,FolderKanban,Zap,Briefcase,GraduationCap,Award,Star,MessageSquare,User,LogOut,Code2,Bell,Menu,X,ExternalLink } from 'lucide-react'
import clsx from 'clsx'
import { useContactCount } from '../../hooks/useContact'

const NAV=[
  {to:'/admin',label:'Dashboard',Icon:LayoutDashboard,end:true},
  {to:'/admin/profile',label:'Perfil',Icon:User},
  {to:'/admin/projects',label:'Projetos',Icon:FolderKanban},
  {to:'/admin/skills',label:'Skills',Icon:Zap},
  {to:'/admin/experiences',label:'Experiências',Icon:Briefcase},
  {to:'/admin/educations',label:'Educação',Icon:GraduationCap},
  {to:'/admin/certifications',label:'Certificações',Icon:Award},
  {to:'/admin/testimonials',label:'Testemunhos',Icon:Star},
  {to:'/admin/contacts',label:'Contatos',Icon:MessageSquare,badge:true},
]

function Sidebar({onClose}:{onClose?:()=>void}) {
  const navigate=useNavigate(); const {data:cnt}=useContactCount()
  const logout=()=>{localStorage.removeItem('admin_key');navigate('/admin/login');onClose?.()}
  return (
    <>
      <div className="h-14 sm:h-16 flex items-center justify-between px-4 border-b border-(--bd) shrink-0">
        <div className="flex items-center gap-2">
          <div className="w-6 h-6 rounded-lg bg-brand-500 flex items-center justify-center"><Code2 size={12} className="text-white"/></div>
          <span className="font-semibold text-(--t1) text-sm">Admin</span>
        </div>
        {onClose&&<button onClick={onClose} className="tap text-(--t3) hover:text-(--t1) md:hidden"><X size={18}/></button>}
      </div>
      <nav className="flex-1 p-3 space-y-0.5 overflow-y-auto">
        {NAV.map(({to,label,Icon,end,badge})=>(
          <NavLink key={to} to={to} end={end} onClick={onClose}
            className={({isActive})=>clsx('flex items-center gap-2.5 px-3 py-2.5 rounded-xl text-sm font-medium transition-all',
              isActive?'bg-[rgba(14,165,233,0.2)] text-brand-400 border border-[rgba(14,165,233,0.3)]'
                :'text-(--t3) hover:text-(--t1) hover:bg-(--cb)')}>
            <Icon size={15} className="shrink-0"/><span>{label}</span>
            {badge&&cnt&&cnt.count>0&&<span className="ml-auto bg-brand-500 text-white text-xs font-bold px-1.5 py-0.5 rounded-full min-w-[1.2rem] text-center">{cnt.count}</span>}
          </NavLink>
        ))}
      </nav>
      <div className="p-3 border-t border-(--bd) space-y-0.5 shrink-0">
        <a href="/" target="_blank" rel="noreferrer" className="flex items-center gap-2.5 px-3 py-2 rounded-xl text-xs text-(--t4) hover:text-color-brand-400 transition-all"><ExternalLink size={13}/>Ver site público</a>
        <button onClick={logout} className="flex items-center gap-2.5 w-full px-3 py-2.5 rounded-xl text-sm font-medium text-(--t3) hover:text-red-400 hover:bg-red-500/10 transition-all"><LogOut size={15}/>Sair</button>
      </div>
    </>
  )
}

export default function AdminLayout() {
  const navigate=useNavigate(); const [drawer,setDrawer]=useState(false); const {data:cnt}=useContactCount()
  useEffect(()=>{if(!localStorage.getItem('admin_key')) navigate('/admin/login')},[navigate])
  return (
    <div className="min-h-dvh flex bg-(--bg)">
      <aside className="hidden md:flex w-56 lg:w-60 flex-col shrink-0 bg-(--s1) border-r border-(--bd)"><Sidebar/></aside>
      {drawer&&(
        <div className="md:hidden fixed inset-0 z-50 flex">
          <div className="absolute inset-0 bg-black/60" onClick={()=>setDrawer(false)}/>
          <aside className="relative z-10 w-60 flex flex-col bg-(--s1) border-r border-(--bd)"><Sidebar onClose={()=>setDrawer(false)}/></aside>
        </div>
      )}
      <div className="flex-1 flex flex-col min-w-0">
        <header className="h-14 sm:h-16 bg-(--s1) border-b border-(--bd) flex items-center justify-between px-4 sm:px-6 shrink-0">
          <div className="flex items-center gap-3">
            <button className="md:hidden tap text-(--t3) hover:text-(--t1) transition-colors" onClick={()=>setDrawer(true)}><Menu size={20}/></button>
            <span className="text-(--t3) text-sm font-medium hidden sm:block">Painel Admin</span>
          </div>
          <div className="flex items-center gap-3">
            <ThemeToggle/>
            {cnt&&cnt.count>0&&<NavLink to="/admin/contacts" className="flex items-center gap-1.5 text-xs text-amber-400 hover:text-amber-300 transition-colors"><Bell size={13}/><span>{cnt.count} nova{cnt.count>1?'s':''}</span></NavLink>}
          </div>
        </header>
        <main className="flex-1 overflow-auto p-4 sm:p-6 lg:p-8"><Outlet/></main>
      </div>
    </div>
  )
}
