import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { testimonialService } from "../services/testimonialService";
import type { Testimonial } from "../types";
import toast from "react-hot-toast";
const K = ["testimonials"];
export function useTestimonials(featured?: boolean) {
  return useQuery({
    queryKey: [...K, { featured }],
    queryFn: () => testimonialService.list(featured),
  });
}
export function useAdminTestimonials() {
  return useQuery({
    queryKey: [...K, "admin"],
    queryFn: testimonialService.adminList,
  });
}
export function useCreateTestimonial() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (d: Partial<Testimonial>) => testimonialService.create(d),
    onSuccess: () => {
      toast.success("Criado!");
      qc.invalidateQueries({ queryKey: K });
    },
    onError: () => toast.error("Erro."),
  });
}
export function useUpdateTestimonial() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: ({ id, data }: { id: number; data: Partial<Testimonial> }) =>
      testimonialService.update(id, data),
    onSuccess: () => {
      toast.success("Salvo!");
      qc.invalidateQueries({ queryKey: K });
    },
    onError: () => toast.error("Erro."),
  });
}
export function useDeleteTestimonial() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (id: number) => testimonialService.remove(id),
    onSuccess: () => {
      toast.success("Removido!");
      qc.invalidateQueries({ queryKey: K });
    },
    onError: () => toast.error("Erro."),
  });
}
