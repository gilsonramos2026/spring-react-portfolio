import { useState } from "react";
import {
  useAdminCertifications,
  useCreateCertification,
  useUpdateCertification,
  useDeleteCertification,
} from "../../hooks/useCertifications";
import { useForm } from "react-hook-form";
import type { Certification } from "../../types";
import { AdminHeader } from "../../components/admin/AdminHeader";
import { CertificationsTable } from "../../components/admin/certifications/CertificationsTable";
import { CertificationFormModal } from "../../components/admin/certifications/CertificationFormModal";


type Editing = Certification | "new" | null;

export function AdminCertifications() {
  const { data: items } = useAdminCertifications();
  const create = useCreateCertification();
  const update = useUpdateCertification();
  const del = useDeleteCertification();

  const [editing, setEditing] = useState<Editing>(null);
  const { reset } = useForm<Partial<Certification>>();

  const open = (c: Certification | "new") => {
    setEditing(c);
    reset(c === "new" ? {} : c);
  };
  const close = () => {
    setEditing(null);
    reset();
  };

  return (
    <div className="space-y-6">
      <AdminHeader
        title="Certificações"
        count={items?.length}
        countLabel="registros"
        buttonLabel="Nova"
        onAdd={() => open("new")}
      />

      <CertificationsTable
        items={items}
        onOpen={(c) => open(c)}
        onDelete={(id) => {
          if (confirm("Remover?")) del.mutate(id);
        }}
      />

      {editing !== null && (
        <CertificationFormModal
          editing={editing}
          onClose={close}
          isPending={create.isPending || update.isPending}
          onSubmit={(d) => {
            if (editing === "new") {
              create.mutate(d, { onSuccess: close });
            } else {
              update.mutate(
                { id: (editing as Certification).id, data: d },
                { onSuccess: close },
              );
            }
          }}
        />
      )}
    </div>
  );
}