import { useProfile } from '../../../hooks/useProfile'
import FadeIn from '../../ui/FadeIn'
import { Mail, Phone, MapPin, Github, Linkedin } from 'lucide-react'

export function ContactInfoSidebar() {
  const { data: profile, isLoading } = useProfile()

  return (
    <FadeIn delay={150} className="space-y-4">
      {/* Informações de contato */}
      <div className="card p-5 space-y-4">
        <h3 className="font-semibold text-(--t1)">Informações</h3>
        
        {isLoading ? (
          <p className="text-xs text-(--t3)">Carregando informações...</p>
        ) : !profile ? (
          <p className="text-xs text-(--t3)">Nenhum perfil configurado no painel.</p>
        ) : (
          <>
            {profile.email && (
              <a href={`mailto:${profile.email}`} className="flex items-center gap-3 text-sm text-(--t2) hover:text-brand-400 transition-colors">
                <span className="w-8 h-8 rounded-lg bg-brand-500/15 flex items-center justify-center shrink-0">
                  <Mail size={14} className="text-brand-400"/>
                </span>
                {profile.email}
              </a>
            )}
            {profile.phone && (
              <a href={`tel:${profile.phone}`} className="flex items-center gap-3 text-sm text-(--t2) hover:text-(--t1) transition-colors">
                <span className="w-8 h-8 rounded-lg bg-brand-500/15 flex items-center justify-center shrink-0">
                  <Phone size={14} className="text-brand-400"/>
                </span>
                {profile.phone}
              </a>
            )}
            {profile.location && (
              <div className="flex items-center gap-3 text-sm text-(--t3)">
                <span className="w-8 h-8 rounded-lg bg-brand-500/15 flex items-center justify-center shrink-0">
                  <MapPin size={14} className="text-brand-400"/>
                </span>
                {profile.location}
              </div>
            )}
          </>
        )}
      </div>

      {/* Redes sociais */}
      <div className="card p-5 space-y-3">
        <h3 className="font-semibold text-(--t1)">Redes sociais</h3>
        {profile?.githubUrl && (
          <a href={profile.githubUrl} target="_blank" rel="noreferrer" className="flex items-center gap-2 text-sm text-(--t2) hover:text-(--t1) transition-colors">
            <Github size={15}/> GitHub
          </a>
        )}
        {profile?.linkedinUrl && (
          <a href={profile.linkedinUrl} target="_blank" rel="noreferrer" className="flex items-center gap-2 text-sm text-(--t2) hover:text-brand-400 transition-colors">
            <Linkedin size={15}/> LinkedIn
          </a>
        )}
      </div>

      {/* Disponibilidade */}
      {profile?.available && (
        <div className="card p-5 bg-linear-to-br from-emerald-500/10 to-transparent border-emerald-500/20">
          <div className="flex items-center gap-2 mb-2">
            <span className="w-2 h-2 rounded-full bg-emerald-400 animate-pulse"/>
            <span className="text-emerald-400 font-semibold text-sm">Disponível</span>
          </div>
          <p className="text-xs text-(--t3)">Aberto para projetos freelance, consultorias e oportunidades CLT.</p>
        </div>
      )}
    </FadeIn>
  )
}