import { useState } from 'react'
import { useAdminEducations, useCreateEducation, useUpdateEducation, useDeleteEducation } from '../../hooks/useEducations'
import { useForm } from 'react-hook-form'
import type { Education } from '../../types'
import { Plus } from 'lucide-react'

import { EducationsTable } from '../../components/admin/educations/EducationsTable'
import { EducationFormModal } from '../../components/admin/educations/EducationFormModal'

type Editing = Education | 'new' | null

export function AdminEducations() {
  const { data: items } = useAdminEducations()
  const create = useCreateEducation()
  const update = useUpdateEducation()
  const del = useDeleteEducation()

  const [editing, setEditing] = useState<Editing>(null)
  const { reset } = useForm<Partial<Education>>()

  const open = (e: Education | 'new') => { setEditing(e); reset(e === 'new' ? {} : e) }
  const close = () => { setEditing(null); reset() }

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-(--t1)">Educação</h1>
          <p className="text-(--t3) text-sm">{items?.length ?? 0} registros</p>
        </div>
        <button onClick={() => open('new')} className="btn-primary text-sm">
          <Plus size={15}/>Nova formação
        </button>
      </div>

      <EducationsTable 
        items={items} 
        onOpen={(e) => open(e)} 
        onDelete={(id) => { if (confirm('Remover?')) del.mutate(id) }} 
      />

      {editing !== null && (
        <EducationFormModal
          editing={editing}
          onClose={close}
          isPending={create.isPending || update.isPending}
          onSubmit={(d) => {
            if (editing === 'new') {
              create.mutate(d, { onSuccess: close })
            } else {
              update.mutate({ id: (editing as Education).id, data: d }, { onSuccess: close })
            }
          }}
        />
      )}
    </div>
  )
}