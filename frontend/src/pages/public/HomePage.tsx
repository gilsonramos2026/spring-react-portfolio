import { usePageMeta } from "../../hooks/usePageMeta";
import { useProfile } from "../../hooks/useProfile";

import { HeroSection } from "../../components/public/home/HeroSection";
import { StatsSection } from "../../components/public/home/StatsSection";
import { TechStackSection } from "../../components/public/home/TechStackSection";
import { ProjectsSection } from "../../components/public/home/ProjectsSection";
import { TestimonialsSection } from "../../components/public/home/TestimonialsSection";
import CallToActionSection from "../../components/public/home/CallToActionSection";

export  function HomePage() {
  const { data: profile } = useProfile();

  usePageMeta({
    title: profile?.name ?? 'Início',
    description: profile?.tagline ?? profile?.bio?.slice(0, 160) ?? 'Portfólio profissional — Desenvolvedor Full Stack.',
    image: profile?.avatarUrl,
  });

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6">
      <HeroSection />
      <StatsSection />
      <TechStackSection />
      <ProjectsSection />
      <TestimonialsSection />
      <CallToActionSection />
    </div>
  );
}