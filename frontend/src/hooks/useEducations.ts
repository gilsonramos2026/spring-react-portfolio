import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { educationService } from "../services/educationService";
import type { Education } from "../types";
import toast from "react-hot-toast";
const K = ["educations"];
export function useEducations() {
  return useQuery({ queryKey: K, queryFn: educationService.list });
}
export function useAdminEducations() {
  return useQuery({
    queryKey: [...K, "admin"],
    queryFn: educationService.adminList,
  });
}
export function useCreateEducation() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (d: Partial<Education>) => educationService.create(d),
    onSuccess: () => {
      toast.success("Criado!");
      qc.invalidateQueries({ queryKey: K });
    },
    onError: () => toast.error("Erro."),
  });
}
export function useUpdateEducation() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: ({ id, data }: { id: number; data: Partial<Education> }) =>
      educationService.update(id, data),
    onSuccess: () => {
      toast.success("Salvo!");
      qc.invalidateQueries({ queryKey: K });
    },
    onError: () => toast.error("Erro."),
  });
}
export function useDeleteEducation() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (id: number) => educationService.remove(id),
    onSuccess: () => {
      toast.success("Removido!");
      qc.invalidateQueries({ queryKey: K });
    },
    onError: () => toast.error("Erro."),
  });
}
