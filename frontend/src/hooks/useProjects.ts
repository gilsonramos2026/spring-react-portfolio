import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { projectService } from '../services/projectService'
import type { Project } from '../types'
import toast from 'react-hot-toast'
const K = ['projects']
export function usePublicProjects(featured?: boolean) { return useQuery({ queryKey: [...K,{featured}], queryFn: () => projectService.list(featured) }) }
export function useProject(slug: string) { return useQuery({ queryKey: [...K,slug], queryFn: () => projectService.getBySlug(slug), enabled: !!slug }) }
export function useAdminProjects() { return useQuery({ queryKey: [...K,'admin'], queryFn: projectService.adminList }) }
export function useCreateProject() {
  const qc = useQueryClient()
  return useMutation({ mutationFn: (d: Partial<Project>) => projectService.create(d),
    onSuccess: () => { toast.success('Projeto criado!'); qc.invalidateQueries({ queryKey: K }) },
    onError: () => toast.error('Erro ao criar projeto.')
  })
}
export function useUpdateProject() {
  const qc = useQueryClient()
  return useMutation({ mutationFn: ({ id, data }: { id: number; data: Partial<Project> }) => projectService.update(id, data),
    onSuccess: () => { toast.success('Projeto salvo!'); qc.invalidateQueries({ queryKey: K }) },
    onError: () => toast.error('Erro ao salvar projeto.')
  })
}
export function useDeleteProject() {
  const qc = useQueryClient()
  return useMutation({ mutationFn: (id: number) => projectService.remove(id),
    onSuccess: () => { toast.success('Removido!'); qc.invalidateQueries({ queryKey: K }) },
    onError: () => toast.error('Erro ao remover.')
  })
}
export function useUploadProjectImage() {
  const qc = useQueryClient()
  return useMutation({ mutationFn: ({ projectId, file, altText }: { projectId: number; file: File; altText?: string }) => projectService.uploadImage(projectId, file, altText),
    onSuccess: () => { toast.success('Imagem enviada!'); qc.invalidateQueries({ queryKey: K }) },
    onError: () => toast.error('Erro ao enviar imagem. Verifique formato e tamanho (máx 5MB).')
  })
}
export function useDeleteProjectImage() {
  const qc = useQueryClient()
  return useMutation({ mutationFn: ({ projectId, imageId }: { projectId: number; imageId: number }) => projectService.deleteImage(projectId, imageId),
    onSuccess: () => { toast.success('Imagem removida!'); qc.invalidateQueries({ queryKey: K }) },
    onError: () => toast.error('Erro ao remover imagem.')
  })
}
