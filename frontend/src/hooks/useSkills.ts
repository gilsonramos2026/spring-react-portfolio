import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { skillService } from '../services/skillService'
import type { Skill } from '../types'
import toast from 'react-hot-toast'
const K = ['skills']
export function useSkills() { return useQuery({ queryKey: K, queryFn: skillService.grouped }) }
export function useAdminSkills() { return useQuery({ queryKey: [...K,'admin'], queryFn: skillService.adminList }) }
export function useCreateSkill() { const qc=useQueryClient(); return useMutation({ mutationFn: (d:Partial<Skill>)=>skillService.create(d), onSuccess:()=>{toast.success('Skill criada!');qc.invalidateQueries({queryKey:K})}, onError:()=>toast.error('Erro.') }) }
export function useUpdateSkill() { const qc=useQueryClient(); return useMutation({ mutationFn: ({id,data}:{id:number;data:Partial<Skill>})=>skillService.update(id,data), onSuccess:()=>{toast.success('Salvo!');qc.invalidateQueries({queryKey:K})}, onError:()=>toast.error('Erro.') }) }
export function useDeleteSkill() { const qc=useQueryClient(); return useMutation({ mutationFn: (id:number)=>skillService.remove(id), onSuccess:()=>{toast.success('Removido!');qc.invalidateQueries({queryKey:K})}, onError:()=>toast.error('Erro.') }) }
