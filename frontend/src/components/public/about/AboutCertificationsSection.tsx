import { resolveAssetUrl } from '../../../utils/api'
import FadeIn from '../../ui/FadeIn'
import { Award, ExternalLink } from 'lucide-react'
import type { Certification } from '../../../types'

interface AboutCertificationsSectionProps {
  certifications: Certification[] | undefined
}

export function AboutCertificationsSection({ certifications }: AboutCertificationsSectionProps) {
  if (!certifications || certifications.length === 0) {
    return (
      <section className="border-t border-(--bd) pt-12">
        <FadeIn>
          <h2 className="text-2xl font-bold text-(--t1) mb-4 flex items-center gap-2">
            <Award size={22} className="text-brand-400"/>
            Certificações
          </h2>
          <p className="text-sm text-(--t3)">Nenhuma certificação cadastrada no painel.</p>
        </FadeIn>
      </section>
    )
  }

  return (
    <section className="border-t border-(--bd) pt-12">
      <FadeIn><h2 className="text-2xl font-bold text-(--t1) mb-8 flex items-center gap-2"><Award size={22} className="text-brand-400"/>Certificações</h2></FadeIn>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
        {certifications.map((c, i) => (
          <FadeIn key={c.id} delay={i * 60}>
            <div className="card p-4 flex gap-3 items-start">
              {c.imageUrl
                ? <img src={resolveAssetUrl(c.imageUrl)} alt={c.name} className="w-10 h-10 rounded-lg object-cover shrink-0"/>
                : <div className="w-10 h-10 rounded-lg bg-amber-500/15 flex items-center justify-center shrink-0"><Award size={18} className="text-amber-400"/></div>
              }
              <div className="min-w-0">
                <p className="font-medium text-sm text-(--t1) leading-tight">{c.name}</p>
                <p className="text-xs text-(--t4) mt-0.5">{c.issuer}</p>
                <p className="text-xs text-(--t5) mt-0.5">{new Date(c.issuedAt).toLocaleDateString('pt-BR', { month: 'short', year: 'numeric' })}</p>
                {c.credentialUrl && (
                  <a href={c.credentialUrl} target="_blank" rel="noreferrer"
                    className="inline-flex items-center gap-1 text-xs text-brand-400 hover:underline mt-1">
                    <ExternalLink size={11}/> Ver credencial
                  </a>
                )}
              </div>
            </div>
          </FadeIn>
        ))}
      </div>
    </section>
  )
}