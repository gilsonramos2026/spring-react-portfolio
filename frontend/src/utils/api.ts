import axios from "axios";

/**
 * Base URL for all API calls.
 * - Dev:  '/api'  (Vite proxy forwards to localhost:8080)
 * - Prod: 'https://spring-react-portfolio-production.up.railway.app/api' (ou sua URL da Railway)
 */
const BASE = import.meta.env.VITE_API_URL || "/api";

/**
 * Separate base for static assets (uploads) served by the backend
 * OUTSIDE the /api context-path.
 */
const ASSET_BASE = BASE.replace(/\/api\/?$/, "");

export const publicApi = axios.create({ baseURL: BASE });
export const adminApi = axios.create({ baseURL: BASE });

adminApi.interceptors.request.use((cfg) => {
  cfg.headers["X-Admin-Key"] = localStorage.getItem("admin_key") ?? "";
  return cfg;
});

/**
 * Resolves a relative upload path (e.g. /uploads/avatars/uuid.jpg)
 * to a full URL that the browser can load.
 */
export function resolveAssetUrl(path?: string | null): string {
  if (!path) return "";
  if (path.startsWith("http://") || path.startsWith("https://")) return path;
  
  let normalized = path.startsWith("/") ? path : `/${path}`;
  
  // CORRIGIDO: Mantém exatamente o caminho de assets limpo (ex: /uploads/foto.png),
  // sem injetar o /api indesejado, alinhando com o WebConfig do Spring Boot.
  
  return `${ASSET_BASE}${normalized}`;
}