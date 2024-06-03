package RecyclerViewHelpers

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import bryan.miranda.crudbryan1b.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion
import modelo.dataClassMusica
import java.util.UUID

class Adaptador(var Datos: List<dataClassMusica>): RecyclerView.Adapter<ViewHolder>() {

    //Funcion para que cuando agregue datos se actualice la lista automáticamente

    fun actualizarListado(nuevaLista:List<dataClassMusica>){
        Datos = nuevaLista
        notifyDataSetChanged()
    }


    //Fucion para eliminar datos
    fun eliminarDatos(nombreCancion:String, posicion:Int) {

        //Eliminarlo de la lista

        val listaDeDatos = Datos.toMutableList()

        listaDeDatos.removeAt(posicion)

        //Eliminarlo de la base de datos

        GlobalScope.launch(Dispatchers.IO) {

            //1- Creo un objeto de la clase conexion

            val objConexion = ClaseConexion().cadenaConexion()

            //2- Creo una variable que contenga un PrepareStatement

            val deleteCancion = objConexion?.prepareStatement("DELETE tbMusica where nombreCancion = ?")!!

            deleteCancion.setString(1, nombreCancion)
            deleteCancion.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()



        }

        Datos = listaDeDatos.toList()
        notifyItemRemoved(posicion)
        notifyDataSetChanged()
    }

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


        //TODO: clic al icono de borrar

        holder.imgBorrar.setOnClickListener {

            //Creo la alerta para confirmar el borrado, la eliminación

            //1- Invoco el contexto

            val context = holder.itemView.context

            //2-Creo la alerta usando los 3 pasos: titulo, mensaje y botones

            val builder = AlertDialog.Builder(context)

            builder.setTitle("Confirmación")

            builder.setMessage("¿Estás seguro que deseas borrar?")

            builder.setPositiveButton("Si"){
                dialog, wich ->

                eliminarDatos(item.nombreCancion, position)
            }

            builder.setNegativeButton("No") {
                dialog, wich ->

                dialog.dismiss()
            }

            val dialog = builder.create()

            dialog.show()



        }

        //TODO: clic al icono de editar

        holder.imgEditar.setOnClickListener {

            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)

            builder.setTitle("Actualizar")

            val cuadroTexto = EditText(context)

            cuadroTexto.setHint(item.nombreCancion)

            builder.setView(cuadroTexto)

            //Botones

            builder.setPositiveButton("Actualizar") {
                dialog, wich ->
                actualizarDatos(cuadroTexto.text.toString(), item.uuid)
            }

            builder.setNegativeButton("Cancelar") {
                dialog, wich ->
                dialog.dismiss()
            }

            val dialog = builder.create()

            dialog.show()
        }





    }
    fun actualizarDatos(nuevoNombre:String, uuid: String){

        GlobalScope.launch(Dispatchers.IO) {

            val objConexion = ClaseConexion().cadenaConexion()

            val updateCancion = objConexion?.prepareStatement("UPDATE tbMusica set nombreCancion = ? where uuid = ?")!!

            updateCancion.setString(1, nuevoNombre)
            updateCancion.setString(2, uuid)

            updateCancion.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()
        }


    }

}