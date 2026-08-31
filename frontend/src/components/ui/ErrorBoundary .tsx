import { Component, type ReactNode, type ErrorInfo } from 'react'
import { AlertTriangle, RefreshCw } from 'lucide-react'

interface Props  { children: ReactNode; fallback?: ReactNode }
interface State  { error: Error | null }

export default class ErrorBoundary extends Component<Props, State> {
  state: State = { error: null }

  static getDerivedStateFromError(error: Error): State {
    return { error }
  }

  componentDidCatch(error: Error, info: ErrorInfo) {
    console.error('[ErrorBoundary]', error, info.componentStack)
  }

  render() {
    if (!this.state.error) return this.props.children
    if (this.props.fallback) return this.props.fallback

    return (
      <div className="min-h-[40dvh] flex flex-col items-center justify-center gap-5 px-4 text-center">
        <div className="w-14 h-14 rounded-2xl bg-red-500/10 border border-red-500/20 flex items-center justify-center">
          <AlertTriangle size={24} className="text-red-400" />
        </div>
        <div>
          <h2 className="text-lg font-semibold text-(--t1) mb-1">Algo deu errado</h2>
          <p className="text-sm text-(--t3) max-w-sm">
            Ocorreu um erro inesperado. Tente recarregar a página.
          </p>
          {import.meta.env.DEV && (
            <pre className="mt-3 text-xs text-red-400 bg-red-500/10 rounded-xl p-3 text-left max-w-lg overflow-auto">
              {this.state.error.message}
            </pre>
          )}
        </div>
        <button
          className="btn-outline gap-2"
          onClick={() => {
            this.setState({ error: null })
            window.location.reload()
          }}
        >
          <RefreshCw size={15} /> Recarregar
        </button>
      </div>
    )
  }
}
