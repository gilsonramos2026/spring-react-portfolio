import { useInView } from 'react-intersection-observer'
import clsx from 'clsx'
import type { ReactNode } from 'react'
export default function FadeIn({ children, delay=0, className }: { children:ReactNode; delay?:number; className?:string }) {
  const { ref, inView } = useInView({ triggerOnce: true, threshold: 0.06 })
  return (
    <div ref={ref} className={clsx('transition-all duration-700', inView?'opacity-100 translate-y-0':'opacity-0 translate-y-5', className)} style={{ transitionDelay:`${delay}ms` }}>
      {children}
    </div>
  )
}