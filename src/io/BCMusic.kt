package io

import common.pack.PackData
import common.pack.UserProfile
import common.util.Data
import common.util.stage.Music
import java.io.ByteArrayInputStream
import java.util.*
import java.util.function.Consumer
import javax.sound.sampled.*
import kotlin.collections.set

object BCMusic : Data {
    private const val FACTOR = 20
    private const val TOT = 125
    private val CACHE = arrayOfNulls<ByteArray>(TOT)
    var play = true
    var music: PackData.Identifier<Music>? = null
    var VOL_BG = 20
    var VOL_SE = 20
    private var secall = BooleanArray(TOT)
    var BG: BCPlayer? = null
    private var hit: Array<BCPlayer?>?
    private var hit1: Array<BCPlayer?>?
    private var baseHit: Array<BCPlayer?>?
    private var h = false
    private var h1 = false
    private var bh = false
    var sounds: MutableMap<Int, ArrayDeque<BCPlayer>> = HashMap<Int, ArrayDeque<BCPlayer>>()
    fun clear() {
        for (clips in sounds.values) {
            while (true) {
                var c: BCPlayer? = clips.poll()
                c = if (c != null) {
                    c.release()
                    null
                } else {
                    break
                }
            }
        }
        if (hit != null) {
            for (i in hit!!.indices) {
                hit!![i]!!.release()
                hit!![i] = null
            }
            hit = null
        }
        if (hit1 != null) {
            for (i in hit1!!.indices) {
                hit1!![i]!!.release()
                hit1!![i] = null
            }
            hit1 = null
        }
        if (baseHit != null) {
            for (i in baseHit!!.indices) {
                baseHit!![i]!!.release()
                baseHit!![i] = null
            }
            baseHit = null
        }
        if (BG != null) {
            BG.release()
            BG = null
        }
        sounds.clear()
    }

