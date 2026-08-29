import axios from "axios";

/**
 * Base URL for all API calls.
 * - Dev:  '/api'  (Vite proxy forwards to localhost:8080)
 * - Prod: 'https://your-backend.onrender.com/api'
 */
const BASE = import.meta.env.VITE_API_URL || "/api";

/**
 * Separate base for static assets (uploads) served by the backend
 * OUTSIDE the /api context-path.
 * - Dev:  ''  (Vite proxy /uploads → localhost:8080/uploads)
 * - Prod: 'https://your-backend.onrender.com'  (strip /api suffix)
 */
const ASSET_BASE = BASE.replace(/\/api\/?$/, "");

export const publicApi = axios.create({ baseURL: BASE });
export const adminApi = axios.create({ baseURL: BASE });

adminApi.interceptors.request.use((cfg) => {
  cfg.headers["X-Admin-Key"] = localStorage.getItem("admin_key") ?? "";
  return cfg;
});

/**
 * Resolves a relative upload path (e.g. /uploads/projects/1/uuid.jpg)
 * to a full URL that the browser can load.
 *
 * External URLs (https://...) are returned unchanged.
 * Relative paths get prefixed with ASSET_BASE.
 *
 * Examples:
 *   resolveAssetUrl('/uploads/projects/1/foo.jpg')
 *     Dev  → '/uploads/projects/1/foo.jpg'   (Vite proxies to :8080)
 *     Prod → 'https://api.x.com/uploads/projects/1/foo.jpg'
 *
 *   resolveAssetUrl('https://i.imgur.com/foo.jpg')
 *     → 'https://i.imgur.com/foo.jpg'  (unchanged)
 */
export function resolveAssetUrl(path?: string | null): string {
  if (!path) return "";
  if (path.startsWith("http://") || path.startsWith("https://")) return path;
  const normalized = path.startsWith("/") ? path : `/${path}`;
  return `${ASSET_BASE}${normalized}`;
}
