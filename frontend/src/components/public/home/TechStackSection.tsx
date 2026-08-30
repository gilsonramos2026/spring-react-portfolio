import { TechGrid } from "../../icons/TechIcon"
import FadeIn from "../../ui/FadeIn"

const STACK = ['React', 'TypeScript', 'Next.js', 'Java 21', 'Spring Boot', 'PostgreSQL', 'Docker', 'AWS', 'Git', 'Tailwind CSS']

export function TechStackSection() {
  return (
    <FadeIn>
      <section className="py-14 border-t border-(--bd)">
        <p className="text-center text-xs text-(--t4) mb-8 uppercase tracking-widest font-semibold">
          Stack principal
        </p>
        <TechGrid names={STACK} iconSize={36} />
      </section>
    </FadeIn>
  )
}