import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { contactService } from '../services/contactService'
import type { ContactForm } from '../types'
import toast from "react-hot-toast";
const K = ["contacts"];
export function useAdminContacts(status?: string) {
  return useQuery({
    queryKey: [...K, status],
    queryFn: () => contactService.list(status),
  });
}
export function useContactCount() {
  return useQuery({
    queryKey: [...K, "count"],
    queryFn: contactService.countNew,
    refetchInterval: 30000,
  });
}
export function useSendContact() {
  return useMutation({
    mutationFn: (d: ContactForm) => contactService.send(d),
    onSuccess: () => toast.success("Mensagem enviada! 🚀"),
    onError: () => toast.error("Erro ao enviar."),
  });
}
export function useUpdateContactStatus() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: number; status: string }) =>
      contactService.updateStatus(id, status),
    onSuccess: () => qc.invalidateQueries({ queryKey: K }),
  });
}
