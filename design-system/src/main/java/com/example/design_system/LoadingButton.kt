package com.example.design_system

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView

class LoadingButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val progressBar: ProgressBar
    private val buttonTextView: TextView

    init {
        val root = LayoutInflater.from(context).inflate(R.layout.layout_loading_button, this, true)
        buttonTextView = root.findViewById(R.id.loadingBtnText)
        progressBar = root.findViewById(R.id.loadingBtnProgressBar)
        loadAttr(attrs, defStyleAttr)
    }

    private fun loadAttr(attrs: AttributeSet?, defStyleAttr: Int) {
        val arr = context.obtainStyledAttributes(
            attrs,
            R.styleable.LoadingButton,
            defStyleAttr,
            0
        )

        val buttonText = arr.getString(R.styleable.LoadingButton_buttonText)
        val loading = arr.getBoolean(R.styleable.LoadingButton_isLoading, false)
        val enabled = arr.getBoolean(R.styleable.LoadingButton_isEnabled, true)
        arr.recycle()
        isEnabled = enabled
        buttonTextView.isEnabled = enabled
        setText(buttonText)
        setLoading(loading)
    }

    fun setLoading(loading: Boolean){
        isClickable = !loading
        if(loading){
            buttonTextView.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        } else {
            buttonTextView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }

    fun setText(text : String?) {
        buttonTextView.text = text
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        buttonTextView.isEnabled = enabled
    }
}