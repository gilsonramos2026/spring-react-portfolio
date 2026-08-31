import { useState } from 'react'
import type { Contact } from '../../types'

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
      <div>
        <h1 className="text-2xl font-bold text-(--t1)">Contatos</h1>
        <p className="text-(--t3) text-sm mt-0.5">{contacts?.length ?? 0} mensagens</p>
      </div>

      <ContactsFilter filter={filter} setFilter={setFilter} />

      <ContactsTable 
        contacts={contacts} 
        onSelect={setSelected} 
        onUpdateStatus={handleUpdateStatus} 
      />

      {/* CORRIGIDO: Envelopado com condicional para garantir que o modal só renderize com um Contact válido, eliminando o conflito com 'null' */}
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
