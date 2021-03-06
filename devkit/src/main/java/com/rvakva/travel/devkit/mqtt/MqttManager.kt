package com.rvakva.travel.devkit.mqtt

import android.util.Log
import com.rvakva.travel.devkit.Ktx
import com.rvakva.travel.devkit.KtxViewModel
import com.rvakva.travel.devkit.expend.loge
import com.rvakva.travel.devkit.expend.toAesDecrypt
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

/**
 * Copyright (C), 2020 - 2999, Sichuan Xiaoka Technology Co., Ltd.
 * @Description:
 * @Author:         胡峰
 * @CreateDate:     2020/5/26 上午9:57
 */
class MqttManager {

    private object SingletonHolder {
        val INSTANCE = MqttManager()
    }

    companion object {
        fun getInstance() = SingletonHolder.INSTANCE
        private const val CONNECTION_TIME_OUT = 10
        private const val KEEP_LIVE_INTERVAL = 20
        private const val QOS = 2
    }

    private var client: MqttAndroidClient? = null

    var mqttConfig: MqttConfigModel? = null


    fun changeTopic(mqttConfig: MqttConfigModel) {
        this.mqttConfig?.topic = mqttConfig.topic
    }

    fun initConnect(config: MqttConfigModel) {
        destroyConnect()
        MqttAndroidClient(
            Ktx.getInstance().app,
            "tcp://${config.broker}:${config.portTcp}",
            "bus-driver-${Ktx.getInstance().userDataSource.userId}",
            MemoryPersistence(), MqttAndroidClient.Ack.AUTO_ACK
        ).let {
            it.setCallback(object : MqttCallbackExtended {
                override fun connectComplete(reconnect: Boolean, serverURI: String?) {
                    "connectComplete $reconnect".loge()
//                    it.setBufferOpts(DisconnectedBufferOptions().apply {
//                        isBufferEnabled = true
//                        bufferSize = 100
//                        isPersistBuffer = false
//                        isDeleteOldestMessages = true
//                    })

                    mutableListOf(
                        "/bus/driver/${Ktx.getInstance().appKeyDataSource.appKey}/${Ktx.getInstance().userDataSource.userId}",
                        "/bus/driver/${Ktx.getInstance().appKeyDataSource.appKey}"
                    ).forEach {
                        subScribeToTopic(it)
                    }
                }

                override fun messageArrived(topic: String?, message: MqttMessage?) {
                    message?.let {
                        "arrived  $it".loge()
                        KtxViewModel.mqttLiveData.postEventValue(it)
                    }
                }

                override fun connectionLost(cause: Throwable?) {
                    "connectLost   ${cause.toString()}".loge()
                }

                override fun deliveryComplete(token: IMqttDeliveryToken?) {
                    "complete".loge()
                }
            })

            it.connect(createOption(config), null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    "onConnectSuccess".loge()
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    "onConnectFailure   ${exception?.message}".loge()
                }
            })
            client = it
            mqttConfig = config
        }
    }

    private fun subScribeToTopic(topic: String) {
        try {
            client?.subscribe(topic, QOS, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    ("onSubscribe  $topic  Success").loge()
                }

                override fun onFailure(
                    asyncActionToken: IMqttToken?,
                    exception: Throwable?
                ) {
                    "onSubscribe${topic}Failure".loge()
                }

            })
        } catch (e: MqttException) {
            e.fillInStackTrace()
        }
    }

    private fun createOption(mqttConfig: MqttConfigModel) = MqttConnectOptions().apply {
        isAutomaticReconnect = true
        isCleanSession = true
        userName = mqttConfig.userName?.toAesDecrypt() ?: ""
        password = mqttConfig.password?.toAesDecrypt()?.toCharArray() ?: "".toCharArray()
        connectionTimeout = CONNECTION_TIME_OUT
        keepAliveInterval = KEEP_LIVE_INTERVAL
//        setWill(pullTopic, "".toByteArray(), QOS, false)
    }

    fun destroyConnect() {
        "onDestroyConnect".loge()
        client?.apply {
            try {
                unregisterResources()
                close()
                disconnect()
                setCallback(null)
            } catch (e: MqttException) {
                e.fillInStackTrace()
            } catch (e: NullPointerException) {
                e.fillInStackTrace()
            } finally {
                client = null
            }
        }
        mqttConfig = null
    }

    fun publish(content: String?) {
        mqttConfig?.let { config ->
            if (!content.isNullOrBlank()) {
                try {
                    client?.let {
                        if (it.isConnected) {
                            Log.e("hufeng/publish",content)
                            it.publish(config.topic, MqttMessage(content.toByteArray()).apply {
                                qos = QOS
                            })
                        }
                    }
                } catch (e: Exception) {
                    e.fillInStackTrace()
                }

            }
        }
    }

}