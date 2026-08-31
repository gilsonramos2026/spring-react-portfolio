import type { Skill } from '../../../types'
import { SkillIcon } from '../../icons/TechIcon'
import { Pencil, Trash2 } from 'lucide-react'

interface SkillsTableProps {
  skills: Skill[] | undefined
  categories: string[]
  onOpen: (skill: Skill) => void
  onDelete: (id: number) => void
}

export function SkillsTable({ skills, categories, onOpen, onDelete }: SkillsTableProps) {
  return (
    <>
      {categories.map(cat => (
        <div key={cat} className="card overflow-hidden">
          <div className="px-5 py-3 border-b border-(--bd) bg-(--cb)">
            <h3 className="font-semibold text-sm text-(--t2)">{cat}</h3>
          </div>
          <div className="overflow-x-auto">
            <table className="w-full admin-table min-w-120">
              <thead>
                <tr>
                  <th>Skill</th>
                  <th>Proficiência</th>
                  <th>Ações</th>
                </tr>
              </thead>
              <tbody>
                {skills?.filter(s => s.category === cat).map(s => (
                  <tr key={s.id}>
                    <td>
                      <div className="flex items-center gap-2">
                        <SkillIcon name={s.iconName ?? s.name} size={20}/>
                        <span className="font-medium text-(--t1)">{s.name}</span>
                      </div>
                    </td>
                    <td>
                      <div className="flex items-center gap-3">
                        <div className="skill-bar w-24">
                          <div className="skill-bar-fill" style={{ width:`${s.proficiency}%` }}/>
                        </div>
                        <span className="text-xs text-(--t4)">{s.proficiency}%</span>
                      </div>
                    </td>
                    <td>
                      <div className="flex gap-1">
                        <button onClick={() => onOpen(s)} className="tap text-(--t4) hover:text-brand-400 transition-colors">
                          <Pencil size={14}/>
                        </button>
                        <button onClick={() => onDelete(s.id)} className="tap text-(--t4) hover:text-red-400 transition-colors">
                          <Trash2 size={14}/>
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      ))}
    </>
  )
}