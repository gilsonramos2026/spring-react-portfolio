import clsx from 'clsx'

interface Props { className?: string; count?: number }

export function SkeletonBox({ className }: { className?: string }) {
  return (
    <div className={clsx(
      'animate-pulse rounded-xl bg-(--bd)',
      className
    )} />
  )
}

export function SkeletonCard() {
  return (
    <div className="card p-5 space-y-3 overflow-hidden">
      <SkeletonBox className="aspect-video w-full rounded-lg" />
      <SkeletonBox className="h-5 w-3/4" />
      <SkeletonBox className="h-4 w-full" />
      <SkeletonBox className="h-4 w-2/3" />
      <div className="flex gap-2 pt-1">
        <SkeletonBox className="h-5 w-16 rounded-full" />
        <SkeletonBox className="h-5 w-16 rounded-full" />
        <SkeletonBox className="h-5 w-16 rounded-full" />
      </div>
    </div>
  )
}

export function SkeletonText({ lines = 3, className }: { lines?: number; className?: string }) {
  return (
    <div className={clsx('space-y-2', className)}>
      {Array.from({ length: lines }).map((_, i) => (
        <SkeletonBox
          key={i}
          className={clsx('h-4', i === lines - 1 ? 'w-2/3' : 'w-full')}
        />
      ))}
    </div>
  )
}

export function SkeletonProjectGrid({ count = 6 }: Props) {
  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
      {Array.from({ length: count }).map((_, i) => <SkeletonCard key={i} />)}
    </div>
  )
}

export function SkeletonProfile() {
  return (
    <div className="grid grid-cols-1 sm:grid-cols-3 gap-8">
      <div className="flex flex-col items-center sm:items-start gap-4">
        <SkeletonBox className="w-32 h-32 rounded-2xl" />
        <SkeletonText lines={2} className="w-full" />
      </div>
      <div className="sm:col-span-2 space-y-4">
        <SkeletonBox className="h-7 w-48" />
        <SkeletonBox className="h-5 w-36" />
        <SkeletonText lines={5} />
      </div>
    </div>
  )
}
