import { useState } from 'react'
import { useAdminCertifications, useCreateCertification, useUpdateCertification, useDeleteCertification } from '../../hooks/useCertifications'
import { useForm } from 'react-hook-form'
import type { Certification } from '../../types'
import { Plus } from 'lucide-react'
import { CertificationsTable } from '../../components/admin/CertificationsTable'
import { CertificationFormModal } from '../../components/admin/CertificationFormModal'


type Editing = Certification | 'new' | null

export  function AdminCertifications() {
  const { data: items } = useAdminCertifications()
  const create = useCreateCertification()
  const update = useUpdateCertification()
  const del = useDeleteCertification()
  
  const [editing, setEditing] = useState<Editing>(null)
  const { reset } = useForm<Partial<Certification>>()
  
  const open = (c: Certification | 'new') => { setEditing(c); reset(c === 'new' ? {} : c) }
  const close = () => { setEditing(null); reset() }

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-(--t1)">Certificações</h1>
          <p className="text-(--t3) text-sm">{items?.length ?? 0} registros</p>
        </div>
        <button onClick={() => open('new')} className="btn-primary text-sm">
          <Plus size={15}/>Nova
        </button>
      </div>

      <CertificationsTable 
        items={items} 
        onOpen={(c) => open(c)} 
        onDelete={(id) => { if (confirm('Remover?')) del.mutate(id) }} 
      />

      {editing !== null && (
        <CertificationFormModal
          editing={editing}
          onClose={close}
          isPending={create.isPending || update.isPending}
          onSubmit={(d) => {
            if (editing === 'new') {
              create.mutate(d, { onSuccess: close })
            } else {
              update.mutate({ id: (editing as Certification).id, data: d }, { onSuccess: close })
            }
          }}
        />
      )}
    </div>
  )
}