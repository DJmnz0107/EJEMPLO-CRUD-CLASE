package bryan.miranda.crudbryan1b

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class detalle_canciones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_canciones)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Aqui en el código de mi pantalla recibo los valores

        val UUIDRecibido = intent.getStringExtra("UUID")
        val nombreRecibido = intent.getStringExtra("nombre")
        val duracionRecibida = intent.getIntExtra("duracion", 0)
        val autorRecibido = intent.getStringExtra("autor")

        val lblUUID = findViewById<TextView>(R.id.lblUUIDDetalle)
        val lblNombre = findViewById<TextView>(R.id.lblNombreDetalle)
        val lblDuracion = findViewById<TextView>(R.id.lblDuracionDetalle)
        val lblAutor = findViewById<TextView>(R.id.lblAutorDetalle)

        lblUUID.text = UUIDRecibido
        lblNombre.text = nombreRecibido
        lblDuracion.text = duracionRecibida.toString()
        lblAutor.text = autorRecibido


        val imgVolver = findViewById<ImageView>(R.id.imgVolver)

        imgVolver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}