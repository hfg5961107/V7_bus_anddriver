package com.rvakva.travel.devkit.expend

import java.io.File
import java.io.InputStream

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/12 下午4:32
 */
fun File.writeIn(inputStream: InputStream) {
    inputStream.use { fis ->
        outputStream().use { fos ->
            val buff = ByteArray(1024)
            var len = fis.read(buff)
            while (len != -1) {
                fos.write(buff, 0, len)
                len = fis.read(buff)
            }
        }
    }
}