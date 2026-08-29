import { publicApi, adminApi } from "../utils/api";
import type { Education } from "../types";
export const educationService = {
  list: () =>
    publicApi.get<Education[]>("/public/educations").then((r) => r.data),
  adminList: () =>
    adminApi.get<Education[]>("/admin/educations").then((r) => r.data),
  create: (d: Partial<Education>) =>
    adminApi.post<Education>("/admin/educations", d).then((r) => r.data),
  update: (id: number, d: Partial<Education>) =>
    adminApi.put<Education>(`/admin/educations/${id}`, d).then((r) => r.data),
  remove: (id: number) => adminApi.delete(`/admin/educations/${id}`),
};
