import { Link } from 'react-router-dom'
import FadeIn from '../../ui/FadeIn'
import type { Contact } from '../../../types'

export function DashboardRecentContacts({ contacts }: { contacts: Contact[] | undefined }) {
  if (!contacts || contacts.length === 0) return null

  return (
    <FadeIn delay={200}>
      <div className="card overflow-hidden">
        <div className="px-5 py-4 border-b border-(--bd) flex items-center justify-between">
          <h3 className="font-semibold text-(--t1) text-sm">Últimas mensagens</h3>
          <Link to="/admin/contacts" className="text-xs text-brand-400 hover:underline">Ver todas</Link>
        </div>
        <div className="overflow-x-auto">
          <table className="w-full admin-table">
            <thead><tr><th>Nome</th><th>E-mail</th><th>Assunto</th><th>Status</th></tr></thead>
            <tbody>
              {contacts.slice(0, 5).map(c => (
                <tr key={c.id}>
                  <td className="font-medium text-(--t1)">{c.name}</td>
                  <td>{c.email}</td>
                  <td className="max-w-50 truncate">{c.subject ?? '—'}</td>
                  <td>
                    <span className={`text-xs px-2 py-0.5 rounded-full border ${
                      c.status === 'new' ? 'bg-[rgba(14,165,233,0.15)] text-brand-400 border-[rgba(14,165,233,0.25)]'
                      : c.status === 'replied' ? 'bg-emerald-500/15 text-emerald-400 border-emerald-500/25'
                      : 'bg-(--chb) text-(--t4) border-(--chbd)'
                    }`}>{c.status}</span>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </FadeIn>
  )
}