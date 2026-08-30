import { motion } from 'framer-motion'
import FadeIn from '../../ui/FadeIn'
import { ExternalLink, Github, Calendar, Images } from 'lucide-react'
import type { Project } from '../../../types'

interface ProjectSidebarSectionProps {
  project: Project
  imagesCount: number
}

export function ProjectSidebarSection({ project, imagesCount }: ProjectSidebarSectionProps) {
  return (
    <FadeIn delay={200} className="space-y-4">
      {/* Links */}
      <div className="card p-5 space-y-3">
        <h3 className="font-semibold text-sm uppercase tracking-wide text-(--t4)">Links</h3>
        {project.demoUrl && (
          <motion.a href={project.demoUrl} target="_blank" rel="noreferrer"
            whileHover={{ x: 3 }}
            className="flex items-center gap-2 text-sm text-(--t2) hover:text-brand-400 transition-colors">
            <ExternalLink size={15} /> Demo ao vivo
          </motion.a>
        )}
        {project.githubUrl && (
          <motion.a href={project.githubUrl} target="_blank" rel="noreferrer"
            whileHover={{ x: 3 }}
            className="flex items-center gap-2 text-sm text-(--t2) hover:text-(--t1) transition-colors">
            <Github size={15} /> Código fonte
          </motion.a>
        )}
        {!project.demoUrl && !project.githubUrl && (
          <p className="text-sm text-(--t4)">Nenhum link disponível.</p>
        )}
      </div>

      {/* Meta */}
      <div className="card p-5 space-y-3">
        <h3 className="font-semibold text-sm uppercase tracking-wide text-(--t4)">Detalhes</h3>

        {project.status && (
          <div className="flex items-center justify-between text-sm">
            <span className="text-(--t4)">Status</span>
            <span className={`px-2 py-0.5 rounded-full text-xs font-semibold border ${
              project.status === 'completed'
                ? 'bg-emerald-500/15 text-emerald-400 border-emerald-500/25'
                : 'bg-amber-500/15 text-amber-400 border-amber-500/25'
            }`}>
              {project.status === 'completed' ? 'Concluído' : 'Em andamento'}
            </span>
          </div>
        )}

        {project.startedAt && (
          <div className="flex items-center gap-2 text-sm text-(--t3)">
            <Calendar size={14} className="text-(--t4)" />
            {new Date(project.startedAt).toLocaleDateString('pt-BR', { month: 'long', year: 'numeric' })}
            {project.finishedAt && (
              <> → {new Date(project.finishedAt).toLocaleDateString('pt-BR', { month: 'long', year: 'numeric' })}</>
            )}
          </div>
        )}

        {imagesCount > 0 && (
          <div className="flex items-center gap-2 text-sm text-(--t3)">
            <Images size={14} className="text-(--t4)" />
            <span className="text-(--color-brand-400 font-semibold">{imagesCount}</span>
            {' '}screenshot{imagesCount > 1 ? 's' : ''}
            <span className="text-(--t5) text-xs">(clique para ampliar)</span>
          </div>
        )}
      </div>
    </FadeIn>
  )
}