import { Link } from 'react-router-dom'
import { motion } from 'framer-motion'
import { ArrowRight, ExternalLink, Github, Code2 } from 'lucide-react'
import { usePublicProjects } from '../../../hooks/useProjects'
import FadeIn from '../../ui/FadeIn'
import { resolveAssetUrl } from '../../../utils/api'
import { TechBadge } from '../../icons/TechIcon'


export  function ProjectsSection() {
  const { data: projects } = usePublicProjects(true)

  if (!projects || projects.length === 0) return null

  return (
    <section className="py-14 border-t border-(--bd)">
      <FadeIn className="flex items-end justify-between mb-10">
        <div>
          <p className="text-xs text-brand-400 font-semibold uppercase tracking-widest mb-2">Portfólio</p>
          <h2 className="text-2xl sm:text-3xl font-bold text-(--t1)">Projetos em destaque</h2>
          <p className="text-(--t3) mt-1 text-sm">Alguns trabalhos que me orgulho</p>
        </div>
        <Link to="/projects" className="btn-outline text-sm hidden sm:inline-flex shrink-0">
          Ver todos <ArrowRight size={14} />
        </Link>
      </FadeIn>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {projects.slice(0, 6).map((p, i) => (
          <motion.div
            key={p.id}
            initial={{ opacity: 0, y: 30 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true, margin: '-50px' }}
            transition={{ delay: i * 0.1, duration: 0.5 }}
            whileHover={{ y: -6 }}
          >
            <Link to={`/projects/${p.slug}`} className="card group overflow-hidden h-full flex flex-col">
              <div className="aspect-video overflow-hidden bg-(--s2) relative">
                {(p.images?.[0]?.url || p.thumbnailUrl) ? (
                  <>
                    <img
                      src={resolveAssetUrl(p.images?.[0]?.url ?? p.thumbnailUrl)}
                      alt={p.title}
                      className="w-full h-full object-cover transition-transform duration-700 group-hover:scale-110"
                    />
                    {p.images && p.images.length > 1 && (
                      <span className="absolute bottom-2 right-2 bg-black/60 text-white text-xs px-2 py-0.5 rounded-full">
                        +{p.images.length - 1} fotos
                      </span>
                    )}
                    <div className="absolute inset-0 bg-linear-to-t from-black/50 to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-300 flex items-end p-4">
                      <span className="text-white text-sm font-medium flex items-center gap-1">
                        Ver detalhes <ArrowRight size={14} />
                      </span>
                    </div>
                  </>
                ) : (
                  <div className="w-full h-full flex items-center justify-center">
                    <Code2 size={40} className="text-(--t5)" />
                  </div>
                )}
              </div>

              <div className="p-5 flex flex-col flex-1 gap-3">
                <h3 className="font-semibold text-(--t1) group-hover:text-brand-400 transition-colors line-clamp-1">
                  {p.title}
                </h3>
                <p className="text-sm text-(--t3) line-clamp-2 flex-1">{p.shortDesc}</p>
                <div className="flex flex-wrap gap-1.5">
                  {p.tags?.slice(0, 3).map(t => <TechBadge key={t} name={t} size={13} />)}
                  {(p.tags?.length ?? 0) > 3 && (
                    <span className="chip">+{p.tags!.length - 3}</span>
                  )}
                </div>
                <div className="flex gap-3 pt-1 text-xs text-(--t4)">
                  {p.githubUrl && (
                    <span onClick={e => { e.preventDefault(); window.open(p.githubUrl!, '_blank') }}
                      className="flex items-center gap-1 hover:text-(--t1) transition-colors cursor-pointer">
                      <Github size={13} /> Código
                    </span>
                  )}
                  {p.demoUrl && (
                    <span onClick={e => { e.preventDefault(); window.open(p.demoUrl!, '_blank') }}
                      className="flex items-center gap-1 hover:text-brand-400) transition-colors cursor-pointer">
                      <ExternalLink size={13} /> Demo
                    </span>
                  )}
                </div>
              </div>
            </Link>
          </motion.div>
        ))}
      </div>

      <div className="text-center mt-8 sm:hidden">
        <Link to="/projects" className="btn-outline">
          Ver todos os projetos <ArrowRight size={14} />
        </Link>
      </div>
    </section>
  )
}