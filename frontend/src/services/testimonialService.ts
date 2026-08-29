import { publicApi, adminApi } from "../utils/api";
import type { Testimonial } from "../types";
export const testimonialService = {
  list: (featured?: boolean) =>
    publicApi
      .get<Testimonial[]>("/public/testimonials", {
        params: featured != null ? { featured } : {},
      })
      .then((r) => r.data),
  adminList: () =>
    adminApi.get<Testimonial[]>("/admin/testimonials").then((r) => r.data),
  create: (d: Partial<Testimonial>) =>
    adminApi.post<Testimonial>("/admin/testimonials", d).then((r) => r.data),
  update: (id: number, d: Partial<Testimonial>) =>
    adminApi
      .put<Testimonial>(`/admin/testimonials/${id}`, d)
      .then((r) => r.data),
  remove: (id: number) => adminApi.delete(`/admin/testimonials/${id}`),
};
