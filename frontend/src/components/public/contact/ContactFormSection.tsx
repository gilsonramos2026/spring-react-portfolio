import { useForm } from 'react-hook-form'
import FadeIn from '../../ui/FadeIn'
import type { ContactForm } from '../../../types'
import { Send } from 'lucide-react'
import { useSendContact } from '../../../hooks/useContact'

export function ContactFormSection() {
  const send = useSendContact()
  const { register, handleSubmit, reset, formState: { errors } } = useForm<ContactForm>()

  const onSubmit = (data: ContactForm) => {
    send.mutate(data, { onSuccess: () => reset() })
  }

  return (
    <FadeIn className="lg:col-span-2">
      <form onSubmit={handleSubmit(onSubmit)} className="card p-6 sm:p-8 space-y-5">
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium text-(--t2) mb-1.5">Nome *</label>
            <input
              {...register('name', { required: 'Campo obrigatório' })}
              className="input"
              placeholder="Seu nome completo"
            />
            {errors.name && <p className="text-red-400 text-xs mt-1">{errors.name.message}</p>}
          </div>
          <div>
            <label className="block text-sm font-medium text-(--t2) mb-1.5">E-mail *</label>
            <input
              {...register('email', {
                required: 'Campo obrigatório',
                pattern: { value: /^[^\s@]+@[^\s@]+\.[^\s@]+$/, message: 'E-mail inválido' }
              })}
              type="email"
              className="input"
              placeholder="seu@email.com"
            />
            {errors.email && <p className="text-red-400 text-xs mt-1">{errors.email.message}</p>}
          </div>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium text-(--t2) mb-1.5">Assunto</label>
            <input {...register('subject')} className="input" placeholder="Sobre o que quer conversar?" />
          </div>
          <div>
            <label className="block text-sm font-medium text-(--t2) mb-1.5">Telefone</label>
            <input {...register('phone')} className="input" placeholder="(11) 99999-9999" />
          </div>
        </div>

        <div>
          <label className="block text-sm font-medium text-(--t2) mb-1.5">Mensagem *</label>
          <textarea
            {...register('message', {
              required: 'Campo obrigatório',
              minLength: { value: 10, message: 'Mínimo 10 caracteres' }
            })}
            className="input"
            placeholder="Conta mais sobre seu projeto ou ideia…"
          />
          {errors.message && <p className="text-red-400 text-xs mt-1">{errors.message.message}</p>}
        </div>

        <button type="submit" disabled={send.isPending} className="btn-primary w-full justify-center">
          {send.isPending ? 'Enviando…' : <><Send size={15}/> Enviar mensagem</>}
        </button>
      </form>
    </FadeIn>
  )
}