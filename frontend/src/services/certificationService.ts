export const  certificationService = {
   list: () => publicApi.get<Certification[]>('/public/certifications').then((response) => response.data),
}