import { Link } from 'react-router-dom'
import { TypeAnimation } from 'react-type-animation'
import { motion } from 'framer-motion'
import { Github, Linkedin, Mail, ArrowRight, Download, Code2, Twitter } from 'lucide-react'
import { useProfile } from '../../../hooks/useProfile'
import { resolveAssetUrl } from '../../../utils/api'

const fadeUp = {
  hidden: { opacity: 0, y: 28 },
  visible: (i = 0) => ({ opacity: 1, y: 0, transition: { delay: i * 0.1, duration: 0.55, ease: 'easeOut' } }),
}
const fadeRight = {
  hidden: { opacity: 0, x: 30 },
  visible: { opacity: 1, x: 0, transition: { duration: 0.6, ease: 'easeOut' } },
}

export default function HeroSection() {
  const { data: profile } = useProfile()

  return (
    <section className="min-h-[92dvh] flex items-center py-16 sm:py-20">
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-12 lg:gap-16 items-center w-full">
        
        {/* Left — text */}
        <motion.div
          className="space-y-6 order-2 lg:order-1"
          initial="hidden" animate="visible"
          variants={{ visible: { transition: { staggerChildren: 0.12 } } }}
        >
          <motion.div variants={fadeUp}>
            <span className="inline-flex items-center gap-2 px-3 py-1.5 rounded-full border border-(--bd) bg-(--cb) text-sm text-(--t3)">
              <span className="w-2 h-2 rounded-full bg-emerald-400 animate-pulse" />
              {profile?.available ? 'Disponível para novos projetos' : 'Em projeto no momento'}
            </span>
          </motion.div>

          <motion.div variants={fadeUp}>
            <h1 className="text-4xl sm:text-5xl xl:text-6xl font-bold leading-[1.1] tracking-tight">
              <span className="text-(--t1)">Olá, sou </span>
              <span className="text-gradient">{profile?.name?.split(' ')[0] ?? 'Dev'}</span>
              <br />
              <span className="text-(--t2) text-3xl sm:text-4xl xl:text-5xl">
                <TypeAnimation
                  sequence={[
                    'Desenvolvedor Full Stack', 2500,
                    'Engenheiro de Software', 2500,
                    'Arquiteto de Soluções', 2500,
                  ]}
                  wrapper="span"
                  speed={55}
                  repeat={Infinity}
                />
              </span>
            </h1>
          </motion.div>

          <motion.p variants={fadeUp} className="text-(--t3) text-lg leading-relaxed max-w-xl">
            {profile?.bio ?? 'Criando experiências digitais de alta qualidade com tecnologias modernas.'}
          </motion.p>

          <motion.div variants={fadeUp} className="flex flex-wrap gap-3">
            <Link to="/projects" className="btn-primary">
              Ver projetos <ArrowRight size={16} />
            </Link>
            <Link to="/contact" className="btn-outline">
              Falar comigo <Mail size={16} />
            </Link>
            {profile?.resumeUrl && (
              <a href={resolveAssetUrl(profile.resumeUrl)} target="_blank" rel="noreferrer" className="btn-outline">
                <Download size={16} /> Currículo
              </a>
            )}
          </motion.div>

          <motion.div variants={fadeUp} className="flex items-center gap-1 pt-1">
            {profile?.githubUrl && (
              <motion.a href={profile.githubUrl} target="_blank" rel="noreferrer" whileHover={{ scale: 1.15, y: -2 }} whileTap={{ scale: 0.95 }} className="tap text-(--t4) hover:text-(--t1) transition-colors">
                <Github size={20} />
              </motion.a>
            )}
            {profile?.linkedinUrl && (
              <motion.a href={profile.linkedinUrl} target="_blank" rel="noreferrer" whileHover={{ scale: 1.15, y: -2 }} whileTap={{ scale: 0.95 }} className="tap text-(--t4) hover:text-(--t1) transition-colors">
                <Linkedin size={20} />
              </motion.a>
            )}
            {profile?.twitterUrl && (
              <motion.a href={profile.twitterUrl} target="_blank" rel="noreferrer" whileHover={{ scale: 1.15, y: -2 }} whileTap={{ scale: 0.95 }} className="tap text-(--t4) hover:text-(--t1) transition-colors">
                <Twitter size={20} />
              </motion.a>
            )}
            {profile?.email && (
              <motion.a href={`mailto:${profile.email}`} whileHover={{ scale: 1.15, y: -2 }} whileTap={{ scale: 0.95 }} className="tap text-(--t4) hover:text-(--t1) transition-colors">
                <Mail size={20} />
              </motion.a>
            )}
          </motion.div>
        </motion.div>

        {/* Right — avatar */}
        <motion.div className="order-1 lg:order-2 flex justify-center lg:justify-end" variants={fadeRight} initial="hidden" animate="visible">
          <div className="relative">
            <motion.div className="absolute inset-0 rounded-full" style={{ background: 'radial-gradient(circle, rgba(14,165,233,0.25) 0%, transparent 70%)' }} animate={{ scale: [1, 1.08, 1] }} transition={{ duration: 4, repeat: Infinity, ease: 'easeInOut' }} />

            <motion.div animate={{ y: [0, -14, 0] }} transition={{ duration: 5, repeat: Infinity, ease: 'easeInOut' }} className="relative z-10">
              {profile?.avatarUrl ? (
                <img src={resolveAssetUrl(profile.avatarUrl)} alt={profile.name} className="w-52 h-52 sm:w-64 sm:h-64 lg:w-72 lg:h-72 rounded-3xl object-cover border-2 border-[rgba(14,165,233,0.3)] shadow-2xl" style={{ boxShadow: '0 0 60px rgba(14,165,233,0.2), 0 25px 50px rgba(0,0,0,0.4)' }} />
              ) : (
                <div className="w-52 h-52 sm:w-64 sm:h-64 lg:w-72 lg:h-72 rounded-3xl border-2 border-[rgba(14,165,233,0.3)] shadow-2xl flex items-center justify-center" style={{ background: 'linear-gradient(135deg, rgba(14,165,233,0.2), rgba(139,92,246,0.2))', boxShadow: '0 0 60px rgba(14,165,233,0.2), 0 25px 50px rgba(0,0,0,0.4)' }}>
                  <Code2 size={72} className="text-brand-400 opacity-60" />
                </div>
              )}

              {profile?.yearsExp && (
                <motion.div initial={{ opacity: 0, scale: 0.5, x: 20 }} animate={{ opacity: 1, scale: 1, x: 0 }} transition={{ delay: 0.8, type: 'spring', stiffness: 200 }} className="absolute -bottom-4 -right-4 bg-brand-500 text-white px-3 py-2 rounded-2xl shadow-lg text-center" style={{ boxShadow: '0 8px 24px rgba(14,165,233,0.4)' }}>
                  <p className="text-xl font-bold leading-none">{profile.yearsExp}+</p>
                  <p className="text-xs opacity-90 mt-0.5">anos exp.</p>
                </motion.div>
              )}

              {profile?.available && (
                <motion.div initial={{ opacity: 0, scale: 0.5, x: -20 }} animate={{ opacity: 1, scale: 1, x: 0 }} transition={{ delay: 1, type: 'spring', stiffness: 200 }} className="absolute -top-4 -left-4 bg-(--surface-2) border border-emerald-500/30 text-emerald-400 px-3 py-2 rounded-2xl shadow-lg text-xs font-semibold">
                  <span className="flex items-center gap-1.5">
                    <span className="w-1.5 h-1.5 rounded-full bg-emerald-400 animate-pulse" />
                    Disponível
                  </span>
                </motion.div>
              )}
            </motion.div>
          </div>
        </motion.div>
      </div>
    </section>
  )
}