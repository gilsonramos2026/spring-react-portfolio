import { useAdminProjects } from '../../hooks/useProjects'
import { useAdminSkills } from '../../hooks/useSkills'
import { useAdminExperiences } from '../../hooks/useExperiences'
import { useAdminCertifications } from '../../hooks/useCertifications'
import { useAdminTestimonials } from '../../hooks/useTestimonials'
import { FolderKanban, Zap, Briefcase, Award, Star, MessageSquare } from 'lucide-react'

import { DashboardHeader } from '../../components/admin/dashboard/DashboardHeader'
import { DashboardAlert } from '../../components/admin/dashboard/DashboardAlert'
import { DashboardStatsGrid } from '../../components/admin/dashboard/DashboardStatsGrid'
import { DashboardRecentContacts } from '../../components/admin/dashboard/DashboardRecentContacts'
import { useAdminContacts, useContactCount } from '../../hooks/useContact'

export function AdminDashboard() {
  const { data: projects } = useAdminProjects()
  const { data: skills } = useAdminSkills()
  const { data: experiences } = useAdminExperiences()
  const { data: certifications } = useAdminCertifications()
  const { data: testimonials } = useAdminTestimonials()
  const { data: contacts } = useAdminContacts()
  const { data: newCount } = useContactCount()

  const stats = [
    { label: 'Projetos', value: projects?.length ?? 0, Icon: FolderKanban, to: '/admin/projects', color: 'text-[var(--color-brand-400)]' },
    { label: 'Skills', value: skills?.length ?? 0, Icon: Zap, to: '/admin/skills', color: 'text-purple-400' },
    { label: 'Experiências', value: experiences?.length ?? 0, Icon: Briefcase, to: '/admin/experiences', color: 'text-emerald-400' },
    { label: 'Certificações', value: certifications?.length ?? 0, Icon: Award, to: '/admin/certifications', color: 'text-amber-400' },
    { label: 'Testemunhos', value: testimonials?.length ?? 0, Icon: Star, to: '/admin/testimonials', color: 'text-pink-400' },
    { label: 'Contatos', value: contacts?.length ?? 0, Icon: MessageSquare, to: '/admin/contacts', color: 'text-orange-400' },
  ]

  return (
    <div className="space-y-8">
      <DashboardHeader />
      <DashboardAlert newCount={newCount} />
      <DashboardStatsGrid stats={stats} />
      <DashboardRecentContacts contacts={contacts} />
    </div>
  )
}