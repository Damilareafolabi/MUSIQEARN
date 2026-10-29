package com.example.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Build
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.sin

/**
 * MusicAudioEngine: Autonomous procedural music and sound-effects synthesizer for MUSIZEARN.
 * Uses Android native AudioTrack to deliver authentic melodic riffs, chord progressions,
 * rhythm grooves, and game sound effects without requiring unlicensed MP3 files.
 */
class MusicAudioEngine(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {
    private val sampleRate = 44100
    private var currentTrack: AudioTrack? = null
    private var playbackJob: Job? = null

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentTrackTitle = MutableStateFlow<String?>(null)
    val currentTrackTitle: StateFlow<String?> = _currentTrackTitle.asStateFlow()

    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress.asStateFlow()

    var isMuted: Boolean = false

    /**
     * Play a musical snippet corresponding to a track or challenge
     */
    fun playMusicSnippet(riffId: String, title: String, durationSec: Int = 8, onComplete: (() -> Unit)? = null) {
        if (isMuted) return
        stop()

        playbackJob = scope.launch {
            _isPlaying.value = true
            _currentTrackTitle.value = title
            _progress.value = 0f

            val pcmData = generateRiffPcm(riffId, durationSec)
            if (pcmData.isEmpty()) {
                _isPlaying.value = false
                return@launch
            }

            try {
                val bufferSize = AudioTrack.getMinBufferSize(
                    sampleRate,
                    AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT
                ).coerceAtLeast(pcmData.size * 2)

                val audioTrack = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    AudioTrack.Builder()
                        .setAudioAttributes(
                            AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_GAME)
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .build()
                        )
                        .setAudioFormat(
                            AudioFormat.Builder()
                                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                                .setSampleRate(sampleRate)
                                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                                .build()
                        )
                        .setBufferSizeInBytes(bufferSize)
                        .setTransferMode(AudioTrack.MODE_STREAM)
                        .build()
                } else {
                    @Suppress("DEPRECATION")
                    AudioTrack(
                        android.media.AudioManager.STREAM_MUSIC,
                        sampleRate,
                        AudioFormat.CHANNEL_OUT_MONO,
                        AudioFormat.ENCODING_PCM_16BIT,
                        bufferSize,
                        AudioTrack.MODE_STREAM
                    )
                }

                currentTrack = audioTrack
                audioTrack.play()

                val chunkSize = 4096
                var offset = 0
                val totalSamples = pcmData.size

                while (isActive && offset < totalSamples) {
                    val count = (totalSamples - offset).coerceAtMost(chunkSize)
                    audioTrack.write(pcmData, offset, count)
                    offset += count
                    _progress.value = (offset.toFloat() / totalSamples).coerceIn(0f, 1f)
                    delay(15)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                stopTrackSafely()
                _isPlaying.value = false
                _progress.value = 1f
                onComplete?.invoke()
            }
        }
    }

    /**
     * Stop active playback
     */
    fun stop() {
        playbackJob?.cancel()
        playbackJob = null
        stopTrackSafely()
        _isPlaying.value = false
        _progress.value = 0f
    }

    private fun stopTrackSafely() {
        try {
            currentTrack?.let {
                if (it.playState == AudioTrack.PLAYSTATE_PLAYING) {
                    it.stop()
                }
                it.release()
            }
        } catch (_: Exception) {
        } finally {
            currentTrack = null
        }
    }

    // --- Sound Effects ---

    fun playCorrectSound() {
        if (isMuted) return
        scope.launch {
            val notes = listOf(523.25, 659.25, 783.99, 1046.50) // C5, E5, G5, C6
            val samples = ShortArray((sampleRate * 0.45).toInt())
            val noteDuration = samples.size / notes.size
            for (i in samples.indices) {
                val noteIdx = (i / noteDuration).coerceAtMost(notes.size - 1)
                val freq = notes[noteIdx]
                val t = i.toDouble() / sampleRate
                val envelope = 1.0 - (i % noteDuration).toDouble() / noteDuration
                val sample = (sin(2.0 * PI * freq * t) * 0.6 * envelope * Short.MAX_VALUE).toInt()
                samples[i] = sample.coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
            }
            playShortBuffer(samples)
        }
    }

    fun playWrongSound() {
        if (isMuted) return
        scope.launch {
            val notes = listOf(220.0, 196.0, 164.81) // A3, G3, E3
            val samples = ShortArray((sampleRate * 0.35).toInt())
            val noteDuration = samples.size / notes.size
            for (i in samples.indices) {
                val noteIdx = (i / noteDuration).coerceAtMost(notes.size - 1)
                val freq = notes[noteIdx]
                val t = i.toDouble() / sampleRate
                val envelope = 1.0 - (i % noteDuration).toDouble() / noteDuration
                // Add square-ish harmonic for buzzer feel
                val sine = sin(2.0 * PI * freq * t)
                val harmonic = sin(2.0 * PI * freq * 2 * t) * 0.3
                val sample = ((sine + harmonic) * 0.5 * envelope * Short.MAX_VALUE).toInt()
                samples[i] = sample.coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
            }
            playShortBuffer(samples)
        }
    }

    fun playTickSound() {
        if (isMuted) return
        scope.launch {
            val samples = ShortArray((sampleRate * 0.05).toInt())
            for (i in samples.indices) {
                val t = i.toDouble() / sampleRate
                val envelope = Math.exp(-t * 90.0)
                val sample = (sin(2.0 * PI * 1200.0 * t) * envelope * 0.3 * Short.MAX_VALUE).toInt()
                samples[i] = sample.coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
            }
            playShortBuffer(samples)
        }
    }

    fun playCoinSound() {
        if (isMuted) return
        scope.launch {
            val samples = ShortArray((sampleRate * 0.35).toInt())
            for (i in samples.indices) {
                val t = i.toDouble() / sampleRate
                val freq = if (t < 0.12) 987.77 else 1318.51 // B5 then E6
                val envelope = 1.0 - (t / 0.35)
                val sample = (sin(2.0 * PI * freq * t) * envelope * 0.5 * Short.MAX_VALUE).toInt()
                samples[i] = sample.coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
            }
            playShortBuffer(samples)
        }
    }

    private fun playShortBuffer(pcm: ShortArray) {
        try {
            val track = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(pcm.size * 2)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()
            track.write(pcm, 0, pcm.size)
            track.play()
        } catch (_: Exception) {
        }
    }

    // --- Dynamic Music Synthesis By Genre & Riff ---

    private fun generateRiffPcm(riffId: String, durationSec: Int): ShortArray {
        val totalSamples = sampleRate * durationSec
        val pcm = ShortArray(totalSamples)

        val (melody, bpm, genreStyle) = when (riffId) {
            "afrobeats_city_boys", "afrobeats_last_last" -> Triple(
                listOf(392.0, 440.0, 523.25, 440.0, 392.0, 329.63, 349.23, 392.0),
                108,
                "afrobeats"
            )
            "amapiano_tshwala_bam", "amapiano_ke_star" -> Triple(
                listOf(261.63, 293.66, 329.63, 392.0, 440.0, 392.0, 329.63, 293.66),
                112,
                "amapiano"
            )
            "pop_blinding_lights" -> Triple(
                listOf(349.23, 392.0, 440.0, 523.25, 440.0, 392.0, 349.23, 293.66),
                171,
                "synthwave"
            )
            "hiphop_not_like_us", "hiphop_humble" -> Triple(
                listOf(220.0, 220.0, 246.94, 261.63, 220.0, 196.0, 220.0, 164.81),
                100,
                "hiphop"
            )
            "reggae_three_birds", "reggae_toast" -> Triple(
                listOf(329.63, 392.0, 440.0, 493.88, 440.0, 392.0, 329.63, 261.63),
                76,
                "reggae"
            )
            "rnb_cuff_it", "rnb_under_the_influence" -> Triple(
                listOf(440.0, 493.88, 523.25, 659.25, 587.33, 523.25, 493.88, 440.0),
                115,
                "rnb"
            )
            "highlife_ku_lo_sa", "highlife_baby" -> Triple(
                listOf(523.25, 587.33, 659.25, 783.99, 659.25, 587.33, 523.25, 440.0),
                104,
                "highlife"
            )
            else -> Triple(
                listOf(440.0, 493.88, 554.37, 659.25, 554.37, 493.88, 440.0, 329.63),
                120,
                "pop"
            )
        }

        val beatDurationSamples = (sampleRate * (60.0 / bpm)).toInt()

        for (i in 0 until totalSamples) {
            val t = i.toDouble() / sampleRate
            val beatIndex = (i / beatDurationSamples) % melody.size
            val sampleInBeat = i % beatDurationSamples
            val beatProgress = sampleInBeat.toDouble() / beatDurationSamples

            val freq = melody[beatIndex]

            // Melodic note envelope
            val noteEnvelope = (1.0 - beatProgress * 0.7).coerceIn(0.0, 1.0)
            val lead = sin(2.0 * PI * freq * t) * noteEnvelope * 0.35

            // Sub-bass / groove element depending on genre
            val bassFreq = freq / 2.0
            val bass = when (genreStyle) {
                "amapiano" -> {
                    // Log-drum characteristic percussive punch
                    val drumEnv = Math.exp(-beatProgress * 8.0)
                    sin(2.0 * PI * 65.0 * t) * drumEnv * 0.5
                }
                "afrobeats" -> {
                    // Syncopated warm bass
                    val bassEnv = if (beatProgress < 0.8) 0.35 else 0.05
                    sin(2.0 * PI * bassFreq * t) * bassEnv
                }
                "hiphop" -> {
                    // Deep 808 glide
                    val subEnv = Math.exp(-beatProgress * 3.0)
                    sin(2.0 * PI * 55.0 * t) * subEnv * 0.55
                }
                "synthwave" -> {
                    // Fast pulsing bass
                    val pulse = if ((sampleInBeat / (beatDurationSamples / 4)) % 2 == 0) 0.3 else 0.1
                    sin(2.0 * PI * bassFreq * t) * pulse
                }
                else -> {
                    sin(2.0 * PI * bassFreq * t) * 0.2
                }
            }

            // Chords / harmony sparkle
            val harmony = sin(2.0 * PI * (freq * 1.5) * t) * 0.15 * noteEnvelope

            val combined = (lead + bass + harmony) * 0.8
            val intSample = (combined * Short.MAX_VALUE).toInt().coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt())
            pcm[i] = intSample.toShort()
        }

        return pcm
    }
}
