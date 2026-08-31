import { useForm } from 'react-hook-form'
import type { Project } from '../../../types'
import AdminModal from '../AdminModal'
import ProjectImageManager from '../ProjectImageManager'

type EditingState = Project | 'new' | null

export type ProjectFormData = Partial<Project> & { tagsInput?: string }

interface ProjectFormModalProps {
  editing: EditingState
  freshProject: Project | undefined
  onClose: () => void
  onSubmit: (data: ProjectFormData) => void
  isPending: boolean
}

export function ProjectFormModal({ editing, freshProject, onClose, onSubmit, isPending }: ProjectFormModalProps) {
  const isNew = editing === 'new'
  const defaultValues: ProjectFormData = isNew || editing === null ? {} : {
    ...editing,
    tagsInput: editing.tags?.join(', ') ?? ''
  }

  const { register, handleSubmit } = useForm<ProjectFormData>({ defaultValues })

  const currentTitle = freshProject?.title ?? (editing !== 'new' && editing !== null ? editing.title : '')

  return (
    <AdminModal
      title={isNew ? 'Novo Projeto' : `Editar: ${currentTitle}`}
      onClose={onClose}
      size="lg"
    >
      <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">Título *</label>
          <input {...register('title')} required className="input"/>
        </div>
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">Descrição curta *</label>
          <input {...register('shortDesc')} required className="input" placeholder="Max 300 chars"/>
        </div>
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">Descrição completa</label>
          <textarea {...register('description')} className="input" rows={4}/>
        </div>
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <div>
            <label className="block text-sm text-(--t3) mb-1.5">URL Thumbnail</label>
            <input {...register('thumbnailUrl')} className="input" placeholder="https://..."/>
          </div>
          <div>
            <label className="block text-sm text-(--t3) mb-1.5">Status</label>
            <select {...register('status')} className="input">
              <option value="completed">Concluído</option>
              <option value="in_progress">Em andamento</option>
              <option value="archived">Arquivado</option>
            </select>
          </div>
        </div>
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <div>
            <label className="block text-sm text-(--t3) mb-1.5">GitHub URL</label>
            <input {...register('githubUrl')} className="input" placeholder="https://github.com/..."/>
          </div>
          <div>
            <label className="block text-sm text-(--t3) mb-1.5">Demo URL</label>
            <input {...register('demoUrl')} className="input" placeholder="https://..."/>
          </div>
        </div>
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">Tags (separadas por vírgula)</label>
          <input {...register('tagsInput')} className="input" placeholder="React, TypeScript, Spring Boot"/>
        </div>
        <div className="flex items-center gap-3">
          <input {...register('featured')} type="checkbox" id="feat" className="w-4 h-4 accent-brand-500"/>
          <label htmlFor="feat" className="text-sm text-(--t2)">Projeto em destaque</label>
        </div>

        {!isNew && freshProject && (
          <div className="pt-3 border-t border-(--bd)">
            <ProjectImageManager projectId={freshProject.id} images={freshProject.images ?? []}/>
          </div>
        )}

        {isNew && (
          <div className="pt-2 border-t border-(--bd)">
            <p className="text-xs text-(--t4) bg-(--cb) rounded-lg p-3">💡 Salve o projeto primeiro para adicionar screenshots.</p>
          </div>
        )}

        <div className="flex flex-col sm:flex-row gap-3 pt-2">
          <button type="submit" disabled={isPending} className="btn-primary flex-1 justify-center">
            {isPending ? 'Salvando...' : 'Salvar'}
          </button>
          <button type="button" onClick={onClose} className="btn-outline justify-center px-6">Cancelar</button>
        </div>
      </form>
    </AdminModal>
  )
}