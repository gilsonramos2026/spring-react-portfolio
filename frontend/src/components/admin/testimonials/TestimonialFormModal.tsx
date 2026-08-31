import { useForm } from 'react-hook-form'
import type { Testimonial } from '../../../types'
import AdminModal from '../AdminModal'

type EditingState = Testimonial | 'new' | null

interface TestimonialFormModalProps {
  editing: EditingState
  onClose: () => void
  onSubmit: (data: Partial<Testimonial>) => void
  isPending: boolean
}

export function TestimonialFormModal({ editing, onClose, onSubmit, isPending }: TestimonialFormModalProps) {
  const isNew = editing === 'new'
  const defaultValues: Partial<Testimonial> = isNew || editing === null ? { rating: 5 } : editing

  const { register, handleSubmit } = useForm<Partial<Testimonial>>({ defaultValues })

  return (
    <AdminModal title={isNew ? 'Novo Testemunho' : 'Editar Testemunho'} onClose={onClose}>
      <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <div>
            <label className="block text-sm text-(--t3) mb-1.5">Nome *</label>
            <input {...register('name')} required className="input" />
          </div>
          <div>
            <label className="block text-sm text-(--t3) mb-1.5">Cargo *</label>
            <input {...register('role')} required className="input" />
          </div>
        </div>
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">Empresa</label>
          <input {...register('company')} className="input" />
        </div>
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">Depoimento *</label>
          <textarea {...register('content')} required className="input" rows={4} />
        </div>
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">URL do avatar</label>
          <input {...register('avatarUrl')} className="input" placeholder="https://..." />
        </div>
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">Avaliação (1-5)</label>
          <input {...register('rating', { valueAsNumber: true })} type="number" min={1} max={5} className="input" defaultValue={5} />
        </div>
        <div className="flex items-center gap-3">
          <input {...register('featured')} type="checkbox" id="feat2" className="w-4 h-4 accent-brand-500" />
          <label htmlFor="feat2" className="text-sm text-(--t2)">Exibir em destaque na home</label>
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