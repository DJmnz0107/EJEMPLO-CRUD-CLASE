package RecyclerViewHelpers

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bryan.miranda.crudbryan1b.R

class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
    val txtCancion: TextView = view.findViewById(R.id.txtCancion)
}