import { Link } from 'react-router-dom'
import { motion } from 'framer-motion'
import { usePageMeta } from '../../hooks/usePageMeta'
import { Home, ArrowLeft } from 'lucide-react'

export default function NotFound() {
  usePageMeta({ title: 'Página não encontrada', noIndex: true })

  return (
    <div className="min-h-[80dvh] flex flex-col items-center justify-center px-4 text-center">
      <motion.div
        initial={{ opacity: 0, y: 30 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.5 }}
        className="space-y-6 max-w-lg"
      >
        {/* Big 404 */}
        <motion.p
          className="text-[8rem] sm:text-[10rem] font-bold leading-none text-gradient select-none"
          animate={{ scale: [1, 1.03, 1] }}
          transition={{ duration: 3, repeat: Infinity, ease: 'easeInOut' }}
        >
          404
        </motion.p>

        <div className="space-y-2">
          <h1 className="text-2xl font-bold text-[var(--t1)]">Página não encontrada</h1>
          <p className="text-[var(--t3)]">
            O endereço que você acessou não existe ou foi movido.
          </p>
        </div>

        <div className="flex flex-col sm:flex-row gap-3 justify-center pt-2">
          <Link to="/" className="btn-primary">
            <Home size={16} /> Voltar para o início
          </Link>
          <button onClick={() => window.history.back()} className="btn-outline">
            <ArrowLeft size={16} /> Página anterior
          </button>
        </div>
      </motion.div>
    </div>
  )
}
