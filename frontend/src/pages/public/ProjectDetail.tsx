import { useState, useCallback } from 'react'
import { useParams, Link } from 'react-router-dom'
import { motion } from 'framer-motion'
import { useProject } from '../../hooks/useProjects'
import { usePageMeta } from '../../hooks/usePageMeta'
import FadeIn from '../../components/ui/FadeIn'
import Spinner from '../../components/ui/Spinner'
import { TechBadge } from '../../components/icons/TechIcon'
import { ArrowLeft } from 'lucide-react'
import type { ProjectImage } from '../../types'
import { ProjectLightbox } from '../../components/public/projectDetails/ProjectLightbox'
import { ProjectGallerySection } from '../../components/public/projectDetails/ProjectGallerySection'
import { ProjectSidebarSection } from '../../components/public/projectDetails/ProjectSidebarSection'

export default function ProjectDetail() {
  const { slug } = useParams<{ slug: string }>()
  const { data: project, isLoading } = useProject(slug ?? '')
  const [lbIndex, setLbIndex] = useState<number | null>(null)

  usePageMeta({
    title: project?.title ?? 'Projeto',
    description: project?.shortDesc,
    image: project?.images?.[0]?.url ?? project?.thumbnailUrl,
    type: 'article',
  })

  const images: ProjectImage[] = project?.images && project.images.length > 0
    ? project.images
    : project?.thumbnailUrl
      ? [{ id: 0, url: project.thumbnailUrl, sortOrder: 0 }]
      : []

  const openLb = (i: number) => setLbIndex(i)
  const closeLb = () => setLbIndex(null)
  const prev = useCallback(() =>
    setLbIndex(i => i === null ? 0 : (i - 1 + images.length) % images.length), [images.length])
  const next = useCallback(() =>
    setLbIndex(i => i === null ? 0 : (i + 1) % images.length), [images.length])

  if (isLoading) return (
    <div className="flex justify-center items-center min-h-[60dvh]">
      <Spinner className="w-9 h-9 text-brand-400" />
    </div>
  )

  if (!project) return (
    <div className="text-center py-24">
      <p className="text-(--t3) text-lg mb-4">Projeto não encontrado.</p>
      <Link to="/projects" className="btn-primary">Ver projetos</Link>
    </div>
  )

  return (
    <>
      <ProjectLightbox images={images} index={lbIndex} onClose={closeLb} onPrev={prev} onNext={next} />

      <div className="max-w-7xl mx-auto px-4 sm:px-6 py-12 sm:py-20">
        {/* Back + header */}
        <FadeIn className="mb-8 sm:mb-12">
          <Link to="/projects"
            className="inline-flex items-center gap-2 text-sm text-(--t4) hover:text-brand-400 transition-colors mb-6 group">
            <motion.span whileHover={{ x: -3 }} className="inline-flex">
              <ArrowLeft size={16} />
            </motion.span>
            Voltar aos projetos
          </Link>
          <h1 className="text-3xl sm:text-4xl font-bold text-(--t1) mb-3">{project.title}</h1>
          <p className="text-lg text-[var(--t3)">{project.shortDesc}</p>
        </FadeIn>

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-10">
          {/* Main content */}
          <div className="lg:col-span-2 space-y-8">
            <ProjectGallerySection images={images} projectTitle={project.title} onOpenLightbox={openLb} />

            {project.description && (
              <FadeIn delay={100}>
                <div className="card p-6 sm:p-8">
                  <h2 className="text-xl font-semibold text-(--t1) mb-4">Sobre o projeto</h2>
                  <p className="text-(--t2) leading-relaxed whitespace-pre-wrap">{project.description}</p>
                </div>
              </FadeIn>
            )}

            {project.tags && project.tags.length > 0 && (
              <FadeIn delay={150}>
                <div className="card p-6">
                  <h3 className="font-semibold text-(--t1) mb-4">Tecnologias usadas</h3>
                  <div className="flex flex-wrap gap-2">
                    {project.tags.map(t => <TechBadge key={t} name={t} />)}
                  </div>
                </div>
              </FadeIn>
            )}
          </div>

          {/* Sidebar */}
          <ProjectSidebarSection project={project} imagesCount={images.length} />
        </div>
      </div>
    </>
  )
}