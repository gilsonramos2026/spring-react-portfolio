import FadeIn from '../../ui/FadeIn'

export function ContactHeaderSection() {
  return (
    <FadeIn className="mb-12">
      <h1 className="text-3xl sm:text-4xl font-bold text-(--t1) mb-3">Contato</h1>
      <p className="text-(--t3) max-w-xl">Tem um projeto em mente? Vamos conversar!</p>
    </FadeIn>
  )
}