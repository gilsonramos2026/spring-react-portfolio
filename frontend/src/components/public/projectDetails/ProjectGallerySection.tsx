import { motion } from 'framer-motion'
import FadeIn from '../../ui/FadeIn'
import { resolveAssetUrl } from '../../../utils/api'
import { Maximize2, Images } from 'lucide-react'
import type { ProjectImage } from '../../../types'

interface ProjectGallerySectionProps {
  images: ProjectImage[]
  projectTitle: string
  onOpenLightbox: (index: number) => void
}

export function ProjectGallerySection({ images, projectTitle, onOpenLightbox }: ProjectGallerySectionProps) {
  if (images.length === 0) return null

  return (
    <FadeIn>
      {/* Hero image */}
      <motion.div
        className="relative rounded-2xl overflow-hidden aspect-video bg-(--s2) cursor-pointer group"
        onClick={() => onOpenLightbox(0)}
        whileHover={{ scale: 1.01 }}
        transition={{ duration: 0.2 }}
      >
        <img
          src={resolveAssetUrl(images[0].url)}
          alt={images[0].altText ?? projectTitle}
          className="w-full h-full object-cover transition-transform duration-700 group-hover:scale-[1.04]"
        />
        <div className="absolute inset-0 bg-black/0 group-hover:bg-black/25 transition-all duration-300 flex items-center justify-center">
          <motion.div
            initial={{ opacity: 0, scale: 0.8 }}
            whileHover={{ opacity: 1, scale: 1 }}
            className="bg-white/20 backdrop-blur-sm rounded-full p-4"
          >
            <Maximize2 size={24} className="text-white" />
          </motion.div>
        </div>

        {images.length > 1 && (
          <div className="absolute bottom-3 right-3 flex items-center gap-1.5 bg-black/60 backdrop-blur-sm text-white text-xs px-2.5 py-1.5 rounded-full">
            <Images size={12} />
            {images.length} screenshots
          </div>
        )}
      </motion.div>

      {/* Thumbnail grid */}
      {images.length > 1 && (
        <div className="grid grid-cols-4 sm:grid-cols-5 gap-2 mt-3">
          {images.slice(1).map((img, i) => (
            <motion.div
              key={img.id}
              className="relative group aspect-video rounded-xl overflow-hidden bg-(--s2) cursor-pointer"
              onClick={() => onOpenLightbox(i + 1)}
              whileHover={{ scale: 1.05 }}
              whileTap={{ scale: 0.97 }}
            >
              <img
                src={resolveAssetUrl(img.url)}
                alt={img.altText ?? `Screenshot ${i + 2}`}
                className="w-full h-full object-cover transition-transform duration-300 group-hover:scale-110"
              />
              <div className="absolute inset-0 bg-black/0 group-hover:bg-black/30 transition-all flex items-center justify-center">
                <Maximize2 size={14} className="text-white opacity-0 group-hover:opacity-100 transition-opacity" />
              </div>
            </motion.div>
          ))}
        </div>
      )}
    </FadeIn>
  )
}