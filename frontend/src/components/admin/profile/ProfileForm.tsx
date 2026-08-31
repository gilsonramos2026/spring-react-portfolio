import type { UseFormRegister } from 'react-hook-form'
import type { Profile } from '../../../types'

interface ProfileFormProps {
  register: UseFormRegister<Partial<Profile>>
  isPending: boolean
  onSubmit: (e: React.FormEvent) => void
}

export function ProfileForm({ register, isPending, onSubmit }: ProfileFormProps) {
  return (
    <form onSubmit={onSubmit} className="card p-6 space-y-5">
      <p className="text-sm font-semibold text-(--t2)">Dados do perfil</p>

      <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">Nome *</label>
          <input {...register('name')} className="input" placeholder="João Silva" />
        </div>
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">Título *</label>
          <input {...register('title')} className="input" placeholder="Desenvolvedor Full Stack Sênior" />
        </div>
      </div>

      <div>
        <label className="block text-sm text-(--t3) mb-1.5">Tagline</label>
        <input {...register('tagline')} className="input" placeholder="Frase curta de impacto (até 255 caracteres)" />
      </div>

      <div>
        <label className="block text-sm text-(--t3) mb-1.5">Bio</label>
        <textarea
          {...register('bio')}
          className="input"
          rows={4}
          placeholder="Sua história profissional, o que você faz e o que te diferencia..."
        />
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">E-mail *</label>
          <input {...register('email')} type="email" className="input" />
        </div>
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">Telefone</label>
          <input {...register('phone')} className="input" placeholder="+55 11 99999-9999" />
        </div>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">Localização</label>
          <input {...register('location')} className="input" placeholder="São Paulo, SP — Brasil" />
        </div>
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">Anos de experiência</label>
          <input {...register('yearsExp', { valueAsNumber: true })} type="number" min={0} max={60} className="input" />
        </div>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">URL do currículo</label>
          <input {...register('resumeUrl')} className="input" placeholder="https://..." />
        </div>
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">Website</label>
          <input {...register('websiteUrl')} className="input" placeholder="https://..." />
        </div>
      </div>

      <p className="text-xs font-semibold text-(--t4) uppercase tracking-wide pt-1">Redes sociais</p>

      <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">GitHub</label>
          <input {...register('githubUrl')} className="input" placeholder="https://github.com/usuario" />
        </div>
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">LinkedIn</label>
          <input {...register('linkedinUrl')} className="input" placeholder="https://linkedin.com/in/usuario" />
        </div>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
        <div>
          <label className="block text-sm text-(--t3) mb-1.5">Twitter / X</label>
          <input {...register('twitterUrl')} className="input" placeholder="https://twitter.com/usuario" />
        </div>
      </div>

      <div className="flex items-center gap-3 pt-1">
        <input
          {...register('available')}
          type="checkbox"
          id="avail"
          className="w-4 h-4 accent-brand-500"
        />
        <label htmlFor="avail" className="text-sm text-(--t2)">
          Disponível para novos projetos / oportunidades
        </label>
      </div>

      <button type="submit" disabled={isPending} className="btn-primary">
        {isPending ? 'Salvando…' : 'Salvar perfil'}
      </button>
    </form>
  )
}