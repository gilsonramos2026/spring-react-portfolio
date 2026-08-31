import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { adminApi } from '../../utils/api'
import { Lock, Code2 } from 'lucide-react'
import Spinner from '../../components/ui/Spinner'

export  function AdminLogin() {
  const [key, setKey] = useState('')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)
  const navigate = useNavigate()

  const login = async (e: React.FormEvent) => {
    e.preventDefault(); setError(''); setLoading(true)
    try {
      localStorage.setItem('admin_key', key)
      await adminApi.get('/admin/contacts/count-new')
      navigate('/admin')
    } catch {
      setError('Chave inválida. Verifique e tente novamente.')
      localStorage.removeItem('admin_key')
    } finally { setLoading(false) }
  }

  return (
    <div className="min-h-dvh flex items-center justify-center px-4 bg-(--bg)">
      <div className="w-full max-w-sm">
        <div className="text-center mb-8">
          <div className="w-14 h-14 rounded-2xl bg-brand-500 flex items-center justify-center mx-auto mb-4">
            <Code2 size={24} className="text-white"/>
          </div>
          <h1 className="text-2xl font-bold text-(--t1)">Painel Admin</h1>
          <p className="text-(--t4) text-sm mt-1">Informe a chave de acesso para continuar</p>
        </div>
        <form onSubmit={login} className="card p-6 space-y-4">
          <div>
            <label className="block text-sm font-medium text-(--t2) mb-1.5">Chave de acesso</label>
            <div className="relative">
              <Lock size={15} className="absolute left-3 top-1/2 -translate-y-1/2 text-(--t4)"/>
              <input type="password" value={key} onChange={e => setKey(e.target.value)} required
                className="input pl-9" placeholder="••••••••••"/>
            </div>
          </div>
          {error && <p className="text-red-400 text-sm bg-red-500/10 border border-red-500/20 rounded-lg px-3 py-2">{error}</p>}
          <button type="submit" disabled={loading || !key} className="btn-primary w-full justify-center">
            {loading ? <Spinner size={16} className="text-white"/> : 'Entrar'}
          </button>
        </form>
        <p className="text-center text-xs text-(--t5) mt-6">
          <a href="/" className="hover:text-brand-400 transition-colors">← Voltar ao site</a>
        </p>
      </div>
    </div>
  )
}
