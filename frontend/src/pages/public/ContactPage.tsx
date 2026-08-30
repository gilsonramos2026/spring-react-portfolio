import { ContactFormSection } from "../../components/public/contact/ContactFormSection"
import { ContactHeaderSection } from "../../components/public/contact/ContactHeaderSection"
import { ContactInfoSidebar } from "../../components/public/contact/ContactInfoSidebar"
import { usePageMeta } from "../../hooks/usePageMeta"

export function ContactPage() {
  usePageMeta({
        title: 'Contato',
        description: 'Entre em contato para projetos, consultorias ou oportunidades de trabalho.',
      })

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 py-12 sm:py-20">
      <ContactHeaderSection />

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-10">
        <ContactFormSection />
        <ContactInfoSidebar />
      </div>
    </div>
  )
}