package com.soho.sohoapp.network

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.IOException


/**
 * Created by chowii on 17/1/18.
 */
class TwilioMessageTypeAdapterFactory : TypeAdapterFactory {


    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T> {

        val delegate = gson.getDelegateAdapter(this, type)
        val elementAdapter = gson.getAdapter(JsonElement::class.java)

        return object : TypeAdapter<T>() {

            @Throws(IOException::class)
            override fun write(out: JsonWriter, value: T) {
                delegate.write(out, value)
            }

            @Throws(IOException::class)
            override fun read(read: JsonReader): T? {

                val dataString = "data"

                var jsonElement = elementAdapter.read(read)
                if (jsonElement.isJsonObject) {
                    val jsonObject = jsonElement.asJsonObject
                    if (jsonObject.entrySet().isNotEmpty()) {
                        if (jsonObject.has(dataString)) {
                            if (jsonObject.get(dataString).isJsonObject) {
                                jsonElement = jsonObject.get(dataString)
                            } else if (jsonObject.get(dataString).isJsonArray && type.rawType.isAssignableFrom(List::class.java)) {
                                jsonElement = jsonObject.get(dataString)
                            }
                        }
                    } else {
                        return null
                    }
                }

                return delegate.fromJsonTree(jsonElement)
            }
        }
    }
}

