import { Link } from 'react-router-dom'
import { motion } from 'framer-motion'
import { Mail, ExternalLink } from 'lucide-react'
import { useProfile } from '../../../hooks/useProfile'
import FadeIn from '../../ui/FadeIn'

export default function CallToActionSection() {
  const { data: profile } = useProfile()

  return (
    <FadeIn>
      <section className="py-14 border-t border-(--bd) mb-8">
        <motion.div
          className="card p-8 sm:p-14 text-center relative overflow-hidden"
          style={{ borderColor: 'rgba(14,165,233,0.3)', background: 'linear-gradient(135deg, rgba(14,165,233,0.07), rgba(139,92,246,0.07))' }}
          whileHover={{ scale: 1.005 }}
        >
          <div className="absolute top-0 left-1/4 w-64 h-64 rounded-full opacity-20 pointer-events-none"
            style={{ background: 'radial-gradient(circle, #0ea5e9, transparent)', filter: 'blur(60px)', transform: 'translateY(-50%)' }} />
          <div className="absolute bottom-0 right-1/4 w-64 h-64 rounded-full opacity-15 pointer-events-none"
            style={{ background: 'radial-gradient(circle, #8b5cf6, transparent)', filter: 'blur(60px)', transform: 'translateY(50%)' }} />

          <div className="relative z-10">
            <h2 className="text-2xl sm:text-3xl font-bold text-(--t1) mb-3">Vamos trabalhar juntos?</h2>
            <p className="text-(--t3) mb-8 max-w-lg mx-auto text-sm sm:text-base">
              Estou disponível para projetos freelance, consultorias e oportunidades CLT.
              Vamos criar algo incrível?
            </p>
            <div className="flex flex-col sm:flex-row gap-3 justify-center">
              <motion.div whileHover={{ scale: 1.04 }} whileTap={{ scale: 0.97 }}>
                <Link to="/contact" className="btn-primary">
                  <Mail size={16} /> Entrar em contato
                </Link>
              </motion.div>
              {profile?.linkedinUrl && (
                <motion.div whileHover={{ scale: 1.04 }} whileTap={{ scale: 0.97 }}>
                  <a href={profile.linkedinUrl} target="_blank" rel="noreferrer" className="btn-outline">
                    <ExternalLink size={16} /> LinkedIn
                  </a>
                </motion.div>
              )}
            </div>
          </div>
        </motion.div>
      </section>
    </FadeIn>
  )
}