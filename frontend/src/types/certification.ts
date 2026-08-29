export interface Certification {
  id: number;
  name: string;
  issuer: string;
  credentialId?: string;
  credentialUrl?: string;
  imageUrl?: string;
  issuedAt: Date;
  expiresAt?: Date; // Optional for certifications that do not expire
  sortOrder?: number; // For ordering certifications in a list
}
