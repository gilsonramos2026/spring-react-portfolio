import type { ReactNode } from "react";
export default function EmptyState({
  icon,
  title,
  description,
}: {
  icon?: ReactNode;
  title: string;
  description?: string;
}) {
  return (
    <div className="flex flex-col items-center justify-center py-16 gap-3 text-center">
      {icon && <span className="text-(--t4) opacity-60">{icon}</span>}
      <p className="text-(--t3) font-medium">{title}</p>
      {description && <p className="text-(--t4) text-sm">{description}</p>}
    </div>
  );
}
