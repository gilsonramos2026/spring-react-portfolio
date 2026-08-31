import type { Testimonial } from '../../../types'
import { Pencil, Trash2, Star } from 'lucide-react'

interface TestimonialsTableProps {
  items: Testimonial[] | undefined
  onOpen: (testimonial: Testimonial) => void
  onDelete: (id: number) => void
}

export function TestimonialsTable({ items, onOpen, onDelete }: TestimonialsTableProps) {
  return (
    <div className="card overflow-hidden">
      <div className="overflow-x-auto">
        <table className="w-full admin-table min-w-125">
          <thead>
            <tr>
              <th>Nome</th>
              <th>Cargo / Empresa</th>
              <th>Avaliação</th>
              <th>Destaque</th>
              <th>Ações</th>
            </tr>
          </thead>
          <tbody>
            {items?.map(t => (
              <tr key={t.id}>
                <td className="font-medium text-(--t1)">{t.name}</td>
                <td className="text-xs">{t.role}{t.company ? ` · ${t.company}` : ''}</td>
                <td>
                  <div className="flex">
                    {Array.from({ length: t.rating ?? 5 }).map((_, i) => (
                      <Star key={i} size={12} className="text-amber-400 fill-amber-400" />
                    ))}
                  </div>
                </td>
                <td>{t.featured && <span className="text-xs text-brand-400">✓</span>}</td>
                <td>
                  <div className="flex gap-1">
                    <button onClick={() => onOpen(t)} className="tap text-(--t4) hover:text-brand-400 transition-colors">
                      <Pencil size={14} />
                    </button>
                    <button onClick={() => onDelete(t.id)} className="tap text-(--t4) hover:text-red-400 transition-colors">
                      <Trash2 size={14} />
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