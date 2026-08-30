import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { experienceService } from "../services/experienceService";
import type { Experience } from "../types";
import toast from "react-hot-toast";
const K = ["experiences"];
export function useExperiences() {
  return useQuery({ queryKey: K, queryFn: experienceService.list });
}
export function useAdminExperiences() {
  return useQuery({
    queryKey: [...K, "admin"],
    queryFn: experienceService.adminList,
  });
}
export function useCreateExperience() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (d: Partial<Experience>) => experienceService.create(d),
    onSuccess: () => {
      toast.success("Criado!");
      qc.invalidateQueries({ queryKey: K });
    },
    onError: () => toast.error("Erro."),
  });
}
export function useUpdateExperience() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: ({ id, data }: { id: number; data: Partial<Experience> }) =>
      experienceService.update(id, data),
    onSuccess: () => {
      toast.success("Salvo!");
      qc.invalidateQueries({ queryKey: K });
    },
    onError: () => toast.error("Erro."),
  });
}
export function useDeleteExperience() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (id: number) => experienceService.remove(id),
    onSuccess: () => {
      toast.success("Removido!");
      qc.invalidateQueries({ queryKey: K });
    },
    onError: () => toast.error("Erro."),
  });
}
