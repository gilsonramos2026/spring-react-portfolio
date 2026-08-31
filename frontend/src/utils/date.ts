export function formatPeriod(start: string, end?: string, current?: boolean) {
  const s = new Date(start).toLocaleDateString('pt-BR', { month: 'short', year: 'numeric' })
  if (current) return `${s} – Presente`
  if (!end) return s
  return `${s} – ${new Date(end).toLocaleDateString('pt-BR', { month: 'short', year: 'numeric' })}`
}