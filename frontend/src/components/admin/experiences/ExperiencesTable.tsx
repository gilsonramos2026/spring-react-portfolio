import type { Experience } from '../../../types'
import { Pencil, Trash2 } from 'lucide-react'

interface ExperiencesTableProps {
  items: Experience[] | undefined
  onOpen: (item: Experience) => void
  onDelete: (id: number) => void
}

export function ExperiencesTable({ items, onOpen, onDelete }: ExperiencesTableProps) {
  return (
    <div className="card overflow-hidden">
      <div className="overflow-x-auto">
        <table className="w-full admin-table min-w-140">
          <thead>
            <tr>
              <th>Empresa</th>
              <th>Cargo</th>
              <th>Período</th>
              <th>Ações</th>
            </tr>
          </thead>
          <tbody>
            {items?.map(e => (
              <tr key={e.id}>
                <td className="font-medium text-(--t1)">{e.company}</td>
                <td>{e.role}</td>
                <td className="text-xs text-(--t4)">
                  {new Date(e.startDate).toLocaleDateString('pt-BR',{month:'short',year:'numeric'})} → {e.current?'Atual':e.endDate?new Date(e.endDate).toLocaleDateString('pt-BR',{month:'short',year:'numeric'}):''}
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