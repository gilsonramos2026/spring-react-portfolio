export  interface Testimonial {
  id: number;
  name: string;
  role?: string;
  company?: string;
  content: string;
  avatarUrl?: string;
  rating?: number; // e.g., 1-5 scale
  featured?: boolean; // Indicates if this testimonial is featured
  sortOrder?: number; // For ordering testimonials in a list
}