export interface Skill {
  id: number;
  name: string;
  category?: string; // e.g., "Frontend", "Backend", "DevOps"
   proficiency?: number; // e.g., 1-5 scale
   iconName?: string; // e.g., "react", "nodejs", "docker"
   sortOrder?: number; // for ordering skills in a list
}