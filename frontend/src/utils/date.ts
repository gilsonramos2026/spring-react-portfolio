export function formatPeriod(
  start: string | Date | undefined | null, 
  end?: string | Date | null, 
  current?: boolean
) {
  if (!start) return ''

  // CORRIGIDO: O construtor new Date() agora aceita com segurança instâncias de Date ou strings ISO
  const startDate = typeof start === 'string' ? new Date(start) : start
  const s = startDate.toLocaleDateString('pt-BR', { month: 'short', year: 'numeric' })
  
  if (current) return `${s} – Presente`
  if (!end) return s

  const endDate = typeof end === 'string' ? new Date(end) : end
  return `${s} – ${endDate.toLocaleDateString('pt-BR', { month: 'short', year: 'numeric' })}`
}
