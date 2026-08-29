import { publicApi, adminApi } from "../utils/api";
import type { Experience } from "../types";
export const experienceService = {
  list: () =>
    publicApi.get<Experience[]>("/public/experiences").then((r) => r.data),
  adminList: () =>
    adminApi.get<Experience[]>("/admin/experiences").then((r) => r.data),
  create: (d: Partial<Experience>) =>
    adminApi.post<Experience>("/admin/experiences", d).then((r) => r.data),
  update: (id: number, d: Partial<Experience>) =>
    adminApi.put<Experience>(`/admin/experiences/${id}`, d).then((r) => r.data),
  remove: (id: number) => adminApi.delete(`/admin/experiences/${id}`),
};
