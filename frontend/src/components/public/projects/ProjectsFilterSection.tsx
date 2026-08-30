import { Search } from 'lucide-react'
import FadeIn from '../../ui/FadeIn'

interface ProjectsFilterSectionProps {
  search: string
  setSearch: (value: string) => void
  activeTag: string | null
  setActiveTag: (tag: string | null) => void
  allTags: string[]
}

export function ProjectsFilterSection({
  search,
  setSearch,
  activeTag,
  setActiveTag,
  allTags,
}: ProjectsFilterSectionProps) {
  return (
    <FadeIn delay={100} className="mb-8 space-y-4">
      <div className="relative max-w-md">
        <Search size={16} className="absolute left-3 top-1/2 -translate-y-1/2 text-(--t4)" />
        <input
          value={search}
          onChange={e => setSearch(e.target.value)}
          placeholder="Buscar projetos..."
          className="input pl-9"
        />
      </div>
      {allTags.length > 0 && (
        <div className="flex flex-wrap gap-2">
          <button
            onClick={() => setActiveTag(null)}
            className={`tag cursor-pointer transition-all ${
              !activeTag ? 'bg-brand-500 text-white border-brand-500' : ''
            }`}
          >
            Todos
          </button>
          {allTags.map(t => (
            <button
              key={t}
              onClick={() => setActiveTag(t === activeTag ? null : t)}
              className={`tag cursor-pointer transition-all ${
                activeTag === t ? 'bg-brand-500 text-white border-brand-500' : ''
              }`}
            >
              {t}
            </button>
          ))}
        </div>
      )}
    </FadeIn>
  )
}