import type { Education } from '../../../types'
import { Pencil, Trash2 } from 'lucide-react'

interface EducationsTableProps {
  items: Education[] | undefined
  onOpen: (item: Education) => void
  onDelete: (id: number) => void
}

export function EducationsTable({ items, onOpen, onDelete }: EducationsTableProps) {
  return (
    <div className="card overflow-hidden">
      <div className="overflow-x-auto">
        <table className="w-full admin-table min-w-125">
          <thead>
            <tr>
              <th>Instituição</th>
              <th>Curso</th>
              <th>Período</th>
              <th>Ações</th>
            </tr>
          </thead>
          <tbody>
            {items?.map(e => (
              <tr key={e.id}>
                <td className="font-medium text-(--t1)">{e.institution}</td>
                <td>{e.degree}</td>
                <td className="text-xs text-(--t4)">
                  {new Date(e.startedAt).getFullYear()} → {e.current ? 'Atual' : e.endedAt ? new Date(e.endedAt).getFullYear() : ''}
                </td>
                <td>
                  <div className="flex gap-1">
                    <button onClick={() => onOpen(e)} className="tap text-(--t4) hover:text-brand-400 transition-colors">
                      <Pencil size={14}/>
                    </button>
                    <button onClick={() => onDelete(e.id)} className="tap text-(--t4) hover:text-red-400 transition-colors">
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