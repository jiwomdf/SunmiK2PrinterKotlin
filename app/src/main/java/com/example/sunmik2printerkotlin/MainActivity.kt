package com.example.sunmik2printerkotlin

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.sunmik2printerkotlin.di.Dependency
import com.example.sunmik2printerkotlin.remote.json.ResponseClass
import com.example.sunmik2printerkotlin.remote.request.RequestClass
import com.example.sunmik2printerkotlin.util.Event
import com.sunmi.extprinterservice.ExtPrinterService
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity(), MainPresenterView {

    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this, Dependency.inject())
        presenter.postCounter(RequestClass())
    }

    override fun onSuccess(result: Event.Success<ResponseClass>) {
        pb_loading.visibility = View.GONE
        printKiosK()
    }

    override fun onLoading() {
        pb_loading.visibility = View.VISIBLE
    }

    override fun onFailed(error: Event.Error) {
        pb_loading.visibility = View.GONE
        Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
    }

    private fun printKiosK() {
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