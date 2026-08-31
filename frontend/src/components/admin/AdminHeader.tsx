import { Plus } from 'lucide-react'

interface AdminHeaderProps {
  title: string
  count: number | undefined
  countLabel: string
  buttonLabel?: string // <-- Torne opcional com o '?'
  onAdd?: () => void  // <-- Torne opcional também
}

export function AdminHeader({ title, count, countLabel, buttonLabel, onAdd }: AdminHeaderProps) {
  return (
    <div className="flex items-center justify-between">
      <div>
        <h1 className="text-2xl font-bold text-(--t1)">{title}</h1>
        <p className="text-(--t3) text-sm mt-0.5">
          {count ?? 0} {countLabel}
        </p>
      </div>

      {/* Só renderiza o botão se o buttonLabel for fornecido */}
      {buttonLabel && (
        <button onClick={onAdd} className="btn-primary text-sm flex items-center gap-2">
          <Plus size={15} className="pointer-events-none" />
          {buttonLabel}
        </button>
      )}
    </div>
  )
}