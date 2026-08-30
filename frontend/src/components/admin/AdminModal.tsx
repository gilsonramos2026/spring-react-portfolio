import { useEffect, type ReactNode } from 'react'
import { X } from 'lucide-react'
import clsx from 'clsx'
const SIZES = { sm:'sm:max-w-sm', md:'sm:max-w-lg', lg:'sm:max-w-2xl' }
interface Props { title:string; onClose:()=>void; children:ReactNode; size?:'sm'|'md'|'lg' }
export default function AdminModal({ title, onClose, children, size='md' }: Props) {
  useEffect(() => {
    const fn=(e:KeyboardEvent)=>{ if(e.key==='Escape') onClose() }
    document.addEventListener('keydown',fn); return ()=>document.removeEventListener('keydown',fn)
  },[onClose])
  return (
    <div className="fixed inset-0 bg-black/65 backdrop-blur-sm z-50 flex items-end sm:items-center justify-center p-0 sm:p-4">
      <div className="absolute inset-0" onClick={onClose}/>
      <div className={clsx('relative z-10 w-full flex flex-col bg-[var(--s2)] border border-[var(--bd)] rounded-t-2xl sm:rounded-2xl max-h-[92dvh]', SIZES[size])}>
        <div className="sm:hidden flex justify-center pt-3 pb-1 shrink-0"><div className="w-10 h-1 rounded-full bg-[var(--bd2)]"/></div>
        <div className="flex items-center justify-between px-5 py-4 border-b border-[var(--bd)] shrink-0">
          <h2 className="font-semibold text-lg text-[var(--t1)]">{title}</h2>
          <button onClick={onClose} className="tap text-[var(--t3)] hover:text-[var(--t1)] transition-colors"><X size={20}/></button>
        </div>
        <div className="overflow-y-auto p-5 sm:p-6 safe-bottom">{children}</div>
      </div>
    </div>
  )
}
