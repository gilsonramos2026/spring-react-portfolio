export interface ProjectImage {
  id: number;
  url: string;
  altText?: string;
  sortOrder: number;
}

export interface Project {
  id: number;
  title: string;
  slug: string;
  shortDesc: string;
  description: string;
  thumbnailUrl?: string;
  demoUrl?: string;
  githubUrl?: string;
  featured?: boolean;
  status?: string;
  sortOrder?: number;
  tags?: string[];
  images?: ProjectImage[];
  startedAt?: Date;
  finishedAt?: Date;
  createdAt: Date;
}