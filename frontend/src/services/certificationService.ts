import { publicApi, adminApi } from "../utils/api";
import type { Certification } from "../types";
export const certificationService = {
  list: () =>
    publicApi
      .get<Certification[]>("/public/certifications")
      .then((r) => r.data),
  adminList: () =>
    adminApi.get<Certification[]>("/admin/certifications").then((r) => r.data),
  create: (d: Partial<Certification>) =>
    adminApi
      .post<Certification>("/admin/certifications", d)
      .then((r) => r.data),
  update: (id: number, d: Partial<Certification>) =>
    adminApi
      .put<Certification>(`/admin/certifications/${id}`, d)
      .then((r) => r.data),
  remove: (id: number) => adminApi.delete(`/admin/certifications/${id}`),
};
