/**
 * TechIcon — ícones de tecnologia 100% dinâmicos via simple-icons.
 * Nenhum SVG escrito à mão. O pacote tem +3000 ícones de marcas.
 *
 * Como funciona:
 *  1. Recebe o nome da tech ("React", "Spring Boot", etc.)
 *  2. Normaliza para o slug do simple-icons ("siReact", "siSpring")
 *  3. Renderiza o SVG com a cor oficial da marca
 *  4. Fallback com iniciais se o ícone não existir
 */
import * as si from 'simple-icons'
import clsx from 'clsx'

type SiIcon = { path: string; hex: string; title: string }

// ── Alias map: nome comum → chave no simple-icons ─────────────
const ALIASES: Record<string, string> = {
  // Languages
  'javascript':'siJavascript', 'js':'siJavascript',
  'typescript':'siTypescript', 'ts':'siTypescript',
  'python':'siPython', 'java':'siOpenjdk', 'java 21':'siOpenjdk', 'openjdk':'siOpenjdk',
  'go':'siGo', 'golang':'siGo', 'rust':'siRust',
  'kotlin':'siKotlin', 'swift':'siSwift', 'php':'siPhp',
  'ruby':'siRuby', 'c#':'siCsharp', '.net':'siDotnet', 'c++':'siCplusplus',
  'dart':'siDart', 'scala':'siScala', 'elixir':'siElixir',

  // Frontend
  'react':'siReact', 'react native':'siReact',
  'next.js':'siNextdotjs', 'nextjs':'siNextdotjs', 'next':'siNextdotjs',
  'vue':'siVuedotjs', 'vue.js':'siVuedotjs', 'nuxt':'siNuxtdotjs',
  'angular':'siAngular', 'svelte':'siSvelte', 'astro':'siAstro',
  'remix':'siRemix', 'vite':'siVite', 'webpack':'siWebpack',

  // Styling
  'tailwind':'siTailwindcss', 'tailwind css':'siTailwindcss', 'tailwindcss':'siTailwindcss',
  'sass':'siSass', 'css':'siCss3', 'html':'siHtml5',
  'bootstrap':'siBootstrap', 'material ui':'siMui', 'mui':'siMui',

  // Backend
  'node.js':'siNodedotjs', 'nodejs':'siNodedotjs', 'node':'siNodedotjs',
  'spring':'siSpring', 'spring boot':'siSpringboot',
  'django':'siDjango', 'flask':'siFlask', 'fastapi':'siFastapi',
  'laravel':'siLaravel', 'express':'siExpress', 'nestjs':'siNestjs',
  'graphql':'siGraphql', 'grpc':'siGrpc',

  // Databases
  'postgresql':'siPostgresql', 'postgres':'siPostgresql',
  'mysql':'siMysql', 'mongodb':'siMongodb', 'mongo':'siMongodb',
  'redis':'siRedis', 'sqlite':'siSqlite', 'oracle':'siOracle',
  'mariadb':'siMariadb', 'supabase':'siSupabase', 'prisma':'siPrisma',
  'cassandra':'siApachecassandra',

  // DevOps / Cloud
  'docker':'siDocker', 'kubernetes':'siKubernetes', 'k8s':'siKubernetes',
  'aws':'siAmazonwebservices', 'amazon web services':'siAmazonwebservices',
  'azure':'siMicrosoftazure', 'gcp':'siGooglecloud', 'google cloud':'siGooglecloud',
  'terraform':'siTerraform', 'ansible':'siAnsible',
  'nginx':'siNginx', 'apache':'siApache',
  'jenkins':'siJenkins', 'github actions':'siGithubactions',
  'gitlab ci':'siGitlab',

  // Tools
  'git':'siGit', 'github':'siGithub', 'gitlab':'siGitlab',
  'figma':'siFigma', 'postman':'siPostman', 'jira':'siJira',
  'notion':'siNotion', 'linux':'siLinux',
  'vs code':'siVisualstudiocode', 'vscode':'siVisualstudiocode',
  'intellij':'siIntellijidea', 'maven':'siApachemaven',
  'gradle':'siGradle', 'sonarqube':'siSonarqube',

  // Payments / Services
  'stripe':'siStripe', 'firebase':'siFirebase',
  'vercel':'siVercel', 'netlify':'siNetlify',
  'heroku':'siHeroku', 'render':'siRender',
  'railway':'siRailway', 'cloudflare':'siCloudflare',
  'twilio':'siTwilio', 'sendgrid':'siSendgrid',
  'elasticsearch':'siElasticsearch',
  'kafka':'siApachekafka', 'rabbitmq':'siRabbitmq',
  'keycloak':'siKeycloak', 'auth0':'siAuth0',
}

