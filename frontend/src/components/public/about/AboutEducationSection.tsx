import { resolveAssetUrl } from '../../../utils/api'
import FadeIn from '../../ui/FadeIn'
import { GraduationCap, Calendar } from 'lucide-react'
import type { Education } from '../../../types'

interface AboutEducationSectionProps {
  educations: Education[] | undefined
}

function formatPeriod(start: string | Date, end?: string | Date, current?: boolean) {
  const s = new Date(start).toLocaleDateString('pt-BR', { month: 'short', year: 'numeric' })
  if (current) return `${s} – Presente`
  if (!end) return s
  return `${s} – ${new Date(end).toLocaleDateString('pt-BR', { month: 'short', year: 'numeric' })}`
}

export function AboutEducationSection({ educations }: AboutEducationSectionProps) {
  if (!educations || educations.length === 0) {
    return (
      <section className="mb-16 border-t border-(--bd) pt-12">
        <FadeIn>
          <h2 className="text-2xl font-bold text-(--t1) mb-4 flex items-center gap-2">
            <GraduationCap size={22} className="text-brand-400"/>
            Educação
          </h2>
          <p className="text-sm text-(--t3)">Nenhuma formação cadastrada no painel.</p>
        </FadeIn>
      </section>
    )
  }

  return (
    <section className="mb-16 border-t border-(--bd) pt-12">
      <FadeIn><h2 className="text-2xl font-bold text-(--t1) mb-8 flex items-center gap-2"><GraduationCap size={22} className="text-brand-400"/>Educação</h2></FadeIn>
      <div className="space-y-4">
        {educations.map((e, i) => (
          <FadeIn key={e.id} delay={i * 80}>
            <div className="card p-5 sm:p-6 flex gap-4 items-start">
              {e.logoUrl
                ? <img src={resolveAssetUrl(e.logoUrl)} alt={e.institution} className="w-10 h-10 rounded-lg object-cover shrink-0"/>
                : <div className="w-10 h-10 rounded-lg bg-brand-500/15 flex items-center justify-center shrink-0"><GraduationCap size={18} className="text-brand-400"/></div>
              }
              <div>
                <h3 className="font-semibold text-(--t1)">{e.degree}</h3>
                <p className="text-brand-400 text-sm">{e.institution}</p>
                <p className="text-xs text-(--t4) flex items-center gap-1 mt-0.5"><Calendar size={11}/>{formatPeriod(e.startedAt, e.endedAt, e.current)}</p>
                {e.fieldOfStudy && <p className="text-sm text-(--t3) mt-1">{e.fieldOfStudy}</p>}
              </div>
            </div>
          </FadeIn>
        ))}
      </div>
    </section>
  )
}