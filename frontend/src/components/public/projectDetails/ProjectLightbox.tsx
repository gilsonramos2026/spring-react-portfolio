import { motion, AnimatePresence } from 'framer-motion'
import { resolveAssetUrl } from '../../../utils/api'
import { X, ChevronLeft, ChevronRight } from 'lucide-react'
import type { ProjectImage } from '../../../types'

interface ProjectLightboxProps {
  images: ProjectImage[]
  index: number | null
  onClose: () => void
  onPrev: () => void
  onNext: () => void
}

export function ProjectLightbox({ images, index, onClose, onPrev, onNext }: ProjectLightboxProps) {
  if (index === null) return null

  return (
    <AnimatePresence>
      <motion.div
        className="fixed inset-0 z-50 flex flex-col"
        style={{ background: 'rgba(2,6,23,0.97)', backdropFilter: 'blur(12px)' }}
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        exit={{ opacity: 0 }}
        onClick={onClose}
      >
        {/* Top bar */}
        <div className="flex items-center justify-between px-5 py-3 shrink-0" onClick={e => e.stopPropagation()}>
          <span className="text-white/50 text-sm font-mono">{index + 1} / {images.length}</span>
          <button onClick={onClose}
            className="tap text-white/50 hover:text-white transition-colors hover:bg-white/10 rounded-xl p-1">
            <X size={22} />
          </button>
        </div>

        {/* Image */}
        <div className="flex-1 flex items-center justify-center gap-4 px-4 min-h-0" onClick={e => e.stopPropagation()}>
          {images.length > 1 && (
            <motion.button onClick={onPrev} whileHover={{ scale: 1.1 }} whileTap={{ scale: 0.9 }}
              className="tap border border-white/20 hover:border-white/50 rounded-full text-white/60 hover:text-white transition-all p-2 shrink-0">
              <ChevronLeft size={22} />
            </motion.button>
          )}

          <motion.img
            key={index}
            src={resolveAssetUrl(images[index].url)}
            alt={images[index].altText ?? `Screenshot ${index + 1}`}
            className="max-h-full max-w-full object-contain rounded-2xl shadow-2xl"
            style={{ maxHeight: 'calc(100dvh - 160px)' }}
            initial={{ opacity: 0, scale: 0.92 }}
            animate={{ opacity: 1, scale: 1 }}
            transition={{ duration: 0.25 }}
          />

          {images.length > 1 && (
            <motion.button onClick={onNext} whileHover={{ scale: 1.1 }} whileTap={{ scale: 0.9 }}
              className="tap border border-white/20 hover:border-white/50 rounded-full text-white/60 hover:text-white transition-all p-2 shrink-0">
              <ChevronRight size={22} />
            </motion.button>
          )}
        </div>

        {/* Alt text */}
        {images[index].altText && (
          <p className="text-white/40 text-sm text-center py-2 shrink-0 px-4">{images[index].altText}</p>
        )}

        {/* Thumbnail strip */}
        {images.length > 1 && (
          <div className="flex justify-center gap-2 pb-5 shrink-0 overflow-x-auto px-4"
            onClick={e => e.stopPropagation()}>
            {images.map((img, i) => (
              <motion.button key={img.id}
                whileHover={{ scale: 1.08 }} whileTap={{ scale: 0.95 }}
                className={`w-14 h-9 rounded-lg overflow-hidden border-2 shrink-0 transition-all ${
                  i === index ? 'border-brand-400 opacity-100' : 'border-transparent opacity-40 hover:opacity-70'
                }`}>
                <img src={resolveAssetUrl(img.url)} alt="" className="w-full h-full object-cover" />
              </motion.button>
            ))}
          </div>
        )}
      </motion.div>
    </AnimatePresence>
  )
}