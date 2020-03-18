package com.example.uts_dani

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_dashboard.*
import org.json.JSONArray
import org.json.JSONObject

class Dashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        button.setOnClickListener{
            val sharedPreferences=getSharedPreferences("CEKLOGIN", Context.MODE_PRIVATE)
            val editor=sharedPreferences.edit()

            editor.putString("STATUS","0")
            editor.apply()

            startActivity(Intent(this@Dashboard,MainActivity::class.java))
            finish()
        }

        btn_save.setOnClickListener {
            val judul = editTextJudul.text.toString()
            val waktu = editTextWaktu.text.toString()
            val penulis = editTextPenulis.text.toString()
            val isi = editTextIsi.text.toString()
            postServer(judul, waktu, penulis, isi)
            Log.i("result",judul+waktu+penulis+isi)
            startActivity(Intent(this, Dashboard::class.java))
        }

        //View Data

        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager= LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val berita=ArrayList<Berita>()

        AndroidNetworking.get("http://192.168.1.5/berita_uts/berita.php")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.i("_kotlinResponse", response.toString())

                    val jsonArray = response.getJSONArray("result")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        Log.i("_kotlinTitle", jsonObject.optString("judul_berita"))

                        var isi1=jsonObject.optString("judul_berita").toString()
                        var isi2=jsonObject.optString("waktu_berita").toString()
                        var isi3=jsonObject.optString("penulis_berita").toString()
                        var isi4=jsonObject.optString("isi_berita").toString()

                        berita.add(Berita("$isi1", "$isi2", "$isi3", "$isi4"))
                    }

                    val adapter=BeritaAdapter(berita)
                    recyclerView.adapter=adapter
                }

                override fun onError(anError: ANError) {
                    Log.i("_err", anError.toString())
                }
            })
    }

    fun postServer(data1: String, data2: String, data3: String, data4: String) {
        Log.i("result",data1+data2+data3+data4)
        AndroidNetworking.post("http://192.168.1.5/berita_uts/insert.php")
            .addBodyParameter("judul_berita", data1)
            .addBodyParameter("waktu_berita", data2)
            .addBodyParameter("penulis_berita", data3)
            .addBodyParameter("isi_berita", data4)
            .setPriority(Priority.MEDIUM).build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {
                }
                override fun onError(anError: ANError?) {
                    Log.i("_err", anError.toString())
                }
            })
    }
}