import { Link } from 'react-router-dom'
import { Bell, ArrowRight } from 'lucide-react'
import FadeIn from '../../ui/FadeIn'

export function DashboardAlert({ newCount }: { newCount: { count: number } | undefined }) {
  if (!newCount || newCount.count <= 0) return null

  return (
    <FadeIn>
      <Link to="/admin/contacts" className="flex items-center gap-3 card p-4 border-amber-500/30 bg-amber-500/5 hover:bg-amber-500/10 transition-colors">
        <Bell size={16} className="text-amber-400 shrink-0"/>
        <p className="text-sm font-medium text-amber-300">
          Você tem <strong>{newCount.count}</strong> nova{newCount.count > 1 ? 's mensagens' : ' mensagem'} de contato
        </p>
        <ArrowRight size={14} className="text-amber-400 ml-auto shrink-0"/>
      </Link>
    </FadeIn>
  )
}