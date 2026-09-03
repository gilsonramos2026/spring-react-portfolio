import axios from "axios";

/**
 * Base URL for all API calls.
 * - Dev:  '/api'  (Vite proxy forwards to localhost:8080)
 * - Prod: 'https://spring-react-portfolio-production.up.railway.app/api'
 */
const BASE = import.meta.env.VITE_API_URL || "/api";

/**
 * CORRIGIDO: Como o Spring Boot exige o context-path (/api) para entregar as mídias,
 * a base de assets deve ser EXATAMENTE igual à base da API.
 */
const ASSET_BASE = BASE;

export const publicApi = axios.create({ baseURL: BASE });
export const adminApi = axios.create({ baseURL: BASE });

adminApi.interceptors.request.use((cfg) => {
  cfg.headers["X-Admin-Key"] = localStorage.getItem("admin_key") ?? "";
  return cfg;
});

/**
 * Resolves a relative upload path (e.g. /uploads/avatars/uuid.jpg ou /api/uploads/...)
 * to a full URL that the browser can load.
 */
export function resolveAssetUrl(path?: string | null): string {
  if (!path) return "";
  if (path.startsWith("http://") || path.startsWith("https://")) return path;
  
  // Limpa possíveis duplicações de '/api' que venham gravadas do banco de dados
  let cleanPath = path;
  if (path.startsWith("/api/")) {
    cleanPath = path.substring(4); // Remove o '/api' do início para não duplicar com o ASSET_BASE
  } else if (path.startsWith("api/")) {
    cleanPath = path.substring(3);
  }
  
  let normalized = cleanPath.startsWith("/") ? cleanPath : `/${cleanPath}`;
  
  // Retorna a URL perfeitamente montada contendo o /api necessário para o Railway
  return `${ASSET_BASE}${normalized}`;
}
