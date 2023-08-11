package com.krubo.apmlib.block

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import com.krubo.apmlib.db.dao.KLooperPrinter
import com.krubo.apmlib.db.dao.KLooperPrinterHelper
import java.lang.StringBuilder

object KLooperPrinter {

    private const val INTERVAL = 5 * 1000L
    private const val PRINTER_START = ">>>>> Dispatching to "
    private const val PRINTER_END = "<<<<< Finished to "

    @Volatile
    private var PRINTER_TIME = 0L

    @Volatile
    private var PRINTER_MSG = ""

    @Volatile
    private var workThread: HandlerThread? = null

    @Volatile
    private var handler: Handler? = null

    @Synchronized
    fun init() {
        workThread = HandlerThread("k_looper_printer")
        handler = object : Handler(workThread!!.looper) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                val builder = StringBuilder()
                Looper.getMainLooper().thread.stackTrace.forEach {
                    builder.append(it.toString())
                }
                KLooperPrinterHelper.insertItem(
                    KLooperPrinter(
                        PRINTER_TIME, INTERVAL, builder.toString()
                    )
                )
            }
        }
        Looper.getMainLooper().setMessageLogging {
            when {
                it.startsWith(PRINTER_START) -> {
                    handler?.removeCallbacksAndMessages(null)
                    PRINTER_TIME = System.currentTimeMillis()
                    PRINTER_MSG = it.replace(PRINTER_START, "")
                    handler?.sendEmptyMessageDelayed(0, INTERVAL)
                }

                it.startsWith(PRINTER_END) -> {
                    handler?.removeCallbacksAndMessages(null)
                    KLooperPrinterHelper.updateItem(
                        PRINTER_TIME, System.currentTimeMillis() - PRINTER_TIME
                    )
                }

            }
        }
    }
}