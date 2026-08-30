import { useEffect } from 'react'

interface PageMeta {
  title: string
  description?: string
  image?: string
  type?: 'website' | 'article'
  noIndex?: boolean
}

const SITE_NAME = import.meta.env.VITE_SITE_NAME || 'Portfólio'
const BASE_URL  = import.meta.env.VITE_SITE_URL  || 'https://seudominio.com'

export function usePageMeta({ title, description, image, type = 'website', noIndex }: PageMeta) {
  useEffect(() => {
    // Title
    document.title = `${title} | ${SITE_NAME}`

    const setMeta = (name: string, content: string, attr = 'name') => {
      let el = document.querySelector(`meta[${attr}="${name}"]`) as HTMLMetaElement | null
      if (!el) {
        el = document.createElement('meta')
        el.setAttribute(attr, name)
        document.head.appendChild(el)
      }
      el.content = content
    }

    if (description) {
      setMeta('description', description)
      setMeta('og:description', description, 'property')
      setMeta('twitter:description', description)
    }

    setMeta('og:title',    title,        'property')
    setMeta('og:type',     type,         'property')
    setMeta('og:site_name', SITE_NAME,   'property')
    setMeta('twitter:title', title)
    setMeta('twitter:card', 'summary_large_image')

    if (image) {
      const abs = image.startsWith('http') ? image : `${BASE_URL}${image}`
      setMeta('og:image',       abs, 'property')
      setMeta('twitter:image',  abs)
    }

    if (noIndex) setMeta('robots', 'noindex, nofollow')
    else         setMeta('robots', 'index, follow')

    return () => { document.title = SITE_NAME }
  }, [title, description, image, type, noIndex])
}
