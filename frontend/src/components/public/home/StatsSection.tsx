import { motion } from 'framer-motion'
import { useProfile } from '../../../hooks/useProfile'
import { usePublicProjects } from '../../../hooks/useProjects'
import FadeIn from '../../ui/FadeIn'

const STACK_LENGTH = 10

export  function StatsSection() {
  const { data: profile } = useProfile()
  const { data: projects } = usePublicProjects(true)

  if (!profile?.yearsExp) return null

  const stats = [
    { v: `${profile.yearsExp}+`, l: 'Anos de experiência' },
    { v: `${projects?.length ?? 0}+`, l: 'Projetos entregues' },
    { v: `${STACK_LENGTH}+`, l: 'Tecnologias' },
    { v: '100%', l: 'Comprometimento' },
  ]

  return (
    <FadeIn>
      <section className="py-12 border-t border-(--bd)">
        <div className="grid grid-cols-2 sm:grid-cols-4 gap-4">
          {stats.map((s, i) => (
            <motion.div
              key={s.l}
              className="card p-5 sm:p-6 text-center"
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              transition={{ delay: i * 0.1, duration: 0.5 }}
              whileHover={{ scale: 1.04 }}
            >
              <p className="text-2xl sm:text-3xl font-bold text-gradient">{s.v}</p>
              <p className="text-xs sm:text-sm text-(--t4) mt-1">{s.l}</p>
            </motion.div>
          ))}
        </div>
      </section>
    </FadeIn>
  )
}