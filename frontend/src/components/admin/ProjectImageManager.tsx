import { useRef, useState } from 'react'
import { useUploadProjectImage, useDeleteProjectImage } from '../../hooks/useProjects'
import { resolveAssetUrl } from '../../utils/api'
import type { ProjectImage } from '../../types'
import { Upload, X, ImageOff, GripVertical, AlertCircle, CheckCircle2 } from 'lucide-react'
import Spinner from '../ui/Spinner'
import { adminApi } from '../../utils/api'
import { useQueryClient } from '@tanstack/react-query'
import toast from 'react-hot-toast'

interface Props {
  projectId: number
  images: ProjectImage[]
}

export default function ProjectImageManager({ projectId, images }: Props) {
  const fileRef = useRef<HTMLInputElement>(null)
  const qc = useQueryClient()
  const upload = useUploadProjectImage()
  const remove = useDeleteProjectImage()

  // Track per-file upload states
  const [uploading, setUploading] = useState<string[]>([])
  const [dragIndex, setDragIndex] = useState<number | null>(null)
  const [dropIndex, setDropIndex] = useState<number | null>(null)

  const isDragging = dragIndex !== null

  const handleFiles = (files: FileList | null) => {
    if (!files) return
    const fileArr = Array.from(files)

    // Validate before uploading
    const invalid = fileArr.filter(f => {
      const ext = f.name.split('.').pop()?.toLowerCase() ?? ''
      return !['jpg', 'jpeg', 'png', 'webp', 'gif'].includes(ext) || f.size > 5 * 1024 * 1024
    })
    if (invalid.length > 0) {
      toast.error(`${invalid.length} arquivo(s) inválido(s). Use JPG/PNG/WEBP até 5MB.`)
    }

    const valid = fileArr.filter(f => {
      const ext = f.name.split('.').pop()?.toLowerCase() ?? ''
      return ['jpg', 'jpeg', 'png', 'webp', 'gif'].includes(ext) && f.size <= 5 * 1024 * 1024
    })

    valid.forEach(f => {
      setUploading(prev => [...prev, f.name])
      upload.mutate(
        { projectId, file: f },
        {
          onSettled: () => setUploading(prev => prev.filter(n => n !== f.name)),
        }
      )
    })
  }

  const handleDragOver = (e: React.DragEvent, targetIndex: number) => {
    e.preventDefault()
    setDropIndex(targetIndex)
  }

  const handleDrop = async (targetIndex: number) => {
    if (dragIndex === null || dragIndex === targetIndex) {
      setDragIndex(null)
      setDropIndex(null)
      return
    }
    const reordered = [...images]
    const [moved] = reordered.splice(dragIndex, 1)
    reordered.splice(targetIndex, 0, moved)
    setDragIndex(null)
    setDropIndex(null)

    try {
      await adminApi.put(
        `/admin/projects/${projectId}/images/reorder`,
        reordered.map(img => img.id)
      )
      qc.invalidateQueries({ queryKey: ['projects'] })
      toast.success('Ordem atualizada!')
    } catch {
      toast.error('Erro ao reordenar.')
    }
  }

  const isPending = upload.isPending || uploading.length > 0

  return (
    <div className="space-y-4">
      {/* Header */}
      <div className="flex items-center justify-between">
        <div>
          <p className="text-sm font-semibold text-[var(--t2)]">Screenshots do projeto</p>
          <p className="text-xs text-[var(--t4)] mt-0.5">
            A primeira imagem aparece como destaque na galeria pública
          </p>
        </div>
        <span className="text-xs font-medium px-2 py-0.5 rounded-full bg-[var(--cb)] border border-[var(--bd)] text-[var(--t4)]">
          {images.length} / ∞
        </span>
      </div>

      {/* Upload dropzone */}
      <div
        onClick={() => !isPending && fileRef.current?.click()}
        onDragOver={e => { e.preventDefault() }}
        onDrop={e => { e.preventDefault(); handleFiles(e.dataTransfer.files) }}
        className={`
          border-2 border-dashed rounded-2xl p-6 flex flex-col items-center gap-3
          cursor-pointer transition-all duration-200 text-center select-none
          ${isPending
            ? 'border-[var(--color-brand-500)]/50 bg-[rgba(14,165,233,0.05)] cursor-wait'
            : 'border-[var(--bd2)] hover:border-[var(--color-brand-500)]/60 hover:bg-[var(--cb)]'
          }
        `}
      >
        {isPending ? (
          <>
            <Spinner size={24} className="text-[var(--color-brand-400)]" />
            <p className="text-sm font-medium text-[var(--color-brand-400)]">
              Enviando {uploading.length} arquivo{uploading.length > 1 ? 's' : ''}…
            </p>
          </>
        ) : (
          <>
            <div className="w-10 h-10 rounded-2xl bg-[var(--cb)] border border-[var(--bd)] flex items-center justify-center">
              <Upload size={18} className="text-[var(--t4)]" />
            </div>
            <div>
              <p className="text-sm font-semibold text-[var(--t2)]">Clique ou arraste imagens aqui</p>
              <p className="text-xs text-[var(--t4)] mt-0.5">JPG · PNG · WEBP · GIF — máx. 5MB cada</p>
            </div>
            <button
              type="button"
              className="text-xs text-[var(--color-brand-400)] hover:underline font-medium"
            >
              Selecionar arquivos
            </button>
          </>
        )}
        <input
          ref={fileRef}
          type="file"
          accept="image/jpeg,image/png,image/webp,image/gif"
          multiple
          className="hidden"
          onChange={e => { handleFiles(e.target.files); e.target.value = '' }}
        />
      </div>

      {/* Uploading names */}
      {uploading.length > 0 && (
        <div className="space-y-1">
          {uploading.map(name => (
            <div key={name} className="flex items-center gap-2 text-xs text-[var(--t4)] bg-[var(--cb)] rounded-lg px-3 py-2">
              <Spinner size={12} className="text-[var(--color-brand-400)] shrink-0" />
              <span className="truncate">{name}</span>
            </div>
          ))}
        </div>
      )}

      {/* Gallery grid */}
      {images.length > 0 ? (
        <>
          <div className="grid grid-cols-2 sm:grid-cols-3 gap-3">
            {images.map((img, i) => (
              <div
                key={img.id}
                draggable
                onDragStart={() => setDragIndex(i)}
                onDragOver={e => handleDragOver(e, i)}
                onDrop={() => handleDrop(i)}
                onDragEnd={() => { setDragIndex(null); setDropIndex(null) }}
                className={`
                  relative group rounded-xl overflow-hidden border aspect-video bg-[var(--cb)] cursor-grab
                  transition-all duration-150
                  ${dropIndex === i && dragIndex !== i
                    ? 'border-[var(--color-brand-400)] ring-2 ring-[var(--color-brand-400)]/30 scale-[1.03]'
                    : dragIndex === i
                      ? 'border-[var(--bd2)] opacity-50 scale-95'
                      : 'border-[var(--bd)] hover:border-[var(--bd2)]'
                  }
                `}
              >
                <img
                  src={resolveAssetUrl(img.url)}
                  alt={img.altText ?? `Screenshot ${i + 1}`}
                  className="w-full h-full object-cover"
                  draggable={false}
                />

                {/* Drag handle */}
                <div className="absolute top-1.5 left-1.5 bg-black/60 rounded-lg p-1 opacity-0 group-hover:opacity-100 transition-opacity">
                  <GripVertical size={12} className="text-white/80" />
                </div>

                {/* Delete button */}
                <button
                  type="button"
                  onClick={() => {
                    if (confirm(`Remover screenshot ${i + 1}?`)) {
                      remove.mutate({ projectId, imageId: img.id })
                    }
                  }}
                  className="absolute top-1.5 right-1.5 bg-black/60 hover:bg-red-500 rounded-lg p-1.5 transition-colors opacity-0 group-hover:opacity-100 tap"
                  style={{ minHeight: 'unset', minWidth: 'unset' }}
                  aria-label="Remover imagem"
                >
                  <X size={11} className="text-white" />
                </button>

                {/* Order badge — first one highlighted */}
                <span className={`
                  absolute bottom-1.5 left-1.5 text-xs px-1.5 py-0.5 rounded-md font-semibold
                  ${i === 0 ? 'bg-[var(--color-brand-500)] text-white' : 'bg-black/60 text-white/70'}
                `}>
                  {i === 0 ? '★ Capa' : `${i + 1}`}
                </span>
              </div>
            ))}
          </div>

          <div className="flex items-start gap-2 text-xs text-[var(--t4)]">
            <GripVertical size={13} className="shrink-0 mt-0.5" />
            <span>Arraste as imagens para reordenar. A marcada como <strong className="text-[var(--color-brand-400)]">Capa</strong> é exibida como destaque na página do projeto.</span>
          </div>
        </>
      ) : (
        <div className="flex flex-col items-center gap-3 py-6 text-center">
          <div className="w-12 h-12 rounded-2xl bg-[var(--cb)] border border-[var(--bd)] flex items-center justify-center">
            <ImageOff size={20} className="text-[var(--t5)]" />
          </div>
          <div>
            <p className="text-sm font-medium text-[var(--t3)]">Nenhum screenshot ainda</p>
            <p className="text-xs text-[var(--t4)] mt-0.5">Envie pelo menos 1 imagem para aparecer na galeria</p>
          </div>
        </div>
      )}
    </div>
  )
}
