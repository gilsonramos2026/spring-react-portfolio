import { useState } from 'react'
import { usePublicProjects } from '../../hooks/useProjects'
import FadeIn from '../../components/ui/FadeIn'
import { usePageMeta } from '../../hooks/usePageMeta'
import { ProjectsFilterSection } from '../../components/public/projects/ProjectsFilterSection'
import { ProjectCardItem } from '../../components/public/projects/ProjectCardItem'
import { SkeletonProjectGrid } from '../../components/ui/SkeletonBox'

export default function ProjectsPage() {
  usePageMeta({
    title: 'Projetos',
    description: 'Confira os projetos que desenvolvi — aplicações web, APIs e sistemas completos.',
  })
  const { data: projects, isLoading } = usePublicProjects()
  const [search, setSearch] = useState('')
  const [activeTag, setActiveTag] = useState<string | null>(null)

  const allTags = Array.from(new Set(projects?.flatMap(p => p.tags ?? []) ?? [])).sort()

  const filtered = projects?.filter(p => {
    const matchSearch = !search || p.title.toLowerCase().includes(search.toLowerCase()) || p.shortDesc.toLowerCase().includes(search.toLowerCase())
    const matchTag = !activeTag || p.tags?.includes(activeTag)
    return matchSearch && matchTag
  })

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 py-12 sm:py-20">
      <FadeIn className="mb-10">
        <h1 className="text-3xl sm:text-4xl font-bold text-(--t1) mb-3">Projetos</h1>
        <p className="text-(--t3) max-w-xl">Trabalhos que desenvolvi ao longo da minha carreira.</p>
      </FadeIn>

      <ProjectsFilterSection
        search={search}
        setSearch={setSearch}
        activeTag={activeTag}
        setActiveTag={setActiveTag}
        allTags={allTags}
      />

      {isLoading && <SkeletonProjectGrid count={6} />}

      {filtered && (
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
          {filtered.map((p, i) => (
            <FadeIn key={p.id} delay={i * 60}>
              <ProjectCardItem project={p} />
            </FadeIn>
          ))}
        </div>
      )}

      {filtered?.length === 0 && !isLoading && (
        <div className="text-center py-20">
          <p className="text-(--t3) text-lg">Nenhum projeto encontrado.</p>
          <button onClick={() => { setSearch(''); setActiveTag(null) }} className="btn-outline mt-4">Limpar filtros</button>
        </div>
      )}
    </div>
  )
}