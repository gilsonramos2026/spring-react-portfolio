export interface Experience {
  id: number;
  company: string;
  role: string;
  description?: string;
  logoUrl?: string;
  location?: string;
  type?: string; // e.g., "Full-time", "Part-time", "Contract", "Internship"
  startDate: Date;
  endDate?: Date; // Optional for current experiences
  current?: boolean; // Indicates if this is the current experience
  sortOrder?: number; // For ordering experiences in a list
  technologies?: string[]; // List of technologies used in this experience
}