import { Link } from 'react-router-dom'
import { useProfile } from '../../hooks/useProfile'
import { Github, Linkedin, Mail, Twitter, Code2, MapPin } from 'lucide-react'

const LINKS=[{to:'/',l:'Início'},{to:'/projects',l:'Projetos'},{to:'/about',l:'Sobre'},{to:'/contact',l:'Contato'}]

export default function Footer() {
  const {data:p}=useProfile()
  return (
    <footer className="border-t border-(--bd) bg-(--s1)/60 mt-20 sm:mt-28">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 py-10 sm:py-14">
        <div className="grid grid-cols-1 sm:grid-cols-3 gap-8 mb-10">
          <div className="space-y-3">
            <Link to="/" className="flex items-center gap-2 group w-fit">
              <div className="w-8 h-8 rounded-lg bg-brand-500 flex items-center justify-center group-hover:bg-brand-400 transition-colors">
                <Code2 size={15} className="text-white"/>
              </div>
              <span className="font-semibold text-(--t1)">{p?.name?.split(' ')[0]??'Dev'}</span>
            </Link>
            <p className="text-(--t4) text-sm leading-relaxed max-w-xs">{p?.tagline??'Desenvolvedor Full Stack apaixonado por código.'}</p>
          </div>
          <div>
            <p className="text-(--t1) font-semibold text-sm mb-4">Navegação</p>
            <ul className="space-y-2 list-none">
              {LINKS.map(l=><li key={l.to}><Link to={l.to} className="text-(--t4) hover:text-brand-400 text-sm transition-colors">{l.l}</Link></li>)}
            </ul>
          </div>
          <div>
            <p className="text-(--t1) font-semibold text-sm mb-4">Contato</p>
            <div className="space-y-2">
              {p?.email&&<a href={`mailto:${p.email}`} className="flex items-center gap-2 text-(--t4) hover:text-(--t1) text-sm transition-colors"><Mail size={13}/>{p.email}</a>}
              {p?.location&&<p className="flex items-center gap-2 text-(--t4) text-sm"><MapPin size={13}/>{p.location}</p>}
              {p?.available&&<span className="inline-flex items-center gap-1.5 text-xs text-emerald-400 font-semibold"><span className="w-1.5 h-1.5 rounded-full bg-emerald-400 animate-pulse"/>Disponível para projetos</span>}
            </div>
          </div>
        </div>
        <div className="pt-6 border-t border-(--bd) flex flex-col sm:flex-row items-center justify-between gap-4">
          <p className="text-(--t5) text-xs">© {new Date().getFullYear()} {p?.name??'Portfólio'}. Todos os direitos reservados.</p>
          <div className="flex items-center gap-1">
            {p?.githubUrl&&<a href={p.githubUrl} target="_blank" rel="noreferrer" className="tap text-(--t4) hover:text-(--t1) transition-colors"><Github size={18}/></a>}
            {p?.linkedinUrl&&<a href={p.linkedinUrl} target="_blank" rel="noreferrer" className="tap text-(--t4) hover:text-(--t1) transition-colors"><Linkedin size={18}/></a>}
            {p?.twitterUrl&&<a href={p.twitterUrl} target="_blank" rel="noreferrer" className="tap text-(--t4) hover:text-(--t1) transition-colors"><Twitter size={18}/></a>}
            {p?.email&&<a href={`mailto:${p.email}`} className="tap text-(--t4) hover:text-(--t1) transition-colors"><Mail size={18}/></a>}
          </div>
        </div>
      </div>
    </footer>
  )
}
