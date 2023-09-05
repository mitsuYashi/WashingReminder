package com.example.washingreminder.ui.home

import ForecastDay
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.graphics.RadialGradient
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.icu.util.Calendar
import android.os.Handler
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.Random

class WeatherSurfaceView(context: Context, attributeSet: AttributeSet) : SurfaceView(context, attributeSet), SurfaceHolder.Callback {
    private val surfaceHolder: SurfaceHolder = holder
    private var forecastDay: ForecastDay? = null
    private val handler = Handler()
    private val raindrops = mutableListOf<Raindrop>()

    init {
        surfaceHolder.addCallback(this)
    }

    override fun surfaceChanged(p0: SurfaceHolder, format: Int, width: Int, height: Int) {
        // 画面のサイズが変更されたときの処理
        drawWeather()
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        // SurfaceView が作成されたときの処理
        drawWeather()
        startAnimation()
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        // SurfaceView が破棄されたときの処理
        stopAnimation()
    }

    fun setForecastday(weatherData: ForecastDay) {
        this.forecastDay = weatherData
        drawWeather()
    }

    private fun drawWeather(forecastDay: ForecastDay? = this.forecastDay) {
        if (surfaceHolder.surface.isValid) {
            surfaceHolder.setFormat(PixelFormat.TRANSPARENT)

            val canvas = surfaceHolder.lockCanvas()
            val paint = Paint()

            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)

            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val textPaint = Paint()
            textPaint.color = Color.WHITE
            textPaint.textAlign = Paint.Align.CENTER
            textPaint.textSize = 30F

            if (forecastDay != null) {
                when (forecastDay.hour[hour].condition.text) {
                    "近くで所により曇り", "所により曇り", "曇り" -> {
                        val colors = intArrayOf(Color.parseColor("#A9A9A9"), Color.parseColor("#808080"))
                        paint.shader = LinearGradient(0F, 0F, 0F, canvas.height.toFloat(), colors, null, Shader.TileMode.CLAMP)
                        drawCloudAnimation(canvas)
                    }
                    "近くで所により雨", "所により雨", "軽いにわか雨", "穏やかな雨", "雨" -> {
                        val colors = intArrayOf(Color.parseColor("#4682B4"), Color.parseColor("#1E90FF"))
                        paint.shader = LinearGradient(0F, 0F, 0F, canvas.height.toFloat(), colors, null, Shader.TileMode.CLAMP)
                        drawRainAnimation(canvas)
                    }
                    "近くで所により雪", "所により雪", "雪" -> {
                        val colors = intArrayOf(Color.parseColor("#87CEEB"), Color.parseColor("#00BFFF"))
                        paint.shader = LinearGradient(0F, 0F, 0F, canvas.height.toFloat(), colors, null, Shader.TileMode.CLAMP)
                    }
                    "晴れ", "快晴",  ->  {
                        paint.shader = RadialGradient(
                            canvas.width / 2F,
                            canvas.height / 2F,
                            canvas.width / 4F,
                            Color.YELLOW,
                            Color.TRANSPARENT,
                            Shader.TileMode.CLAMP

                        )
                        textPaint.color = Color.BLACK
                        drawSunAnimation(canvas)
                    }
                    else -> paint.color = Color.TRANSPARENT
                }
            } else {
                paint.color = Color.TRANSPARENT
            }

            val centerX = canvas.width / 2F
            val centerY = canvas.height / 2F
            val radius = canvas.height / 2F

            canvas.drawCircle( centerX, centerY, radius, paint)

            val text = "${forecastDay?.hour?.get(hour)?.chance_of_rain ?: 0}%"
            val textBounds = Rect()
            textPaint.getTextBounds(text, 0, text.length, textBounds)

            canvas.drawText("降水確率: $text", centerX, centerY, textPaint)

            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }


    private fun drawRainAnimation(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.BLUE
        paint.strokeWidth = 2f

        for (raindrop in raindrops) {
            canvas.drawLine(raindrop.x, raindrop.y, raindrop.x, raindrop.y + raindrop.length, paint)
            raindrop.y += raindrop.speed
            if (raindrop.y > canvas.height) {
                raindrop.y = 0f
                raindrop.x = Random().nextFloat() * canvas.width
            }
        }
    }

    private fun drawSunAnimation(canvas: Canvas) {
        val centerX = canvas.width / 2f
        val centerY = canvas.height / 2f
        val radius = canvas.width / 4f
        val paint = Paint()
        paint.color = Color.YELLOW
        canvas.drawCircle(centerX, centerY, radius, paint)
    }

    private fun drawCloudAnimation(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.GRAY
        paint.style = Paint.Style.FILL
        val cloudRect = RectF(100f, 100f, 300f, 200f)
        canvas.drawOval(cloudRect, paint)
        cloudRect.offset(100f, 0f)
        canvas.drawOval(cloudRect, paint)
        cloudRect.offset(-200f, 0f)
        canvas.drawOval(cloudRect, paint)
        cloudRect.offset(100f, -50f)
        canvas.drawOval(cloudRect, paint)
        cloudRect.offset(0f, 50f)
        canvas.drawOval(cloudRect, paint)
        cloudRect.offset(700f, 150f)
        canvas.drawOval(cloudRect, paint)
    }

    private val animationRunnable = object : Runnable {
        override fun run() {
            drawWeather()

            handler.postDelayed(this, 100)
        }
    }

    private fun startAnimation() {
        handler.post(animationRunnable)
        for (i in 0 until 100) {
            val x = Random().nextFloat() * width
            val y = Random().nextFloat() * height
            val speed = Random().nextInt(10) + 5
            val length = Random().nextInt(30) + 20
            raindrops.add(Raindrop(x, y, speed, length))
        }
    }

    private fun stopAnimation() {
        handler.removeCallbacks(animationRunnable)
    }

    data class Raindrop(var x: Float, var y: Float, val speed: Int, val length: Int)
}
