import FadeIn from "../../ui/FadeIn";


export function DashboardHeader() {
  return (
    <FadeIn>
      <h1 className="text-2xl font-bold text-(--t1)">Dashboard</h1>
      <p className="text-(--t3) text-sm mt-0.5">Visão geral do seu portfólio</p>
    </FadeIn>
  )
}