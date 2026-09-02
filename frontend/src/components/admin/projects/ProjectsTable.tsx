import type { Project } from '../../../types'
import { Pencil, Trash2, Github, ExternalLink } from 'lucide-react'

interface ProjectsTableProps {
  projects: Project[] | undefined
  onOpen: (project: Project) => void
  onDelete: (id: number) => void
}

export function ProjectsTable({ projects, onOpen, onDelete }: ProjectsTableProps) {
  return (
    <div className="card overflow-hidden">
      <div className="overflow-x-auto">
        <table className="w-full admin-table min-w-150">
          <thead>
            <tr>
              <th>Projeto</th>
              <th>Status</th>
              <th>Tags</th>
              <th>Links</th>
              <th>Ações</th>
            </tr>
          </thead>
          <tbody>
            {projects?.map(p => (
              <tr key={p.id}>
                <td>
                  <div className="font-medium text-(--t1)">{p.title}</div>
                  <div className="text-xs text-(--t4) truncate max-w-62.5">{p.shortDesc}</div>
                </td>
                <td>
                  <span className={`text-xs px-2 py-0.5 rounded-full border ${
                    p.status === 'completed'
                      ? 'bg-emerald-500/15 text-emerald-400 border-emerald-500/25'
                      : p.status === 'in_progress'
                      ? 'bg-amber-500/15 text-amber-400 border-amber-500/25'
                      : 'bg-(--chb) text-(--t4) border-(--chbd)'
                  }`}>
                    {p.status === 'completed' ? 'Concluído' : p.status === 'in_progress' ? 'Em andamento' : 'Arquivado'}
                  </span>
                </td>
                <td>
                  <div className="flex flex-wrap gap-1">
                    {/* CORRIGIDO AQUI: garante um array vazio caso p.tags venha nulo/undefined */}
                    {(p.tags || []).slice(0, 3).map(t => <span key={t} className="tag text-xs">{t}</span>)}
                  </div>
                </td>
                <td>
                  <div className="flex gap-2">
                    {p.githubUrl && (
                      <a href={p.githubUrl} target="_blank" rel="noreferrer" className="text-(--t4) hover:text-(--t1) transition-colors">
                        <Github size={14}/>
                      </a>
                    )}
                    {p.demoUrl && (
                      <a href={p.demoUrl} target="_blank" rel="noreferrer" className="text-(--t4) hover:text-brand-400 transition-colors">
                        <ExternalLink size={14}/>
                      </a>
                    )}
                  </div>
                </td>
                <td>
                  <div className="flex gap-1">
                    <button onClick={() => onOpen(p)} className="tap text-(--t4) hover:text-brand-400 transition-colors">
                      <Pencil size={14}/>
                    </button>
                    <button onClick={() => onDelete(p.id)} className="tap text-(--t4) hover:text-red-400 transition-colors">
                      <Trash2 size={14}/>
                    </button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  )
}