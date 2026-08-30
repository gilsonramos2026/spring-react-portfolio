import { motion } from 'framer-motion'
import { Star } from 'lucide-react'
import { useTestimonials } from '../../../hooks/useTestimonials'
import FadeIn from '../../ui/FadeIn'
import { resolveAssetUrl } from '../../../utils/api'

export function TestimonialsSection() {
  const { data: testimonials } = useTestimonials(true)

  if (!testimonials || testimonials.length === 0) return null

  return (
    <section className="py-14 border-t border-(--bd)">
      <FadeIn className="text-center mb-10">
        <p className="text-xs text-brand-400 font-semibold uppercase tracking-widest mb-2">Depoimentos</p>
        <h2 className="text-2xl sm:text-3xl font-bold text-(--t1)">O que dizem sobre mim</h2>
      </FadeIn>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-5">
        {testimonials.slice(0, 3).map((t, i) => (
          <motion.div
            key={t.id}
            className="card p-6 h-full flex flex-col gap-4"
            initial={{ opacity: 0, y: 20 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true }}
            transition={{ delay: i * 0.12, duration: 0.5 }}
            whileHover={{ y: -4 }}
          >
            <div className="flex gap-0.5">
              {Array.from({ length: t.rating ?? 5 }).map((_, idx) => (
                <Star key={idx} size={14} className="text-amber-400 fill-amber-400" />
              ))}
            </div>
            <p className="text-(--t2) text-sm leading-relaxed flex-1 italic">"{t.content}"</p>
            <div className="flex items-center gap-3">
              {t.avatarUrl ? (
                <img src={resolveAssetUrl(t.avatarUrl)} alt={t.name}
                  className="w-9 h-9 rounded-full object-cover shrink-0" />
              ) : (
                <div className="w-9 h-9 rounded-full bg-brand-500/20 flex items-center justify-center text-brand-400 font-bold text-sm shrink-0">
                  {t.name[0]}
                </div>
              )}
              <div>
                <p className="font-semibold text-sm text-(--t1)">{t.name}</p>
                <p className="text-xs text-(--t4)">{t.role}{t.company ? ` · ${t.company}` : ''}</p>
              </div>
            </div>
          </motion.div>
        ))}
      </div>
    </section>
  )
}