import { publicApi, adminApi } from "../utils/api";
import type { Skill } from "../types";
export const skillService = {
  grouped: () =>
    publicApi
      .get<Record<string, Skill[]>>("/public/skills")
      .then((r) => r.data),
  adminList: () => adminApi.get<Skill[]>("/admin/skills").then((r) => r.data),
  create: (d: Partial<Skill>) =>
    adminApi.post<Skill>("/admin/skills", d).then((r) => r.data),
  update: (id: number, d: Partial<Skill>) =>
    adminApi.put<Skill>(`/admin/skills/${id}`, d).then((r) => r.data),
  remove: (id: number) => adminApi.delete(`/admin/skills/${id}`),
};
