package com.example.sunmik2printerkotlin

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Toast
import com.example.sunmik2printerkotlin.di.Dependency
import com.example.sunmik2printerkotlin.remote.json.QueueNumberResponse
import com.example.sunmik2printerkotlin.util.Event
import com.sunmi.extprinterservice.ExtPrinterService
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

/**
 * https://www.printkiosk.com?norec=00002990-6ff1-11eb-9ae6-f7140383
 */

class MainActivity : AppCompatActivity(), MainPresenterView {

    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this, Dependency.inject())

        val norec = getQueryString()

        presenter.getQueueNumber("00002990-6ff1-11eb-9ae6-f7140383")
        /* if(!norec.isNullOrEmpty())
            presenter.getQueueNumber(norec)
        else
            Toast.makeText(this, "Deep Linking Query Param is empty", Toast.LENGTH_SHORT).show() */
    }

    private fun getQueryString(): String? {

        //norec=00002990-6ff1-11eb-9ae6-f7140383
        val data: Uri? = intent?.data
        return data?.getQueryParameter("norec")
    }

    override fun onSuccessGetQueue(result: Event.Success<QueueNumberResponse>) {
        pb_loading.visibility = View.GONE
        printKiosK(result.data)
    }

    override fun onLoadingGetQueue() {
        pb_loading.visibility = View.VISIBLE
    }

    override fun onFailedGetQueue(error: Event.Error) {
        pb_loading.visibility = View.GONE
        Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
    }

    private fun printKiosK(data: QueueNumberResponse) {

        data.jumlahantrian
        data.noantrian
        data.ruangan
        data.status

        //for more documentation, read here: https://file.cdn.sunmi.com/SUNMIDOCS/Sunmi-k2-Print-Service-Development-Documentation.pdf
        var ext: ExtPrinterService
        val serviceConnection: ServiceConnection = object : ServiceConnection {
            override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
                try {
                    ext = ExtPrinterService.Stub.asInterface(iBinder)
                    ext.printerInit()
                    ext.printText("123456\n" +
                            "123456\n" +
                            "123456\n" +
                            "123456\n" +
                            "123456\n" +
                            "123456\n" +
                            "123456\n" +
                            "123456\n" +
                            "123456\n" +
                            "123456\n" +
                            "123456\n" +
                            "123456\n" +
                            "123456\n" +
                            "123456\n" +
                            "123456\n" +
                            "123456\n" +
                            "123456\n" +
                            "123456\n" +
                            "123456\n" +
                            "123456\n"
                    )
                    Toast.makeText(this@MainActivity, "Printing", Toast.LENGTH_SHORT).show()
                    ext.cutPaper(0, 0)
                } catch (ex: Exception) {
                    Toast.makeText(this@MainActivity, ex.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onServiceDisconnected(componentName: ComponentName) {
                Toast.makeText(this@MainActivity, "Disconected", Toast.LENGTH_SHORT).show()
                unbindService(this)
            }
        }
        val intent = Intent()
        intent.setPackage("com.sunmi.extprinterservice")
        intent.action = "com.sunmi.extprinterservice.PrinterService"
        bindService(intent, serviceConnection, BIND_AUTO_CREATE)
    }

}