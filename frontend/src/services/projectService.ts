import { publicApi, adminApi } from '../utils/api'
import type { Project, ProjectImage } from '../types'

export const projectService = {
  // ── Public ──────────────────────────────────────────────────
  list: (featured?: boolean) =>
    publicApi.get<Project[]>('/public/projects', {
      params: featured != null ? { featured } : {},
    }).then(r => r.data),

  getBySlug: (slug: string) =>
    publicApi.get<Project>(`/public/projects/${slug}`).then(r => r.data),

  // ── Admin - Projects ─────────────────────────────────────────
  adminList: () =>
    adminApi.get<Project[]>('/admin/projects').then(r => r.data),

  create: (data: Partial<Project>) =>
    adminApi.post<Project>('/admin/projects', data).then(r => r.data),

  update: (id: number, data: Partial<Project>) =>
    adminApi.put<Project>(`/admin/projects/${id}`, data).then(r => r.data),

  remove: (id: number) =>
    adminApi.delete(`/admin/projects/${id}`),

  // ── Admin - Project Images ───────────────────────────────────
  /**
   * Upload de screenshot via multipart/form-data.
   *
   * IMPORTANTE: NÃO setar Content-Type manualmente.
   * O Axios + FormData geram o boundary correto automaticamente.
   * Setar o header manualmente quebra o boundary e o Spring rejeita.
   */
  uploadImage: (projectId: number, file: File, altText?: string) => {
    const form = new FormData()
    form.append('file', file)
    if (altText) form.append('altText', altText)
    // Content-Type omitido intencionalmente — deixar o browser gerar com boundary
    return adminApi.post<ProjectImage>(
      `/admin/projects/${projectId}/images`,
      form
    ).then(r => r.data)
  },

  deleteImage: (projectId: number, imageId: number) =>
    adminApi.delete(`/admin/projects/${projectId}/images/${imageId}`),

  reorderImages: (projectId: number, ids: number[]) =>
    adminApi.put(`/admin/projects/${projectId}/images/reorder`, ids),
}
