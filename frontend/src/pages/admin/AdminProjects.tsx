import { useState } from 'react'
import { useAdminProjects, useCreateProject, useUpdateProject, useDeleteProject } from '../../hooks/useProjects'
import { useForm } from 'react-hook-form'
import type { Project } from '../../types'
import { Plus } from 'lucide-react'

import { ProjectsTable } from '../../components/admin/projects/ProjectsTable'
import { ProjectFormModal, type ProjectFormData } from '../../components/admin/projects/ProjectFormModal'

type Editing = Project | 'new' | null

export function AdminProjects() {
  const { data: projects } = useAdminProjects()
  const create = useCreateProject()
  const update = useUpdateProject()
  const del = useDeleteProject()

  const [editing, setEditing] = useState<Editing>(null)
  const { reset } = useForm<ProjectFormData>()

  const freshProject = projects?.find(p => editing !== 'new' && editing !== null && p.id === editing.id)

  const open = (p: Project | 'new') => {
    setEditing(p)
    reset(p === 'new' ? {} : { ...p, tagsInput: p.tags?.join(', ') ?? '' })
  }

  const close = () => {
    setEditing(null)
    reset()
  }

  const onSubmit = (d: ProjectFormData) => {
    const { tagsInput, ...rest } = d
    const tags = tagsInput?.split(',').map(t => t.trim()).filter(Boolean) ?? []
    const payload = { ...rest, tags }

    if (editing === 'new') {
      create.mutate(payload, {
        onSuccess: (saved: Project) => {
          setEditing(saved)
        }
      })
    } else if (editing !== null) {
      update.mutate({ id: editing.id, data: payload }, { onSuccess: close })
    }
  }

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-(--t1)">Projetos</h1>
          <p className="text-(--t3) text-sm mt-0.5">{projects?.length ?? 0} projetos</p>
        </div>
        <button onClick={() => open('new')} className="btn-primary text-sm">
          <Plus size={15}/>Novo projeto
        </button>
      </div>

      <ProjectsTable
        projects={projects}
        onOpen={(p) => open(p)}
        onDelete={(id) => {
          if (confirm('Remover projeto?')) del.mutate(id)
        }}
      />

      {editing !== null && (
        <ProjectFormModal
          editing={editing}
          freshProject={freshProject}
          onClose={close}
          isPending={create.isPending || update.isPending}
          onSubmit={onSubmit}
        />
      )}
    </div>
  )
}