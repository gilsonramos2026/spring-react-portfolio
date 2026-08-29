import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { certificationService } from "../services/certificationService";
import type { Certification } from "../types";
import toast from "react-hot-toast";

const K = ["certifications"];

export function useCertifications() {
  return useQuery({
    queryKey: K,
    queryFn: certificationService.list,
  });
}

export function useAdminCertifications() {
  return useQuery({
    queryKey: [...K, "admin"],
    queryFn: certificationService.adminList,
  });
}

export function useCreateCertification() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (d: Partial<Certification>) => certificationService.create(d),
    onSuccess: () => {
      toast.success("Criado!");
      qc.invalidateQueries({ queryKey: K });
    },
    onError: () => toast.error("Erro."),
  });
}

export function useUpdateCertification() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: ({ id, data }: { id: number; data: Partial<Certification> }) =>
      certificationService.update(id, data),
    onSuccess: () => {
      toast.success("Salvo!");
      qc.invalidateQueries({ queryKey: K });
    },
    onError: () => toast.error("Erro."),
  });
}
export function useDeleteCertification() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (id: number) => certificationService.remove(id),
    onSuccess: () => {
      toast.success("Removido!");
      qc.invalidateQueries({ queryKey: K });
    },
    onError: () => toast.error("Erro."),
  });
}
