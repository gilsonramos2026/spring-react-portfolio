export interface  Contact {
  id: number;
  name: string;
  email: string;
  subject?: string;
  message: string;
  phone?: string;
  status?: string; // e.g., "new", "in_progress", "resolved"
  createdAt: Date;
}

export interface ContactForm {
  name: string;
  email: string;
  subject?: string;
  message: string;
  phone?: string;
}