package de.trackcovidcluster.server

import android.content.ContentValues
import android.os.AsyncTask
import android.util.Log
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

internal class SendJsonToServer : AsyncTask<String?, String?, String?>() {

    public override fun doInBackground(vararg params: String?): String? {
        var JsonResponse: String? = null
        val JsonDATA = params[0]
        var urlConnection: HttpURLConnection?
        urlConnection = null
        var reader: BufferedReader? = null
        try {
            val url = URL("https://api.trackcovidcluster.de:12345/json")
            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.doOutput = true

            // is output buffer writter
            urlConnection!!.requestMethod = "POST"
            urlConnection.setRequestProperty("Content-Type", "application/json")
            urlConnection.setRequestProperty("Accept", "application/json")

            //set headers and method
            val writer: Writer = BufferedWriter(
                OutputStreamWriter(
                    urlConnection.outputStream,
                    "UTF-8"
                )
            )
            writer.write(JsonDATA)

            // json data
            writer.close()
            val inputStream = urlConnection.inputStream

            //input stream
            val buffer = StringBuffer()
            if (inputStream == null) {
                // Nothing to do.
                return null
            }
            reader = BufferedReader(InputStreamReader(inputStream))
            var inputLine: String
            while (reader.readLine()
                    .also { inputLine = it } != null
            ) buffer.append(
                """
    $inputLine

    """.trimIndent()
            )
            if (buffer.length == 0) {
                // Stream was empty. No point in parsing.
                return null
            }
            JsonResponse = buffer.toString()

            //response data
            Log.d("", "_________________RESPONSE________________")
            Log.i(ContentValues.TAG, JsonResponse)
            try {
                //send to post execute
                return JsonResponse
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            urlConnection?.disconnect()
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    Log.e(ContentValues.TAG, "Error closing stream", e)
                }
            }
        }
        return null
    }

    override fun onPostExecute(s: String?) {}
}