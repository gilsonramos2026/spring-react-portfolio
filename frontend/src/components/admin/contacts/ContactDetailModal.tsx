import { Mail, Phone, Calendar } from 'lucide-react'
import AdminModal from '../AdminModal'
import type { Contact } from '../../../types'

const STATUS_OPTS = [
  { v:'new',    l:'Nova',    cls:'bg-[rgba(14,165,233,0.15)] text-[var(--color-brand-400)] border-[rgba(14,165,233,0.25)]' },
  { v:'read',    l:'Lida',    cls:'bg-[var(--chb)] text-[var(--t3)] border-[var(--chbd)]' },
  { v:'replied', l:'Respondida',cls:'bg-emerald-500/15 text-emerald-400 border-emerald-500/25' },
  { v:'archived',l:'Arquivada', cls:'bg-[var(--chb)] text-[var(--t5)] border-[var(--chbd)]' },
]

interface ContactDetailModalProps {
  selected: Contact
  onClose: () => void
  onUpdateStatus: (id: number, status: string) => void
}

export function ContactDetailModal({ selected, onClose, onUpdateStatus }: ContactDetailModalProps) {
  if (!selected) return null

  return (
    <AdminModal title="Mensagem" onClose={onClose}>
      <div className="space-y-4">
        <div className="flex flex-col gap-1">
          <h3 className="font-semibold text-(--t1) text-lg">{selected.name}</h3>
          <a href={`mailto:${selected.email}`} className="flex items-center gap-1.5 text-sm text-brand-400 hover:underline"><Mail size={13}/>{selected.email}</a>
          {selected.phone&&<a href={`tel:${selected.phone}`} className="flex items-center gap-1.5 text-sm text-(--t3)"><Phone size={13}/>{selected.phone}</a>}
          {selected.createdAt&&<p className="flex items-center gap-1.5 text-xs text-(--t4)"><Calendar size={11}/>{new Date(selected.createdAt).toLocaleString('pt-BR')}</p>}
        </div>
        {selected.subject&&<div className="card p-3"><p className="text-xs text-(--t4) mb-0.5">Assunto</p><p className="text-sm font-medium text-(--t1)">{selected.subject}</p></div>}
        <div className="card p-4"><p className="text-xs text-(--t4) mb-2">Mensagem</p><p className="text-sm text-(--t2) leading-relaxed whitespace-pre-wrap">{selected.message}</p></div>
        <div className="flex gap-2 flex-wrap">
          {STATUS_OPTS.map(o=>(
            <button key={o.v} onClick={()=>onUpdateStatus(selected.id, o.v)}
              className={`text-xs px-3 py-1.5 rounded-full border transition-all ${selected.status===o.v?o.cls:'border-(--bd) text-(--t4) hover:border-(--bd2)'}`}>
              {o.l}
            </button>
          ))}
        </div>
        <a href={`mailto:${selected.email}?subject=Re: ${selected.subject??'Sua mensagem'}`}
          className="btn-primary w-full justify-center text-sm"><Mail size={15}/>Responder por e-mail</a>
      </div>
    </AdminModal>
  )
}