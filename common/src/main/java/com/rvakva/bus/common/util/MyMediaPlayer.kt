package com.rvakva.bus.common.util

import android.media.MediaPlayer
import com.rvakva.bus.common.R
import com.rvakva.bus.common.X
import com.rvakva.travel.devkit.Ktx

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/18 下午4:26
 */
enum class MyMediaPlayerType {
    ONLINE
}

class MyMediaPlayer private constructor() : MediaPlayer.OnCompletionListener {

    private object SingletonHolder {
        val INSTANCE = MyMediaPlayer()
    }

    companion object {
        fun getInstance() = SingletonHolder.INSTANCE
    }

    private var mediaPlayer: MediaPlayer? = null

    private val playTypeMap = mapOf(
        MyMediaPlayerType.ONLINE to R.raw.work_online
    )

    fun play(playerType: MyMediaPlayerType) {
        mediaPlayer?.let {
            if (it.isPlaying) {
                return
            }
        }

        if (mediaPlayer == null) {
            playTypeMap[playerType]?.let {
                MediaPlayer.create(Ktx.getInstance().app, it).let {
                    it.setOnCompletionListener(this)
                    it.start()
                    mediaPlayer = it
                }
            }
        }
    }

    override fun onCompletion(mp: MediaPlayer?) {
        mediaPlayer?.let {
            it.stop()
            it.release()
        }
        mediaPlayer = null
    }
}