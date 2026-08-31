import { useState } from 'react'
import { useAdminTestimonials, useCreateTestimonial, useUpdateTestimonial, useDeleteTestimonial } from '../../hooks/useTestimonials'
import { useForm } from 'react-hook-form'
import type { Testimonial } from '../../types'

import { AdminHeader } from '../../components/admin/AdminHeader'
import { TestimonialsTable } from '../../components/admin/testimonials/TestimonialsTable'
import { TestimonialFormModal } from '../../components/admin/testimonials/TestimonialFormModal'

type Editing = Testimonial | 'new' | null

export function AdminTestimonials() {
  const { data: items } = useAdminTestimonials()
  const create = useCreateTestimonial()
  const update = useUpdateTestimonial()
  const del = useDeleteTestimonial()

  const [editing, setEditing] = useState<Editing>(null)
  const { reset } = useForm<Partial<Testimonial>>()

  const open = (t: Testimonial | 'new') => {
    setEditing(t)
    reset(t === 'new' ? { rating: 5 } : t)
  }

  const close = () => {
    setEditing(null)
    reset()
  }

  const onSubmit = (d: Partial<Testimonial>) => {
    if (editing === 'new') {
      create.mutate(d, { onSuccess: close })
    } else if (editing !== null) {
      update.mutate({ id: editing.id, data: d }, { onSuccess: close })
    }
  }

  return (
    <div className="space-y-6">
      <AdminHeader
        title="Testemunhos"
        count={items?.length}
        countLabel="registros"
        buttonLabel="Novo"
        onAdd={() => open('new')}
      />

      <TestimonialsTable
        items={items}
        onOpen={(t) => open(t)}
        onDelete={(id) => {
          if (confirm('Remover?')) del.mutate(id)
        }}
      />

      {editing !== null && (
        <TestimonialFormModal
          editing={editing}
          onClose={close}
          isPending={create.isPending || update.isPending}
          onSubmit={onSubmit}
        />
      )}
    </div>
  )
}