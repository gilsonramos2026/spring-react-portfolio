import { usePageMeta } from "../../hooks/usePageMeta";
import { useProfile } from "../../hooks/useProfile";

import { HeroSection } from "../../components/public/home/HeroSection";
import { StatsSection } from "../../components/public/home/StatsSection";
import { TechStackSection } from "../../components/public/home/TechStackSection";
import { ProjectsSection } from "../../components/public/home/ProjectsSection";


export  function HomePage() {
  const { data: profile } = useProfile();

  usePageMeta({
    title: profile?.name ??'início',
    description:  profile?.tagline ??  profile?.bio?.slice(0, 160) ?? 'Portfólio profissional — Desenvolvedor Full Stack.',
    image: profile?.avatarUrl,
  });

  return (
    <div className="max-w-7xl max-auto px-4 sm:px-6">
      <HeroSection />
      <StatsSection />
      <TechStackSection />
      <ProjectsSection />
    </div>
  )
}