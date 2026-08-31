import { useForm } from 'react-hook-form'
import type { Education } from '../../../types'
import AdminModal from '../AdminModal'

type EditingState = Education | 'new' | null

interface EducationFormModalProps {
  editing: EditingState
  onClose: () => void
  onSubmit: (data: Partial<Education>) => void
  isPending: boolean
}

export function EducationFormModal({ editing, onClose, onSubmit, isPending }: EducationFormModalProps) {
  const { register, handleSubmit } = useForm<Partial<Education>>({
    defaultValues: editing === 'new' || editing === null ? {} : editing
  })

  const isNew = editing === 'new'

  return (
    <AdminModal title={isNew ? 'Nova Formação' : 'Editar Formação'} onClose={onClose}>
      <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">Instituição *</label>
          <input {...register('institution')} required className="input"/>
        </div>
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">Curso/Grau *</label>
          <input {...register('degree')} required className="input" placeholder="Ex: Bacharelado em Ciência da Computação"/>
        </div>
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">Área</label>
          <input {...register('fieldOfStudy')} className="input"/>
        </div>
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <div>
            <label className="block text-sm text-(--t3) mb-1.5">Início *</label>
            <input {...register('startedAt')} required type="date" className="input"/>
          </div>
          <div>
            <label className="block text-sm text-(--t3) mb-1.5">Fim</label>
            <input {...register('endedAt')} type="date" className="input"/>
          </div>
        </div>
        <div className="flex items-center gap-3">
          <input {...register('current')} type="checkbox" id="curr2" className="w-4 h-4 accent-brand-500"/>
          <label htmlFor="curr2" className="text-sm text-(--t2)">Em andamento</label>
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