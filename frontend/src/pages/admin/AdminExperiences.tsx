import { useState } from 'react'
import { useAdminExperiences, useCreateExperience, useUpdateExperience, useDeleteExperience } from '../../hooks/useExperiences'
import { useForm } from 'react-hook-form'
import type { Experience } from '../../types'

import { AdminHeader } from '../../components/admin/AdminHeader'
import { ExperiencesTable } from '../../components/admin/experiences/ExperiencesTable'
import { ExperienceFormModal } from '../../components/admin/experiences/ExperienceFormModal'

type Editing = Experience | 'new' | null
type ExperienceFormData = Partial<Experience> & { techInput?: string }

export function AdminExperiences() {
  const { data: items } = useAdminExperiences()
  const create = useCreateExperience()
  const update = useUpdateExperience()
  const del = useDeleteExperience()
  
  const [editing, setEditing] = useState<Editing>(null)
  const { reset } = useForm<ExperienceFormData>()

  const open = (e: Experience | 'new') => { setEditing(e); reset(e === 'new' ? {} : e) }
  const close = () => { setEditing(null); reset() }

  const onSubmit = (d: ExperienceFormData) => {
    const payload = { 
      ...d, 
      technologies: typeof d.techInput === 'string' 
        ? d.techInput.split(',').map((t: string) => t.trim()).filter(Boolean) 
        : d.technologies 
    }
    delete (payload as ExperienceFormData).techInput

    if (editing === 'new') {
      create.mutate(payload, { onSuccess: close })
    } else if (editing !== null) {
      update.mutate({ id: editing.id, data: payload }, { onSuccess: close })
    }
  }

  return (
    <div className="space-y-6">
      <AdminHeader
        title="Experiências"
        count={items?.length}
        countLabel="registros"
        buttonLabel="Nova"
        onAdd={() => open('new')}
      />

      <ExperiencesTable 
        items={items} 
        onOpen={(e) => open(e)} 
        onDelete={(id) => { if (confirm('Remover?')) del.mutate(id) }} 
      />

      {editing !== null && (
        <ExperienceFormModal
          editing={editing}
          onClose={close}
          isPending={create.isPending || update.isPending}
          onSubmit={onSubmit}
        />
      )}
    </div>
  )
}