    @Synchronized
    fun flush(allow: Boolean) {
        if (hit == null) {
            hit = arrayOfNulls<BCPlayer>(2)
            for (i in hit!!.indices) {
                try {
                    hit!![i] = BCPlayer(openFile(UserProfile.Companion.getBCData().musics.get(20)), 20, false)
                    hit!![i].setVolume(VOL_SE)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        if (hit1 == null) {
            hit1 = arrayOfNulls<BCPlayer>(2)
            for (i in hit1!!.indices) {
                try {
                    hit1!![i] = BCPlayer(openFile(UserProfile.Companion.getBCData().musics.get(21)), 21, false)
                    hit1!![i].setVolume(VOL_SE)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        if (baseHit == null) {
            baseHit = arrayOfNulls<BCPlayer>(2)
            for (i in baseHit!!.indices) {
                try {
                    baseHit!![i] = BCPlayer(openFile(UserProfile.Companion.getBCData().musics.get(22)), 22, false)
                    baseHit!![i].setVolume(VOL_SE)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        for (i in 0 until TOT) {
            if (secall[i] && allow) try {
                if (CACHE[i] == null) loadSound(i, UserProfile.Companion.getBCData().musics.get(i), getVol(VOL_SE), false, 0) else loadSound(i, CACHE[i], getVol(VOL_SE), false, 0)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        secall = BooleanArray(TOT)
    }

    @Synchronized
    fun play(mus1: PackData.Identifier<Music>?, loop: Long) {
        music = mus1
        val f: Music? = music!!.get()
        if (f != null) setBG(f, loop)
    }

    fun preload() {
        for (i in Data.Companion.SE_ALL) CACHE[i] = UserProfile.Companion.getBCData().musics.get(i).data.getBytes()
    }

    @Synchronized
    fun setBG(f: Music?, loop: Long) {
        if (!play) return
        try {
            if (BG != null) {
                BG.release()
                loadSound(-1, f, getVol(VOL_BG), true, loop)
            } else {
                loadSound(-1, f, getVol(VOL_BG), true, loop)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Synchronized
    fun setBGVol(vol: Int) {
        VOL_BG = vol
        if (BG != null) {
            BG.setVolume(vol)
        }
    }

    @Synchronized
    fun setSE(ind: Int) {
        if (!play || VOL_SE == 0) return
        secall[ind] = true
    }

    @Synchronized
    fun setSEVol(vol: Int) {
        VOL_SE = vol
        for (players in sounds.values) {
            players.forEach(Consumer<BCPlayer> { player: BCPlayer -> player.setVolume(vol) })
        }
    }

    @Synchronized
    fun stopAll() {
        if (BG != null) BG.stop()
        for (players in sounds.values) {
            players.forEach(Consumer<BCPlayer> { player: BCPlayer -> player.stop() })
        }
        clear()
    }

    private fun getVol(vol: Int): Float {
        return FACTOR * (Math.log10(vol.toDouble()).toFloat() - 2)
    }

    @Throws(Exception::class)
    private fun loadSound(ind: Int, bytes: ByteArray, vol: Float, b: Boolean, loop: Long) {
        // set ind to -1 to tell it's BG
        if (b) {
            val c: Clip = openFile(bytes)
            c.loop(Clip.LOOP_CONTINUOUSLY)
            if (BG != null) {
                BG.stop()
            }
            BG = BCPlayer(c, -1, loop, true)
            BG.setVolume(VOL_BG)
            BG.start()
            return
        }
        var clips: ArrayDeque<BCPlayer>? = sounds[ind]
        if (clips == null) {
            clips = ArrayDeque<BCPlayer>()
            sounds[ind] = clips
            loadSound(ind, openFile(bytes), false)
        } else {
            val player: BCPlayer? = clips.poll()
            if (player != null) {
                player.rewind()
                player.start()
            } else {
                when (ind) {
                    20 -> if (hit != null) {
                        if (h) {
                            hit!![0]!!.stop()
                            hit!![0]!!.rewind()
                            hit!![1]!!.start()
                        } else {
                            hit!![1]!!.stop()
                            hit!![1]!!.rewind()
                            hit!![0]!!.start()
                        }
                        h = !h
                    }
                    21 -> if (hit1 != null) {
                        if (h1) {
                            hit1!![0]!!.stop()
                            hit1!![0]!!.rewind()
                            hit1!![1]!!.start()
                        } else {
                            hit1!![1]!!.stop()
                            hit1!![1]!!.rewind()
                            hit1!![0]!!.start()
                        }
                        h1 = !h1
                    }
                    22 -> if (baseHit != null) {
                        if (bh) {
                            baseHit!![0]!!.stop()
                            baseHit!![0]!!.rewind()
                            baseHit!![1]!!.start()
                        } else {
                            baseHit!![1]!!.stop()
                            baseHit!![1]!!.rewind()
                            baseHit!![0]!!.start()
                        }
                        bh = !bh
                    }
                    else -> loadSound(ind, openFile(bytes), false)
                }
            }
        }
    }

    private fun loadSound(ind: Int, c: Clip, loop: Boolean) {
        val player = BCPlayer(c, ind, loop)
        player.setVolume(VOL_SE)
        player.start()
    }

    @Throws(Exception::class)
    private fun loadSound(ind: Int, file: Music, vol: Float, b: Boolean, loop: Long) {
        // set ind to -1 to tell it's BG
        if (b) {
            val c: Clip = openFile(file)
            c.loop(Clip.LOOP_CONTINUOUSLY)
            if (BG != null) {
                BG.stop()
                BG.release()
            }
            BG = BCPlayer(c, -1, loop, true)
            BG.setVolume(VOL_BG)
            BG.start()
            return
        }
        var clips: ArrayDeque<BCPlayer>? = sounds[ind]
        if (clips == null) {
            clips = ArrayDeque<BCPlayer>()
            sounds[ind] = clips
            loadSound(ind, openFile(file), false)
        } else {
            val player: BCPlayer? = clips.poll()
            if (player != null) {
                player.rewind()
                player.start()
            } else {
                loadSound(ind, openFile(file), false)
            }
        }
    }

    @Throws(Exception::class)
    private fun openFile(data: ByteArray): Clip {
        val `is` = ByteArrayInputStream(data)
        val raw: AudioInputStream = AudioSystem.getAudioInputStream(`is`)
        val rf: AudioFormat = raw.format
        val ch = rf.channels
        val rate = rf.sampleRate
        val format = AudioFormat(AudioFormat.Encoding.PCM_SIGNED, rate, 16, ch, ch * 2, rate, false)
        val stream: AudioInputStream = AudioSystem.getAudioInputStream(format, raw)
        val info: DataLine.Info = DataLine.Info(Clip::class.java, format)
        val line: Clip = AudioSystem.getLine(info) as Clip
        line.open(stream)
        raw.close()
        stream.close()
        return line
    }

    @Throws(Exception::class)
    private fun openFile(file: Music): Clip {
        val raw: AudioInputStream = AudioSystem.getAudioInputStream(file.data.getStream())
        val rf: AudioFormat = raw.format
        val ch = rf.channels
        val rate = rf.sampleRate
        val format = AudioFormat(AudioFormat.Encoding.PCM_SIGNED, rate, 16, ch, ch * 2, rate, false)
        val stream: AudioInputStream = AudioSystem.getAudioInputStream(format, raw)
        val info: DataLine.Info = DataLine.Info(Clip::class.java, format)
        val line: Clip = AudioSystem.getLine(info) as Clip
        line.open(stream)
        raw.close()
        stream.close()
        return line
    }
}
