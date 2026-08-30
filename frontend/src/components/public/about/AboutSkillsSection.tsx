import FadeIn from '../../ui/FadeIn'
import type { Skill } from '../../../types'
import { SkillIcon } from '../../icons/TechIcon'

interface AboutSkillsSectionProps {
  skillsMap: Record<string, Skill[]> | undefined
}

export function AboutSkillsSection({ skillsMap }: AboutSkillsSectionProps) {
  if (!skillsMap || Object.keys(skillsMap).length === 0) {
    return null
  }

  return (
    <section className="mb-16 border-t border-(--bd) pt-12">
      <FadeIn>
        <h2 className="text-2xl font-bold text-(--t1) mb-8 flex items-center gap-2">
          <span className="text-gradient">Skills</span>
        </h2>
      </FadeIn>
      <div className="grid grid-cols-1 sm:grid-cols-2 gap-6">
        {Object.entries(skillsMap).map(([cat, skills], ci) => (
          <FadeIn key={cat} delay={ci * 80}>
            <div className="card p-5 sm:p-6">
              <h3 className="text-sm font-semibold text-(--t4) uppercase tracking-wide mb-4">{cat}</h3>
              <div className="space-y-3">
                {skills.map(s => (
                  <div key={s.id}>
                    <div className="flex items-center justify-between mb-1.5">
                      <div className="flex items-center gap-2">
                        <SkillIcon name={s.iconName ?? s.name} size={18}/>
                        <span className="text-sm font-medium text-(--t2)">{s.name}</span>
                      </div>
                      <span className="text-xs text-(--t4)">{s.proficiency}%</span>
                    </div>
                    <div className="skill-bar">
                      <div className="skill-bar-fill" style={{ width: `${s.proficiency}%` }}/>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          </FadeIn>
        ))}
      </div>
    </section>
  )
}