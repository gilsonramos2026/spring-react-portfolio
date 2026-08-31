import { Link } from 'react-router-dom'
import { ArrowRight } from 'lucide-react'
import FadeIn from '../../ui/FadeIn'

interface StatItem {
  label: string
  value: number
  Icon: React.ComponentType<{ size: number; className?: string }>
  to: string
  color: string
}

export function DashboardStatsGrid({ stats }: { stats: StatItem[] }) {
  return (
    <div className="grid grid-cols-2 sm:grid-cols-3 gap-4">
      {stats.map(({ label, value, Icon, to, color }, i) => (
        <FadeIn key={label} delay={i * 60}>
          <Link to={to} className="card p-4 sm:p-5 block group hover:scale-[1.02] transition-transform">
            <div className="flex items-center justify-between mb-3">
              <Icon size={18} className={color}/>
              <ArrowRight size={14} className="text-(--t5) group-hover:text-(--t3) transition-colors"/>
            </div>
            <p className="text-2xl font-bold text-(--t1)">{value}</p>
            <p className="text-xs text-(--t4) mt-0.5">{label}</p>
          </Link>
        </FadeIn>
      ))}
    </div>
  )
}