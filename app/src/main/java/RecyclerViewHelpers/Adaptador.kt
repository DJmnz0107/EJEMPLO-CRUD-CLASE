package RecyclerViewHelpers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bryan.miranda.crudbryan1b.R
import modelo.dataClassMusica

class Adaptador(var Datos: List<dataClassMusica>): RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Conectar el recycler view con la card

        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent, false)

        return ViewHolder(vista)

    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Poder darle clic a cada elemento de la card

        val item = Datos[position]

        holder.txtCancion.text = item.nombreCancion

    }

}