function resolveIcon(name: string): SiIcon | null {
  const key = name.toLowerCase().trim()

  const iconsDict = (si as unknown) as Record<string, SiIcon>

  // 1. Direct alias lookup
  const aliasKey = ALIASES[key]
  if (aliasKey) {
    const icon = iconsDict[aliasKey]
    if (icon) return icon
  }

  // 2. Auto-generate slug: "Spring Boot" → "siSpringboot"
  const autoSlug = 'si' + name
    .toLowerCase()
    .replace(/\s+/g, '')
    .replace(/[.\-_]/g, '')
    .replace(/^./, c => c.toUpperCase())

  const autoIcon = iconsDict[autoSlug]
  if (autoIcon) return autoIcon

  // 3. Try removing special chars: "Node.js" → "siNodejs"
  const cleanSlug = 'si' + name.replace(/[^a-zA-Z0-9]/g, '')
    .replace(/^./, c => c.toUpperCase())
  const cleanIcon = iconsDict[cleanSlug]
  if (cleanIcon) return cleanIcon

  return null
}


// ── Renderers ──────────────────────────────────────────────────
function SiSvg({ icon, size }: { icon: SiIcon; size: number }) {
  return (
    <svg
      role="img"
      viewBox="0 0 24 24"
      width={size}
      height={size}
      fill={`#${icon.hex}`}
      xmlns="http://www.w3.org/2000/svg"
      aria-label={icon.title}
      style={{ display: 'block' }}
    >
      <path d={icon.path} />
    </svg>
  )
}

function Fallback({ name, size }: { name: string; size: number }) {
  const initials = name.split(/[\s.+_-]/).filter(Boolean)
    .slice(0, 2).map(w => w[0].toUpperCase()).join('')
  return (
    <span
      className="inline-flex items-center justify-center w-full h-full rounded-md bg-(--chb) text-(--t3) font-bold"
      style={{ fontSize: Math.max(Math.floor(size * 0.38), 9) }}
    >
      {initials || name.slice(0, 2).toUpperCase()}
    </span>
  )
}

function IconBox({ name, size }: { name: string; size: number }) {
  const icon = resolveIcon(name)
  return (
    <span
      className="inline-flex items-center justify-center shrink-0 overflow-hidden rounded-md"
      style={{ width: size, height: size }}
      title={name}
    >
      {icon ? <SiSvg icon={icon} size={size} /> : <Fallback name={name} size={size} />}
    </span>
  )
}

// ── Exported components ────────────────────────────────────────

interface TechIconProps {
  name: string
  size?: number
  showLabel?: boolean
  className?: string
}

/** Ícone standalone, opcionalmente com label embaixo */
export default function TechIcon({ name, size = 32, showLabel = false, className = '' }: TechIconProps) {
  return (
    <span className={clsx('inline-flex flex-col items-center gap-1', className)} title={name}>
      <IconBox name={name} size={size} />
      {showLabel && (
        <span className="text-xs text-(--t4) font-medium leading-none text-center">{name}</span>
      )}
    </span>
  )
}

/** Pill badge com ícone + nome — para listas de tags */
export function TechBadge({ name, size = 14 }: { name: string; size?: number }) {
  return (
    <span className="chip">
      <IconBox name={name} size={size} />
      {name}
    </span>
  )
}

/** Ícone nu — para skill bars, tabelas */
export function SkillIcon({ name, size = 22 }: { name: string; size?: number }) {
  return <IconBox name={name} size={size} />
}

/** Grid de ícones com labels — para stack tecnológica na home */
export function TechGrid({ names, iconSize = 34 }: { names: string[]; iconSize?: number }) {
  return (
    <div className="flex flex-wrap justify-center gap-5 sm:gap-6">
      {names.map(name => (
        <TechIcon key={name} name={name} size={iconSize} showLabel />
      ))}
    </div>
  )
}