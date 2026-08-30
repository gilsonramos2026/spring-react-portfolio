import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { profileService } from '../services/profileService'
import type { Profile } from '../types'
import toast from 'react-hot-toast'

export function useProfile() {
  return useQuery({ queryKey: ['profile'], queryFn: profileService.get })
}

export function useUpdateProfile() {
  const qc = useQueryClient()
  return useMutation({
    mutationFn: (data: Partial<Profile>) => profileService.update(data),
    onSuccess: () => {
      toast.success('Perfil salvo!')
      qc.invalidateQueries({ queryKey: ['profile'] })
    },
    onError: () => toast.error('Erro ao salvar perfil.'),
  })
}

export function useUploadAvatar() {
  const qc = useQueryClient()
  return useMutation({
    mutationFn: (file: File) => profileService.uploadAvatar(file),
    onSuccess: () => {
      toast.success('Foto de perfil atualizada!')
      qc.invalidateQueries({ queryKey: ['profile'] })
    },
    onError: () => toast.error('Erro ao enviar foto. Use JPG, PNG ou WEBP até 5MB.'),
  })
}
