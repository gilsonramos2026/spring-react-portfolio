import { useState } from 'react'
import { useAdminEducations, useCreateEducation, useUpdateEducation, useDeleteEducation } from '../../hooks/useEducations'
import { useForm } from 'react-hook-form'
import type { Education } from '../../types'

import { AdminHeader } from '../../components/admin/AdminHeader'
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
      <AdminHeader
        title="Educação"
        count={items?.length}
        countLabel="registros"
        buttonLabel="Nova formação"
        onAdd={() => open('new')}
      />

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
              update.mutate({ id: editing.id, data: d }, { onSuccess: close })
            }
          }}
        />
      )}
    </div>
  )
}