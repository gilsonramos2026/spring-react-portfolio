import { useForm } from 'react-hook-form'
import type { Certification } from '../../../types'
import AdminModal from '../AdminModal'

interface CertificationFormModalProps {
  editing: Certification | 'new'
  onClose: () => void
  onSubmit: (data: Partial<Certification>) => void
  isPending: boolean
}

export function CertificationFormModal({ editing, onClose, onSubmit, isPending }: CertificationFormModalProps) {
  const { register, handleSubmit } = useForm<Partial<Certification>>({
    defaultValues: editing === 'new' ? {} : editing
  })

  return (
    <AdminModal title={editing === 'new' ? 'Nova Certificação' : 'Editar Certificação'} onClose={onClose}>
      <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">Nome *</label>
          <input {...register('name')} required className="input"/>
        </div>
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">Emissor *</label>
          <input {...register('issuer')} required className="input" placeholder="Ex: AWS, Oracle, Google"/>
        </div>
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <div>
            <label className="block text-sm text-(--t3) mb-1.5">Emissão *</label>
            <input {...register('issuedAt')} required type="date" className="input"/>
          </div>
          <div>
            <label className="block text-sm text-(--t3) mb-1.5">Expiração</label>
            <input {...register('expiresAt')} type="date" className="input"/>
          </div>
        </div>
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">URL da credencial</label>
          <input {...register('credentialUrl')} className="input" placeholder="https://..."/>
        </div>
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">URL da imagem</label>
          <input {...register('imageUrl')} className="input" placeholder="https://..."/>
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