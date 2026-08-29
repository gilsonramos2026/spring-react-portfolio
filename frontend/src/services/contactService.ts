import { publicApi, adminApi } from "../utils/api";
import type { Contact, ContactForm } from "../types";
export const contactService = {
  send: (d: ContactForm) =>
    publicApi.post("/public/contact", d).then((r) => r.data),
  list: (status?: string) =>
    adminApi
      .get<Contact[]>("/admin/contacts", { params: status ? { status } : {} })
      .then((r) => r.data),
  updateStatus: (id: number, status: string) =>
    adminApi
      .patch<Contact>(`/admin/contacts/${id}/status`, { status })
      .then((r) => r.data),
  countNew: () =>
    adminApi
      .get<{ count: number }>("/admin/contacts/count-new")
      .then((r) => r.data),
};