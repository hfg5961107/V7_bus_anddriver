package com.rvakva.travel.devkit.retrofit.progress

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*
import java.io.IOException

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/9 下午3:26
 */
class ProgressResponseBody(
    private val responseBody: ResponseBody,
    private val url: String,
    private val block: (
        url: String?,
        bytesRead: Long,
        contentLength: Long,
        done: Boolean
    ) -> Unit
) : ResponseBody() {

    override fun contentType(): MediaType? {
        return responseBody.contentType()
    }

    override fun contentLength(): Long {
        return responseBody.contentLength()
    }

    private var bufferedSource: BufferedSource? = null

    override fun source(): BufferedSource {

        return bufferedSource?.let {
            it
        } ?: source(responseBody.source()).buffer().let {
            bufferedSource = it
            it
        }

    }

    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            var totalBytesRead = 0L

            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                totalBytesRead += if (bytesRead != -1L) bytesRead else 0
                block(
                    url,
                    totalBytesRead,
                    responseBody.contentLength(),
                    bytesRead == -1L
                )
                return bytesRead
            }
        }
    }

}