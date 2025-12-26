package com.pab.tixid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pab.tixid.R
import com.pab.tixid.models.Admin

class AdminAdapter(
    private val onEdit: (Admin) -> Unit,
    private val onDelete: (Admin) -> Unit
) : RecyclerView.Adapter<AdminAdapter.AdminViewHolder>() {

    private var admins: List<Admin> = emptyList()

    class AdminViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvEmail: TextView = itemView.findViewById(R.id.tv_email)
        val tvPhone: TextView = itemView.findViewById(R.id.tv_phone)
        val tvCreatedAt: TextView = itemView.findViewById(R.id.tv_created_at)
        val btnEdit: ImageView = itemView.findViewById(R.id.btn_edit)
        val btnDelete: ImageView = itemView.findViewById(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin, parent, false)
        return AdminViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {
        val admin = admins[position]

        holder.tvName.text = admin.name
        holder.tvEmail.text = admin.email
        holder.tvPhone.text = admin.phone ?: "-"
        holder.tvCreatedAt.text = "Dibuat: ${admin.created_at}"

        holder.btnEdit.setOnClickListener {
            onEdit(admin)
        }

        holder.btnDelete.setOnClickListener {
            onDelete(admin)
        }
    }

    override fun getItemCount(): Int = admins.size

    fun updateAdmins(newAdmins: List<Admin>) {
        admins = newAdmins
        notifyDataSetChanged()
    }
}

