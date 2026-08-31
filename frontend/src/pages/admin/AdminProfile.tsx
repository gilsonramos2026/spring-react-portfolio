import { useEffect, useRef } from 'react'
import { useForm } from 'react-hook-form'
import { useProfile, useUpdateProfile, useUploadAvatar } from '../../hooks/useProfile'
import type { Profile } from '../../types'
import Spinner from '../../components/ui/Spinner'

import { ProfileAvatarSection } from '../../components/admin/profile/ProfileAvatarSection'
import { ProfileForm } from '../../components/admin/profile/ProfileForm'

export function AdminProfile() {
  const { data: profile, isLoading } = useProfile()
  const update = useUpdateProfile()
  const uploadAvatar = useUploadAvatar()
  const fileRef = useRef<HTMLInputElement>(null)

  const { register, handleSubmit, reset } = useForm<Partial<Profile>>()
  useEffect(() => { if (profile) reset(profile) }, [profile, reset])

  const handleAvatarChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0]
    if (!file) return
    const ext = file.name.split('.').pop()?.toLowerCase() ?? ''
    if (!['jpg','jpeg','png','webp'].includes(ext)) {
      return alert('Use JPG, PNG ou WEBP.')
    }
    if (file.size > 5 * 1024 * 1024) {
      return alert('Arquivo deve ter no máximo 5MB.')
    }
    uploadAvatar.mutate(file)
    e.target.value = ''
  }

  if (isLoading) return (
    <div className="flex justify-center py-20">
      <Spinner size={32} className="text-brand-400" />
    </div>
  )

  return (
    <div className="max-w-2xl space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-(--t1)">Perfil</h1>
        <p className="text-(--t3) text-sm mt-0.5">
          Informações exibidas no site público
        </p>
      </div>

      <ProfileAvatarSection
        profile={profile}
        fileRef={fileRef}
        uploadAvatar={uploadAvatar}
        onAvatarChange={handleAvatarChange}
        onRemoveAvatar={() => update.mutate({ avatarUrl: '' })}
      />

      <ProfileForm
        register={register}
        isPending={update.isPending}
        onSubmit={handleSubmit(d => update.mutate(d))}
      />
    </div>
  )
}