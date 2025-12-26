package com.pab.tixid.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    /**
     * PENTING: Ganti IP address sesuai dengan IP komputer Anda!
     *
     * Cara cek IP komputer:
     * 1. Tekan Windows + R
     * 2. Ketik: cmd → Enter
     * 3. Ketik: ipconfig
     * 4. Cari "IPv4 Address" (contoh: 192.168.1.5)
     *
     * Aturan:
     * - Untuk EMULATOR: gunakan http://10.0.2.2/tix_id_api/
     * - Untuk HP FISIK: gunakan http://IP_PC_ANDA/tix_id_api/
     *
     * Contoh:
     * private const val BASE_URL = "http://192.168.1.5/tix_id_api/"
     * private const val BASE_URL = "http://192.168.100.10/tix_id_api/"
     *
     * CATATAN: HP dan PC harus tersambung ke WiFi/jaringan yang sama!
     */
    private const val BASE_URL = "http://192.168.1.2/tix_id_api/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

