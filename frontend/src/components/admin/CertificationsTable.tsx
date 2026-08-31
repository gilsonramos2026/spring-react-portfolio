import { Pencil, Trash2 } from 'lucide-react'
import type { Certification } from '../../types'

interface CertificationsTableProps {
  items: Certification[] | undefined
  onOpen: (c: Certification) => void
  onDelete: (id: number) => void
}

export function CertificationsTable({ items, onOpen, onDelete }: CertificationsTableProps) {
  return (
    <div className="card overflow-hidden">
      <div className="overflow-x-auto">
        <table className="w-full admin-table min-w-125">
          <thead>
            <tr>
              <th>Certificação</th>
              <th>Emissor</th>
              <th>Data</th>
              <th>Ações</th>
            </tr>
          </thead>
          <tbody>
            {items?.map(c => (
              <tr key={c.id}>
                <td className="font-medium text-(--t1)">{c.name}</td>
                <td>{c.issuer}</td>
                <td className="text-xs text-(--t4)">
                  {new Date(c.issuedAt).toLocaleDateString('pt-BR', { month: 'short', year: 'numeric' })}
                </td>
                <td>
                  <div className="flex gap-1">
                    <button onClick={() => onOpen(c)} className="tap text-(--t4) hover:text-brand-400 transition-colors">
                      <Pencil size={14}/>
                    </button>
                    <button onClick={() => onDelete(c.id)} className="tap text-(--t4) hover:text-red-400 transition-colors">
                      <Trash2 size={14}/>
                    </button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  )
}