import type { Contact } from "../../../types"

const STATUS_OPTS = [
  { v:'new',    l:'Nova',    cls:'bg-[rgba(14,165,233,0.15)] text-[var(--color-brand-400)] border-[rgba(14,165,233,0.25)]' },
  { v:'read',    l:'Lida',    cls:'bg-[var(--chb)] text-[var(--t3)] border-[var(--chbd)]' },
  { v:'replied', l:'Respondida',cls:'bg-emerald-500/15 text-emerald-400 border-emerald-500/25' },
  { v:'archived',l:'Arquivada', cls:'bg-[var(--chb)] text-[var(--t5)] border-[var(--chbd)]' },
]

interface ContactsTableProps {
  contacts: Contact[] | undefined
  onSelect: (c: Contact) => void
  onUpdateStatus: (id: number, status: string) => void
}

export function ContactsTable({ contacts, onSelect, onUpdateStatus }: ContactsTableProps) {
  return (
    <div className="card overflow-hidden"><div className="overflow-x-auto">
      <table className="w-full admin-table min-w-150">
        <thead><tr><th>Nome</th><th>E-mail</th><th>Assunto</th><th>Data</th><th>Status</th></tr></thead>
        <tbody>{contacts?.map(c=>(
          <tr key={c.id} onClick={()=>onSelect(c)} className="cursor-pointer">
            <td className="font-medium text-(--t1)">{c.name}</td>
            <td className="text-xs">{c.email}</td>
            <td className="max-w-45 truncate text-xs">{c.subject??'—'}</td>
            <td className="text-xs text-(--t4)">{c.createdAt?new Date(c.createdAt).toLocaleDateString('pt-BR'):''}</td>
            <td onClick={e=>e.stopPropagation()}>
              <select value={c.status??'new'} onChange={e=>onUpdateStatus(c.id, e.target.value)}
                className={`text-xs px-2 py-1 rounded-full border bg-transparent cursor-pointer focus:outline-none ${STATUS_OPTS.find(o=>o.v===c.status)?.cls??STATUS_OPTS[0].cls}`}>
                {STATUS_OPTS.map(o=><option key={o.v} value={o.v}>{o.l}</option>)}
              </select>
            </td>
          </tr>
        ))}</tbody>
      </table>
    </div></div>
  )
}