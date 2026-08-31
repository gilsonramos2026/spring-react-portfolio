import { useState } from 'react'
import type { Contact } from '../../types'

import { AdminHeader } from '../../components/admin/AdminHeader'
import { ContactsFilter } from '../../components/admin/contacts/ContactsFilter'
import { ContactsTable } from '../../components/admin/contacts/ContactsTable'
import { ContactDetailModal } from '../../components/admin/contacts/ContactDetailModal'
import { useAdminContacts, useUpdateContactStatus } from '../../hooks/useContact'

export function AdminContacts() {
  const [filter, setFilter] = useState<string|undefined>(undefined)
  const { data: contacts } = useAdminContacts(filter)
  const updateStatus = useUpdateContactStatus()
  const [selected, setSelected] = useState<Contact|null>(null)

  const handleUpdateStatus = (id: number, status: string) => {
    updateStatus.mutate({ id, status })
    setSelected(s => s ? { ...s, status } : null)
  }

  return (
    <div className="space-y-6">
      <AdminHeader
        title="Contatos"
        count={contacts?.length}
        countLabel="mensagens"
        buttonLabel="" // Se não houver botão de ação principal, você pode ajustar ou omitir se preferir
        onAdd={() => {}}
      />

      <ContactsFilter filter={filter} setFilter={setFilter} />

      <ContactsTable 
        contacts={contacts} 
        onSelect={setSelected} 
        onUpdateStatus={handleUpdateStatus} 
      />

      {selected && (
        <ContactDetailModal 
          selected={selected} 
          onClose={() => setSelected(null)} 
          onUpdateStatus={handleUpdateStatus} 
        />
      )}
    </div>
  )
}