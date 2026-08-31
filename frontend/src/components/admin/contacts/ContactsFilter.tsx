const STATUS_OPTS = [
  { v:'new',    l:'Nova',    cls:'bg-[rgba(14,165,233,0.15)] text-[var(--color-brand-400)] border-[rgba(14,165,233,0.25)]' },
  { v:'read',    l:'Lida',    cls:'bg-[var(--chb)] text-[var(--t3)] border-[var(--chbd)]' },
  { v:'replied', l:'Respondida',cls:'bg-emerald-500/15 text-emerald-400 border-emerald-500/25' },
  { v:'archived',l:'Arquivada', cls:'bg-[var(--chb)] text-[var(--t5)] border-[var(--chbd)]' },
]

interface ContactsFilterProps {
  filter: string | undefined
  setFilter: (v: string | undefined) => void
}

export function ContactsFilter({ filter, setFilter }: ContactsFilterProps) {
  return (
    <div className="flex gap-2 flex-wrap">
      {[{v:undefined,l:'Todas'},...STATUS_OPTS].map(o=>(
        <button key={o.l} onClick={()=>setFilter(o.v)}
          className={`tag cursor-pointer transition-all text-xs ${filter===o.v?'bg-[var(--color-brand-500)] text-white border-[var(--color-brand-500)]':''}`}>
          {o.l}
        </button>
      ))}
    </div>
  )
}