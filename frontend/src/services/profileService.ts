import { publicApi, adminApi } from '../utils/api'
import type { Profile } from '../types'

export const profileService = {
  get: () => publicApi.get<Profile>('/public/profile').then(r => r.data),

  update: (data: Partial<Profile>) =>
    adminApi.put<Profile>('/admin/profile', data).then(r => r.data),

  /**
   * Upload da foto de perfil via multipart/form-data.
   * NÃO setar Content-Type — o browser gera o boundary automaticamente.
   */
  uploadAvatar: (file: File) => {
    const form = new FormData()
    form.append('file', file)
    return adminApi.post<Profile>('/admin/profile/avatar', form).then(r => r.data)
  },
}
