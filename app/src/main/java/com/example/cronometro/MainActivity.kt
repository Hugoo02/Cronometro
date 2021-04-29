package com.example.cronometro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    var horas : MutableList<String> = arrayListOf("")
    var isWorking = false
    var currentTime: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listViewHour : ListView = findViewById(R.id.listViewHora)

        listViewHour.adapter = HoraAdapter()
    }

        inner class HoraAdapter : BaseAdapter() {
            override fun getCount(): Int {
                return horas.size
            }

            override fun getItem(position: Int): Any {
                return horas[position]
            }

            override fun getItemId(position: Int): Long {
                return 0
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

                val view = layoutInflater.inflate(R.layout.layout_hours, parent, false)
                var cronometro = findViewById<Chronometer>(R.id.cronometro)
                val startStop: Button = findViewById(R.id.buttonStartStop)
                val restart: FloatingActionButton = findViewById(R.id.restart)
                val textViewHora = view.findViewById<TextView>(R.id.textViewHora)

                startStop.setOnClickListener {
                    if (!isWorking) {

                        cronometro.base = SystemClock.elapsedRealtime() - currentTime
                        cronometro.start()
                        startStop.setText("Stop")
                        isWorking = true

                    } else {

                        currentTime = SystemClock.elapsedRealtime() - cronometro.base
                        cronometro.stop();
                        startStop.setText("Start")
                        isWorking = false

                    }
                }

                restart.setOnClickListener {

                    cronometro.setBase(SystemClock.elapsedRealtime())
                    cronometro.stop()
                    currentTime = 0
                    startStop.setText("Start")
                    isWorking = false

                }

                textViewHora.text = horas[position]

                findViewById<Button>(R.id.buttonLap).setOnClickListener {

                    horas.add(position, cronometro.text.toString())
                    notifyDataSetChanged()

                }

                return view
            }

    }


}