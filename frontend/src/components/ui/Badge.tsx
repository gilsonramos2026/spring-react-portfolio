import clsx from "clsx";
import type { ReactNode } from "react";
type V = "brand" | "success" | "warning" | "muted";
const v: Record<V, string> = {
  brand:
    "bg-[rgba(14,165,233,0.15)] text-[var(--color-brand-400)] border-[rgba(14,165,233,0.25)]",
  success: "bg-emerald-500/15 text-emerald-400 border-emerald-500/25",
  warning: "bg-amber-500/15 text-amber-400 border-amber-500/25",
  muted: "bg-[var(--chb)] text-[var(--t3)] border-[var(--chbd)]",
};
export default function Badge({
  children,
  variant = "muted",
  className,
}: {
  children: ReactNode;
  variant?: V;
  className?: string;
}) {
  return (
    <span
      className={clsx(
        "inline-flex items-center gap-1 px-2.5 py-0.5 rounded-full text-xs font-semibold border",
        v[variant],
        className,
      )}
    >
      {children}
    </span>
  );
}
