import { useProfile } from '../../hooks/useProfile'
import { useSkills } from '../../hooks/useSkills'
import { useExperiences } from '../../hooks/useExperiences'
import { useEducations } from '../../hooks/useEducations'
import { useCertifications } from '../../hooks/useCertifications'
import FadeIn from '../../components/ui/FadeIn'
import { usePageMeta } from '../../hooks/usePageMeta'

import { AboutProfileSection } from '../../components/public/about/AboutProfileSection'
import { AboutSkillsSection } from '../../components/public/about/AboutSkillsSection'
import { AboutExperienceSection } from '../../components/public/about/AboutExperienceSection'
import { AboutCertificationsSection } from '../../components/public/about/AboutCertificationsSection'
import { AboutEducationSection } from '../../components/public/about/AboutEducationSection'

export  function AboutPage() {
  const { data: profile, isLoading: loadingProfile } = useProfile()
  usePageMeta({
    title: 'Sobre mim',
    description: profile
      ? `${profile.name} — ${profile.title}. Conheça minha trajetória, habilidades e experiências.`
      : 'Sobre mim — trajetória, habilidades e experiência profissional.',
  })
  const { data: skillsMap } = useSkills()
  const { data: experiences } = useExperiences()
  const { data: educations } = useEducations()
  const { data: certifications } = useCertifications()

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 py-12 sm:py-20">
      <FadeIn className="mb-14">
        <h1 className="text-3xl sm:text-4xl font-bold text-(--t1) mb-3">Sobre mim</h1>
        <p className="text-(--t3) max-w-xl">Conheça minha trajetória, habilidades e formação.</p>
      </FadeIn>
      

      <AboutProfileSection profile={profile} isLoading={loadingProfile} />
      <AboutSkillsSection skillsMap={skillsMap} />
      <AboutExperienceSection experiences={experiences} />
      <AboutEducationSection educations={educations} />
      <AboutCertificationsSection certifications={certifications} /> 
    </div>
  )
}