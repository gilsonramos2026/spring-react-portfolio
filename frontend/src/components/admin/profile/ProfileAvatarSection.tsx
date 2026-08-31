import type { UseMutationResult } from '@tanstack/react-query'
import { resolveAssetUrl } from '../../../utils/api'
import { Camera, User, Loader2 } from 'lucide-react'
import type { Profile } from '../../../types'

interface ProfileAvatarSectionProps {
  profile: Profile | undefined
  fileRef: React.RefObject<HTMLInputElement | null>
  uploadAvatar: UseMutationResult<Profile, Error, File, unknown>
  onAvatarChange: (e: React.ChangeEvent<HTMLInputElement>) => void
  onRemoveAvatar: () => void
}

export function ProfileAvatarSection({
  profile,
  fileRef,
  uploadAvatar,
  onAvatarChange,
  onRemoveAvatar,
}: ProfileAvatarSectionProps) {
  return (
    <div className="card p-5 sm:p-6">
      <p className="text-sm font-semibold text-(--t2) mb-4">Foto de perfil</p>
      <div className="flex items-center gap-5">
        {/* Preview */}
        <div className="relative shrink-0">
          {profile?.avatarUrl ? (
            <img
              src={resolveAssetUrl(profile.avatarUrl)}
              alt={profile.name}
              className="w-20 h-20 rounded-2xl object-cover border-2 border-(--bd)"
            />
          ) : (
            <div className="w-20 h-20 rounded-2xl bg-linear-to-br from-[rgba(14,165,233,0.2)] to-[rgba(139,92,246,0.2)] border-2 border-(--bd) flex items-center justify-center">
              <User size={28} className="text-(--t4)" />
            </div>
          )}

          {/* Camera button overlay */}
          <button
            type="button"
            onClick={() => fileRef.current?.click()}
            disabled={uploadAvatar.isPending}
            className="absolute -bottom-2 -right-2 w-7 h-7 rounded-full bg-brand-500 hover:bg-brand-400 border-2 border-(--s2) flex items-center justify-center transition-colors disabled:opacity-60"
            aria-label="Alterar foto"
          >
            {uploadAvatar.isPending ? (
              <Loader2 size={12} className="text-white animate-spin" />
            ) : (
              <Camera size={12} className="text-white" />
            )}
          </button>
        </div>

        {/* Info + actions */}
        <div className="flex-1 min-w-0">
          <p className="text-sm font-medium text-(--t1) mb-0.5">
            {profile?.avatarUrl ? 'Foto carregada' : 'Nenhuma foto'}
          </p>
          <p className="text-xs text-(--t4) mb-3">
            JPG, PNG ou WEBP · máx. 5MB · recomendado 400×400px ou maior
          </p>
          <div className="flex flex-wrap gap-2">
            <button
              type="button"
              onClick={() => fileRef.current?.click()}
              disabled={uploadAvatar.isPending}
              className="btn-primary text-xs py-1.5 px-3 gap-1.5"
            >
              {uploadAvatar.isPending ? (
                <>
                  <Loader2 size={12} className="animate-spin" /> Enviando…
                </>
              ) : (
                <>
                  <Camera size={12} /> {profile?.avatarUrl ? 'Trocar foto' : 'Enviar foto'}
                </>
              )}
            </button>
            {profile?.avatarUrl && (
              <button
                type="button"
                onClick={onRemoveAvatar}
                className="btn-outline text-xs py-1.5 px-3 text-red-400 border-red-400/30 hover:bg-red-500/10 hover:border-red-400"
              >
                Remover
              </button>
            )}
          </div>
        </div>

        <input
          ref={fileRef}
          type="file"
          accept="image/jpeg,image/png,image/webp"
          className="hidden"
          onChange={onAvatarChange}
        />
      </div>
    </div>
  )
}