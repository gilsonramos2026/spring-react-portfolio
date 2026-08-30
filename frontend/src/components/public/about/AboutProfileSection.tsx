import { resolveAssetUrl } from '../../../utils/api'
import FadeIn from '../../ui/FadeIn'
import { MapPin, Mail } from 'lucide-react'
import type { Profile } from '../../../types' // Ajuste o caminho se necessário
import { SkeletonProfile } from '../../ui/SkeletonBox'

interface AboutProfileSectionProps {
  profile: Profile | undefined
  isLoading: boolean
}

export function AboutProfileSection({ profile, isLoading }: AboutProfileSectionProps) {
  return (
    <>
      {isLoading && <SkeletonProfile />}
      {profile && (
        <FadeIn className="grid grid-cols-1 sm:grid-cols-3 gap-8 mb-16">
          <div className="sm:col-span-1 flex flex-col items-center sm:items-start gap-4">
            {profile.avatarUrl ? (
              <img src={resolveAssetUrl(profile.avatarUrl)} alt={profile.name}
                className="w-32 h-32 rounded-2xl object-cover border-2 border-(--bd)"/>
            ) : (
              <div className="w-32 h-32 rounded-2xl bg-linear-to-br from-brand-500 to-accent-500 flex items-center justify-center text-white text-4xl font-bold">
                {profile.name[0]}
              </div>
            )}
            <div className="space-y-2 text-center sm:text-left">
              {profile.location && <p className="flex items-center gap-1.5 text-sm text-(--t3)"><MapPin size={13}/>{profile.location}</p>}
              {profile.email && <a href={`mailto:${profile.email}`} className="flex items-center gap-1.5 text-sm text-(--t3) hover:text-brand-400 transition-colors"><Mail size={13}/>{profile.email}</a>}
              {profile.available && <span className="inline-flex items-center gap-1.5 text-xs text-emerald-400 font-semibold"><span className="w-1.5 h-1.5 rounded-full bg-emerald-400 animate-pulse"/>Disponível</span>}
            </div>
          </div>
          <div className="sm:col-span-2">
            <h2 className="text-xl font-semibold text-(--t1) mb-1">{profile.name}</h2>
            <p className="text-brand-400 font-medium mb-4">{profile.title}</p>
            <p className="text-(--t2) leading-relaxed">{profile.bio}</p>
          </div>
        </FadeIn>
      )}
    </>
  )
}