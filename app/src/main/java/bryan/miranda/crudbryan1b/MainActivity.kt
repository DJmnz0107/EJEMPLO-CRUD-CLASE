package bryan.miranda.crudbryan1b

import RecyclerViewHelpers.Adaptador
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.dataClassMusica
import java.util.UUID

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //1- Mandar a llamar a todos los elementos
        val txtNombre = findViewById<EditText>(R.id.txtNombre)
        val txtDuracion = findViewById<EditText>(R.id.txtDuracion)
        val txtAutor = findViewById<EditText>(R.id.txtAutor)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)

        val rcvMusica = findViewById<RecyclerView>(R.id.rcvMusica)
        //Asignarle un layout al RecyclerView

        rcvMusica.layoutManager = LinearLayoutManager(this)

        /////// TODO:  mostrar datos

        fun mostrarDatos(): List<dataClassMusica> {
            //1- Crear un objeto de la clase conexion

            val objconexion = ClaseConexion().cadenaConexion()

            //2- Creo un statement

            val statement = objconexion?.createStatement()

            val resultSet = statement?.executeQuery("SELECT * FROM tbMusica")!!

            //Voy a guardar todo lo que me traiga el select

            val canciones = mutableListOf<dataClassMusica>()

            while (resultSet.next()) {
                val nombre = resultSet.getString("nombreCancion")
                val duracion = resultSet.getInt("duracion")
                val autor = resultSet.getString("autor")
                val uuid = resultSet.getString("uuid")

                val cancion = dataClassMusica(uuid, nombre, duracion, autor)

                canciones.add(cancion)
            }

            return canciones

        }

        //Aisgnar el adapter al RecyclerView
        //Ejecutar la funcion para mostrar datos

        CoroutineScope(Dispatchers.IO).launch {

            //Creo una variable que ejecute la funcion de mostrarDatos

            val musicaDB = mostrarDatos()

            withContext(Dispatchers.Main){
                val miAdaptador = Adaptador(musicaDB)
                rcvMusica.adapter = miAdaptador
            }

        }

        fun limpiarCampos() {

            txtNombre.text.clear()
            txtDuracion.text.clear()
            txtAutor.text.clear()
        }


        //2- Programar el boton
        btnAgregar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {

                //1- Crear un objeto de la clase conexion
                val objConexion = ClaseConexion().cadenaConexion()

                //2- Crear una variable que contenga un PrepareStatement
                val addMusica = objConexion?.prepareStatement("insert into tbMusica(nombreCancion, duracion, autor, uuid) values(?, ?, ?, ?)")!!

                addMusica.setString(1, txtNombre.text.toString())
                addMusica.setInt(2, txtDuracion.text.toString().toInt())
                addMusica.setString(3, txtAutor.text.toString())
                addMusica.setString(4, UUID.randomUUID().toString())
                addMusica.executeUpdate()

                val nuevasCanciones = mostrarDatos()
                withContext(Dispatchers.Main) {
                    //Actualizo al adaptador con los datos nuevos

                    (rcvMusica.adapter as? Adaptador)?.actualizarListado(nuevasCanciones)
                    limpiarCampos()
                }





            }


        }


    }


}