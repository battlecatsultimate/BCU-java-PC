package io

import java.util.*
import javax.sound.sampled.Clip
import javax.sound.sampled.FloatControl
import javax.sound.sampled.LineEvent
import javax.sound.sampled.LineListener

class BCPlayer : LineListener {
    private val ind: Int
    private var c: Clip?
    private var master: FloatControl?
    private val loop: Long
    private val isLooping: Boolean
    private var rewinding = false
    var isPlaying = false
        private set

    constructor(c: Clip, ind: Int, looping: Boolean) {
        this.ind = ind
        this.c = c
        this.c.addLineListener(this)
        master = c.getControl(FloatControl.Type.MASTER_GAIN) as FloatControl
        loop = 0
        isLooping = looping
    }

    constructor(c: Clip, ind: Int, loop: Long, looping: Boolean) {
        this.ind = ind
        this.c = c
        this.c.addLineListener(this)
        master = c.getControl(FloatControl.Type.MASTER_GAIN) as FloatControl
        this.loop = loop
        isLooping = looping
        if (loop > 0 && loop * 1000 < c.getMicrosecondLength()) {
            c.setLoopPoints(milliToFrame(loop), -1)
        } else if (loop * 1000 >= c.getMicrosecondLength()) {
            c.loop(0)
        }
    }

    fun stop() {
        isPlaying = false
        if (c != null) {
            c.stop()
        }
    }

    override fun update(event: LineEvent) {
        if (event.getType() === LineEvent.Type.STOP) {
            isPlaying = false
            stop()
            if (ind >= 0 && ind != 20 && ind != 21 && ind != 22) {
                synchronized(BCMusic::class.java) {
                    val players: ArrayDeque<BCPlayer> = BCMusic.sounds.get(ind)
                    players?.push(this)
                }
            } else if (isLooping && loop >= c.getMicrosecondLength()) {
                return
            }
        }
    }

    fun release() {
        if (isPlaying) {
            stop()
        }
        isPlaying = false
        rewinding = false
        if (c != null) {
            c.close()
            c = null
        }
        master = null
    }

    fun rewind() {
        if (rewinding) {
            return
        }
        rewinding = true
        c.setFramePosition(0)
        rewinding = false
    }

    protected fun setLineListener(l: LineListener?) {
        c.addLineListener(l)
    }

    fun setVolume(vol: Int) {
        master.setValue(getVol(vol))
    }

    fun start() {
        if (isPlaying) {
            return
        }
        isPlaying = true
        c.start()
    }

    private fun milliToFrame(milli: Long): Int {
        val rate = c.getFormat().getFrameRate() as Long
        return (milli / 1000.0).toInt() * rate.toInt()
    }

    companion object {
        private const val FACTOR = 20
        private fun getVol(vol: Int): Float {
            return FACTOR * (Math.log10(vol.toDouble()).toFloat() - 2)
        }
    }
}
