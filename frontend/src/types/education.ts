export interface Education {
  id: number;
  institution: string;
  degree: string;
  fieldOfStudy?: string;
  description?: string;
  logoUrl?: string;
  grade?: string;
  startedAt: Date;
  endedAt?: Date; // Optional for ongoing education
  current?: boolean; // Indicates if this is the current education
  sortOrder?: number; // For ordering education entries in a list
}
