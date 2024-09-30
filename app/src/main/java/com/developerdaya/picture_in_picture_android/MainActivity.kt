package com.developerdaya.picture_in_picture_android

import android.annotation.SuppressLint
import android.app.PictureInPictureParams
import android.os.Build
import android.os.Bundle
import android.util.Rational
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class MainActivity : AppCompatActivity() {
    private lateinit var youTubePlayerView: YouTubePlayerView
    private lateinit var youTubePlayer1: YouTubePlayer

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rootView = findViewById<TextView>(R.id.mButton) as TextView
        youTubePlayerView = findViewById(R.id.youtube_player_view)
        lifecycle.addObserver(youTubePlayerView)
        rootView.setOnClickListener {
            enterPiPMode()
        }
        initializeYouTubePlayer("KLuTLF3x9sA")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun enterPiPMode() {
        val aspectRatio = Rational(16, 9)
        val pipParams = PictureInPictureParams.Builder()
            .setAspectRatio(aspectRatio)
            .build()
        enterPictureInPictureMode(pipParams)
    }

    fun initializeYouTubePlayer(url: String) {
        val tracker = YouTubePlayerTracker()
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(player: YouTubePlayer) {
                youTubePlayer1 = player
                youTubePlayer1.addListener(tracker)
                youTubePlayer1.cueVideo(url, 0f)
                player.play()

            }
        })
    }


    private fun releaseYouTubePlayer() {
        try {
            if (::youTubePlayer1.isInitialized) {
                youTubePlayer1.pause()
                youTubePlayerView.release()
            }
        } catch (e: Exception) {
        }

    }

    override fun onPause() {
        super.onPause()
        releaseYouTubePlayer()


    }


    override fun onStop() {
        super.onStop()
        if (isInPictureInPictureMode) {
        }
    }

}
