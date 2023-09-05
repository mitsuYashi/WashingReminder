package com.example.washingreminder.ui.home
import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.example.washingreminder.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

@SuppressLint("ViewConstructor")
class CustomMarkerView(context: Context, layoutResource: Int) : MarkerView(context, layoutResource) {

    private val textView: TextView = findViewById(R.id.markerTextView) // レイアウト内のTextView

    @SuppressLint("SetTextI18n")
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        if (e == null) return

        // Entryから表示するデータを取得（ここでは降水確率のデータを表示）
        val precipChance = e.y.toInt()

        // TextViewにデータをセット
        textView.text = "${precipChance}%"
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
    }
}
