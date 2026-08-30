import { Link } from 'react-router-dom'
import { resolveAssetUrl } from '../../../utils/api'
import { TechBadge } from '../../icons/TechIcon'
import { Github, ExternalLink } from 'lucide-react'
import type { Project } from '../../../types'

interface ProjectCardItemProps {
  project: Project
}

export function ProjectCardItem({ project: p }: ProjectCardItemProps) {
  return (
    <div className="card group flex flex-col h-full overflow-hidden">
      <Link to={`/projects/${p.slug}`}>
        <div className="aspect-video overflow-hidden bg-(--s2)">
          {(p.images?.[0]?.url || p.thumbnailUrl) ? (
            <img
              src={resolveAssetUrl(p.images?.[0]?.url ?? p.thumbnailUrl)}
              alt={p.title}
              className="w-full h-full object-cover transition-transform duration-500 group-hover:scale-105"
            />
          ) : (
            <div className="w-full h-full flex items-center justify-center text-(--t5) text-4xl font-bold">
              {p.title[0]}
            </div>
          )}
        </div>
      </Link>
      <div className="p-5 flex flex-col flex-1 gap-3">
        <div className="flex items-start justify-between gap-2">
          <Link to={`/projects/${p.slug}`}>
            <h3 className="font-semibold text-(--t1) group-hover:text-brand-400 transition-colors">
              {p.title}
            </h3>
          </Link>
          <span className={`text-xs px-2 py-0.5 rounded-full border shrink-0 ${
            p.status === 'completed'
              ? 'bg-emerald-500/15 text-emerald-400 border-emerald-500/25'
              : p.status === 'in_progress'
              ? 'bg-amber-500/15 text-amber-400 border-amber-500/25'
              : 'bg-(--chb) text-(--t4) border-(--chbd)'
          }`}>
            {p.status === 'completed' ? 'Concluído' : p.status === 'in_progress' ? 'Em andamento' : 'Arquivado'}
          </span>
        </div>
        <p className="text-sm text-(--t3) line-clamp-2 flex-1">{p.shortDesc}</p>
        <div className="flex flex-wrap gap-1.5">
          {p.tags?.map(t => <TechBadge key={t} name={t} size={13} />)}
        </div>
        <div className="flex gap-2 pt-1">
          {p.githubUrl && (
            <a
              href={p.githubUrl}
              target="_blank"
              rel="noreferrer"
              className="flex items-center gap-1.5 text-xs text-(--t4) hover:text-brand-400 transition-colors"
            >
              <Github size={14} /> Código
            </a>
          )}
          {p.demoUrl && (
            <a
              href={p.demoUrl}
              target="_blank"
              rel="noreferrer"
              className="flex items-center gap-1.5 text-xs text-(--t4) hover:text-brand-400 transition-colors"
            >
              <ExternalLink size={14} /> Demo
            </a>
          )}
        </div>
      </div>
    </div>
  )
}