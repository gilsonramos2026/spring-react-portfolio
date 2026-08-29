export interface Profile {
  id: number;
  name: string;
  title: string;
  tagline?: string;
  bio: string;
  email: string;
  phone?: string;
  location?: string;
  avatarUrl?: string;
  resumeUrl?: string;
  githubUrl?: string;
  linkedinUrl?: string;
  twitterUrl?: string;
  websiteUrl?: string;
  yearsExp?: number;
  available?: boolean;
  updatedAt: Date;
}