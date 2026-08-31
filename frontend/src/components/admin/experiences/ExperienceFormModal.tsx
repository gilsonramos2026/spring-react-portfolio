import { useForm } from 'react-hook-form'
import type { Experience } from '../../../types'
import AdminModal from '../AdminModal'

type EditingState = Experience | 'new' | null

interface ExperienceFormData extends Partial<Experience> {
  techInput?: string
}

interface ExperienceFormModalProps {
  editing: EditingState
  onClose: () => void
  onSubmit: (data: ExperienceFormData) => void
  isPending: boolean
}

export function ExperienceFormModal({ editing, onClose, onSubmit, isPending }: ExperienceFormModalProps) {
  const isNew = editing === 'new'
  const defaultValues: ExperienceFormData = isNew || editing === null ? {} : {
    ...editing,
    techInput: editing.technologies?.join(', ') ?? ''
  }

  const { register, handleSubmit } = useForm<ExperienceFormData>({ defaultValues })

  return (
    <AdminModal title={isNew ? 'Nova Experiência' : 'Editar Experiência'} onClose={onClose} size="lg">
      <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <div>
            <label className="block text-sm text-(--t3) mb-1.5">Empresa *</label>
            <input {...register('company')} required className="input"/>
          </div>
          <div>
            <label className="block text-sm text-(--t3) mb-1.5">Cargo *</label>
            <input {...register('role')} required className="input"/>
          </div>
        </div>
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">Descrição</label>
          <textarea {...register('description')} className="input" rows={3}/>
        </div>
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <div>
            <label className="block text-sm text-(--t3) mb-1.5">Tipo</label>
            <select {...register('type')} className="input">
              <option value="full_time">CLT</option>
              <option value="freelance">Freelance</option>
              <option value="contract">PJ</option>
              <option value="internship">Estágio</option>
            </select>
          </div>
          <div>
            <label className="block text-sm text-(--t3) mb-1.5">Localização</label>
            <input {...register('location')} className="input"/>
          </div>
        </div>
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <div>
            <label className="block text-sm text-(--t3) mb-1.5">Início *</label>
            <input {...register('startDate')} required type="date" className="input"/>
          </div>
          <div>
            <label className="block text-sm text-(--t3) mb-1.5">Fim</label>
            <input {...register('endDate')} type="date" className="input"/>
          </div>
        </div>
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">Tecnologias (separadas por vírgula)</label>
          <input {...register('techInput')} className="input" placeholder="React, TypeScript, Java…"/>
        </div>
        <div className="flex items-center gap-3">
          <input {...register('current')} type="checkbox" id="curr" className="w-4 h-4 accent-brand-500"/>
          <label htmlFor="curr" className="text-sm text-(--t2)">Emprego atual</label>
        </div>
        <div className="flex gap-3 pt-2">
          <button type="submit" disabled={isPending} className="btn-primary flex-1 justify-center">
            {isPending ? 'Salvando…' : 'Salvar'}
          </button>
          <button type="button" onClick={onClose} className="btn-outline px-6">Cancelar</button>
        </div>
      </form>
    </AdminModal>
  )
}