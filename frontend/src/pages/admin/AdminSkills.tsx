import { useState } from 'react'
import { useAdminSkills, useCreateSkill, useUpdateSkill, useDeleteSkill } from '../../hooks/useSkills'
import { useForm } from 'react-hook-form'
import type { Skill } from '../../types'

import { AdminHeader } from '../../components/admin/AdminHeader'
import { SkillsTable } from '../../components/admin/skills/SkillsTable'
import { SkillFormModal } from '../../components/admin/skills/SkillFormModal'

type Editing = Skill | 'new' | null

export function AdminSkills() {
  const { data: skills } = useAdminSkills()
  const create = useCreateSkill()
  const update = useUpdateSkill()
  const del = useDeleteSkill()
  
  const [editing, setEditing] = useState<Editing>(null)
  const { reset } = useForm<Partial<Skill>>()

  const cats = Array.from(new Set(skills?.map(s => s.category) ?? []))

  const open = (s: Skill | 'new') => {
    setEditing(s)
    reset(s === 'new' ? {} : s)
  }

  const close = () => {
    setEditing(null)
    reset()
  }

  const onSubmit = (d: Partial<Skill>) => {
    if (editing === 'new') {
      create.mutate(d, { onSuccess: close })
    } else if (editing !== null) {
      update.mutate({ id: editing.id, data: d }, { onSuccess: close })
    }
  }

  return (
    <div className="space-y-6">
      <AdminHeader
        title="Skills"
        count={skills?.length}
        countLabel="habilidades"
        buttonLabel="Nova skill"
        onAdd={() => open('new')}
      />

      <SkillsTable
        skills={skills}
        categories={cats}
        onOpen={(s) => open(s)}
        onDelete={(id) => {
          if (confirm('Remover skill?')) del.mutate(id)
        }}
      />

      {editing !== null && (
        <SkillFormModal
          editing={editing}
          onClose={close}
          isPending={create.isPending || update.isPending}
          onSubmit={onSubmit}
        />
      )}
    </div>
  )
}