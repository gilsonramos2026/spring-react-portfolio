import { useForm } from 'react-hook-form'
import type { Skill } from '../../../types'
import AdminModal from '../AdminModal'

type EditingState = Skill | 'new' | null

interface SkillFormModalProps {
  editing: EditingState
  onClose: () => void
  onSubmit: (data: Partial<Skill>) => void
  isPending: boolean
}

export function SkillFormModal({ editing, onClose, onSubmit, isPending }: SkillFormModalProps) {
  const isNew = editing === 'new'
  const defaultValues: Partial<Skill> = isNew || editing === null ? {} : editing

  const { register, handleSubmit } = useForm<Partial<Skill>>({ defaultValues })

  const initialProficiency = !isNew && editing !== null ? editing.proficiency : 80

  return (
    <AdminModal title={isNew ? 'Nova Skill' : 'Editar Skill'} onClose={onClose}>
      <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">Nome *</label>
          <input {...register('name')} required className="input" placeholder="Ex: React"/>
        </div>
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">Categoria *</label>
          <input {...register('category')} required className="input" placeholder="Ex: Frontend, Backend, DevOps"/>
        </div>
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">Icon name (mesmo do nome geralmente)</label>
          <input {...register('iconName')} className="input" placeholder="Ex: react, spring boot, postgresql"/>
        </div>
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">
            Proficiência: <span className="text-brand-400" id="pval">{initialProficiency}%</span>
          </label>
          <input
            {...register('proficiency', { valueAsNumber: true })}
            type="range"
            min={1}
            max={100}
            className="w-full accent-brand-500"
            onInput={e => {
              const el = document.getElementById('pval')
              if (el) el.textContent = (e.target as HTMLInputElement).value + '%'
            }}
          />
        </div>
        <div>
          <label className="block text-sm text-[var(--t3) mb-1.5">Ordem</label>
          <input {...register('sortOrder', { valueAsNumber: true })} type="number" className="input" defaultValue={0}/>
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