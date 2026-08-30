import FadeIn from '../../ui/FadeIn'
import { Briefcase } from 'lucide-react'
import type { Experience } from '../../../types'

interface AboutExperienceSectionProps {
  experiences: Experience[] | undefined
}

function formatPeriod(start?: string | Date, end?: string | Date, current?: boolean) {
  if (!start) return ''
  const dateObj = new Date(start)
  if (isNaN(dateObj.getTime())) return ''
  
  const s = dateObj.toLocaleDateString('pt-BR', { month: 'short', year: 'numeric' })
  if (current) return `${s} – Presente`
  if (!end) return s
  
  const endDateObj = new Date(end)
  if (isNaN(endDateObj.getTime())) return s
  return `${s} – ${endDateObj.toLocaleDateString('pt-BR', { month: 'short', year: 'numeric' })}`
}

export function AboutExperienceSection({ experiences }: AboutExperienceSectionProps) {
  // Se ainda estiver carregando (undefined), não renderiza nada ou mostra loading
  if (experiences === undefined) {
    return null
  }

  // Se já carregou mas não há experiências cadastradas no backend
  if (experiences.length === 0) {
    return (
      <section className="mb-16 border-t border-(--bd) pt-12">
        <FadeIn>
          <h2 className="text-2xl font-bold text-(--t1) mb-4 flex items-center gap-2">
            <Briefcase size={22} className="text-brand-400)"/>
            Experiência
          </h2>
          <p className="text-sm text-(--t3)">Nenhuma experiência cadastrada no painel.</p>
        </FadeIn>
      </section>
    )
  }

  return (
    <section className="mb-16 border-t border-(--bd) pt-12">
      <FadeIn>
        <h2 className="text-2xl font-bold text-(--t1) mb-8 flex items-center gap-2">
          <Briefcase size={22} className="text-brand-400"/>
          Experiência
        </h2>
      </FadeIn>
      
      <div className="space-y-4">
        {experiences.map((e, i) => (
          <FadeIn key={e.id ?? i} delay={i * 80}>
            <div className="card p-5 sm:p-6 flex gap-4">
              <div className="timeline-dot mt-1.5 shrink-0"/>
              <div className="flex-1 min-w-0">
                <div className="flex flex-col sm:flex-row sm:items-center gap-1 sm:gap-3 mb-1">
                  <h3 className="font-semibold text-(--t1)">{e.role}</h3>
                  {e.type && (
                    <span className="text-xs px-2 py-0.5 rounded-full bg-(--chb) border border-(--chbd) text-(--t4) w-fit">
                      {e.type.replace('_',' ')}
                    </span>
                  )}
                </div>
                <p className="text-brand-400 font-medium text-sm">{e.company}</p>
                <p className="text-xs text-(--t4) mt-0.5 mb-3">
                  {formatPeriod(e.startDate, e.endDate, e.current)}
                  {e.location && ` · ${e.location}`}
                </p>
                {e.description && <p className="text-sm text-(--t3) leading-relaxed">{e.description}</p>}
                {e.technologies && e.technologies.length > 0 && (
                  <div className="flex flex-wrap gap-1.5 mt-3">
                    {e.technologies.map((t, index) => (
                      <span key={index} className="tag text-xs">{t}</span>
                    ))}
                  </div>
                )}
              </div>
            </div>
          </FadeIn>
        ))}
      </div>
    </section>
  )
}