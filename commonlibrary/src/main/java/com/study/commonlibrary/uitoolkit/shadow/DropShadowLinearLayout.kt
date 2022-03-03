package com.study.commonlibrary.uitoolkit.shadow

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.study.commonlibrary.R


/**
 * 如果冲突了，全都使用Halohoop的版本即可
 * 需要建立其他的ViewGroup，请按照以下步骤C&V一个类文件出来即可
 * [@1@.新建文件DropShadowXxxxLayout，直接c&v这个文件即可;]
 * [@2@.修改ViewGroup名字]
 */
class DropShadowLinearLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0, defStyleRes: Int = 0) : /*TODO [@2@.修改ViewGroup名字]*/LinearLayout/*TODO [@2@.修改ViewGroup名字]*/(context, attrs, defStyleAttr, defStyleRes) {
    private val SHADOW_SOLID = 0
    private val SHADOW_BLUR = 1

    inner class DropShadowLayoutParam(c: Context, attributeSet: AttributeSet?) : /*TODO [@2@.修改ViewGroup名字]*/LinearLayout/*TODO [@2@.修改ViewGroup名字]*/.LayoutParams(c, attributeSet) {
        var shadowMode: Int = SHADOW_SOLID
        var shadowColor: Int = Color.parseColor("#338e8e8e")

        var shadowBlur: Int = 0//px only work on SHADOW_BLUR
        lateinit var blurMaskFilter: BlurMaskFilter
        var shadowWidth: Int = 0//px
        var shadowStartWidth: Int = 0//px
        var shadowTopWidth: Int = 0//px
        var shadowEndWidth: Int = 0//px
        var shadowBottomWidth: Int = 0//px

        var shadowCornorRaidus: Int = 0//px
        var shadowStartBottomCornorRaidus: Int = 0//px
        var shadowEndBottomCornorRaidus: Int = 0//px
        var shadowStartTopCornorRaidus: Int = 0//px
        var shadowEndTopCornorRaidus: Int = 0//px

        init {
            attributeSet?.let { attrs ->
                try {
                    val ta = c.obtainStyledAttributes(attrs, R.styleable.DropShadowViewGroup)
                    shadowMode = ta.getColor(R.styleable.DropShadowViewGroup_dsvg_shadow_mode, SHADOW_SOLID)
                    shadowMode.let { mode ->
                        if (mode == SHADOW_SOLID) {
                            shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                                style = Paint.Style.FILL
                            }
                        } else {
                            shadowBlur = ta.getDimensionPixelSize(R.styleable.DropShadowViewGroup_dsvg_shadow_blur, shadowBlur) - 1
                            shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                                style = Paint.Style.FILL
                            }
                            shadowPaint.strokeWidth = 1f
                            blurMaskFilter = BlurMaskFilter(shadowBlur.toFloat(), BlurMaskFilter.Blur.NORMAL)
                        }
                    }
                    shadowColor = ta.getColor(R.styleable.DropShadowViewGroup_dsvg_shadow_color, shadowColor)
                    shadowWidth = ta.getDimensionPixelSize(R.styleable.DropShadowViewGroup_dsvg_shadow_width, shadowWidth)
                    shadowStartWidth = ta.getDimensionPixelSize(R.styleable.DropShadowViewGroup_dsvg_shadow_start_width, shadowStartWidth)
                    shadowTopWidth = ta.getDimensionPixelSize(R.styleable.DropShadowViewGroup_dsvg_shadow_top_width, shadowTopWidth)
                    shadowEndWidth = ta.getDimensionPixelSize(R.styleable.DropShadowViewGroup_dsvg_shadow_end_width, shadowEndWidth)
                    shadowBottomWidth = ta.getDimensionPixelSize(R.styleable.DropShadowViewGroup_dsvg_shadow_bottom_width, shadowBottomWidth)
                    shadowCornorRaidus = ta.getDimensionPixelSize(R.styleable.DropShadowViewGroup_dsvg_shadow_cornor_radius, shadowCornorRaidus)
                    shadowStartBottomCornorRaidus = ta.getDimensionPixelSize(R.styleable.DropShadowViewGroup_dsvg_shadow_start_bottom_cornor_radius, shadowStartBottomCornorRaidus)
                    shadowEndBottomCornorRaidus = ta.getDimensionPixelSize(R.styleable.DropShadowViewGroup_dsvg_shadow_end_bottom_cornor_radius, shadowEndBottomCornorRaidus)
                    shadowStartTopCornorRaidus = ta.getDimensionPixelSize(R.styleable.DropShadowViewGroup_dsvg_shadow_start_top_cornor_radius, shadowStartTopCornorRaidus)
                    shadowEndTopCornorRaidus = ta.getDimensionPixelSize(R.styleable.DropShadowViewGroup_dsvg_shadow_end_top_cornor_radius, shadowEndTopCornorRaidus)
                    ta.recycle()
                } catch (e: Exception) {
                }
            }
            setWillNotDraw(false)
            setLayerType(LAYER_TYPE_SOFTWARE, null)
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): /*TODO [@2@.修改ViewGroup名字]*/LinearLayout/*TODO [@2@.修改ViewGroup名字]*/.LayoutParams {
        return DropShadowLayoutParam(context, attrs)
    }

    private lateinit var shadowPaint: Paint

    private val shadowPaintXfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)

    private val canvasHelperPath = Path()
    private val canvasHelperRadiusRectF = RectF()

    /*
    //测试画点代码start
    private val testPointPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    style = Paint.Style.FILL
    color = Color.BLACK
    }

    private fun drawTestPoint(x :Float, y :Float, canvas: Canvas?) {
    canvas?.drawCircle(x, y, 5f, testPointPaint)
    }

    override fun onDrawForeground(canvas: Canvas?) {
    super.onDrawForeground(canvas)
    repeat(childCount) { index ->
    val child = getChildAt(index)
    val lp = child.layoutParams
    if (lp is DropShadowLayoutParam) {
    val startBottomCornorRaidus = lp.shadowStartBottomCornorRaidus.takeIf { it != 0 } ?: lp.shadowCornorRaidus
    val endBottomCornorRaidus = lp.shadowEndBottomCornorRaidus.takeIf { it != 0 } ?: lp.shadowCornorRaidus
    val startTopCornorRaidus = lp.shadowStartTopCornorRaidus.takeIf { it != 0 } ?: lp.shadowCornorRaidus
    val endTopCornorRaidus = lp.shadowEndTopCornorRaidus.takeIf { it != 0 } ?: lp.shadowCornorRaidus
    val startWidth = lp.shadowStartWidth.takeIf { it != 0 } ?: lp.shadowWidth
    val topWidth = lp.shadowTopWidth.takeIf { it != 0 } ?: lp.shadowWidth
    val endWidth = lp.shadowEndWidth.takeIf { it != 0 } ?: lp.shadowWidth
    val bottomWidth = lp.shadowBottomWidth.takeIf { it != 0 } ?: lp.shadowWidth


    drawTestPoint(child.x + child.width + endWidth - endTopCornorRaidus.toFloat() * 2, child.y + topWidth, canvas)
    }
    }
    }
    //测试画点代码end
    */

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawShadow { canvas }
    }

    private inline fun drawOnModeBlur(layoutParam: () -> DropShadowLayoutParam, childView: () -> View, canvas: () -> Canvas?) {
        val lp = layoutParam()
        val child = childView()
        val c = canvas()
        shadowPaint.color = lp.shadowColor
        val startBottomCornorRaidus = lp.shadowStartBottomCornorRaidus.takeIf { it != 0 }
            ?: lp.shadowCornorRaidus
        val endBottomCornorRaidus = lp.shadowEndBottomCornorRaidus.takeIf { it != 0 }
            ?: lp.shadowCornorRaidus
        val startTopCornorRaidus = lp.shadowStartTopCornorRaidus.takeIf { it != 0 }
            ?: lp.shadowCornorRaidus
        val endTopCornorRaidus = lp.shadowEndTopCornorRaidus.takeIf { it != 0 }
            ?: lp.shadowCornorRaidus
        val startWidth = lp.shadowStartWidth.takeIf { it != 0 } ?: lp.shadowWidth
        val topWidth = lp.shadowTopWidth.takeIf { it != 0 } ?: lp.shadowWidth
        val endWidth = lp.shadowEndWidth.takeIf { it != 0 } ?: lp.shadowWidth
        val bottomWidth = lp.shadowBottomWidth.takeIf { it != 0 } ?: lp.shadowWidth
        canvasHelperPath.reset()
        // 左上圆角
        canvasHelperRadiusRectF.set(0f, 0f, startTopCornorRaidus.toFloat() * 2, startTopCornorRaidus.toFloat() * 2)
        canvasHelperRadiusRectF.offsetTo(child.x - startWidth + lp.shadowBlur, child.y - topWidth + lp.shadowBlur)
        canvasHelperPath.arcTo(canvasHelperRadiusRectF, -90f, -90f)
        // 左直线
        canvasHelperPath.lineTo(child.x - startWidth + lp.shadowBlur, child.y + child.height + bottomWidth - startBottomCornorRaidus.toFloat())
        // 左下圆角
        canvasHelperRadiusRectF.set(0f, 0f, startBottomCornorRaidus.toFloat() * 2, startBottomCornorRaidus.toFloat() * 2)
        canvasHelperRadiusRectF.offsetTo(child.x - startWidth + lp.shadowBlur, child.y + child.height + bottomWidth - startBottomCornorRaidus.toFloat() * 2 - lp.shadowBlur)
        canvasHelperPath.arcTo(canvasHelperRadiusRectF, 180f, -90f)
        // 下直线
        canvasHelperPath.lineTo(child.x + child.width + endWidth - endBottomCornorRaidus, child.y + child.height + bottomWidth - lp.shadowBlur)
        // 右下圆角
        canvasHelperRadiusRectF.set(0f, 0f, endBottomCornorRaidus.toFloat() * 2, endBottomCornorRaidus.toFloat() * 2)
        canvasHelperRadiusRectF.offsetTo(child.x + child.width + endWidth - endBottomCornorRaidus.toFloat() * 2 - lp.shadowBlur, child.y + child.height + bottomWidth - endBottomCornorRaidus.toFloat() * 2 - lp.shadowBlur)
        canvasHelperPath.arcTo(canvasHelperRadiusRectF, 90f, -90f)
        // 右直线
        canvasHelperPath.lineTo(child.x + child.width + endWidth - lp.shadowBlur, child.y - topWidth + endTopCornorRaidus.toFloat())
        // 右上圆角
        canvasHelperRadiusRectF.set(0f, 0f, endTopCornorRaidus.toFloat() * 2, endTopCornorRaidus.toFloat() * 2)
        canvasHelperRadiusRectF.offsetTo(child.x + child.width + endWidth - endTopCornorRaidus.toFloat() * 2 - lp.shadowBlur, child.y - topWidth + lp.shadowBlur)
        canvasHelperPath.arcTo(canvasHelperRadiusRectF, 0f, -90f)
        // 上直线
        canvasHelperPath.close()
        shadowPaint.xfermode = null
        shadowPaint.maskFilter = lp.blurMaskFilter
        c?.drawPath(canvasHelperPath, shadowPaint)

        //去掉过度绘制部分
        c?.let { _c ->
            canvasHelperPath.reset()
            // 左上圆角
            canvasHelperRadiusRectF.set(0f, 0f, startTopCornorRaidus.toFloat() * 2, startTopCornorRaidus.toFloat() * 2)
            canvasHelperRadiusRectF.offsetTo(child.x, child.y)
            canvasHelperPath.arcTo(canvasHelperRadiusRectF, -90f, -90f)
            // 左直线
            canvasHelperPath.lineTo(child.x, child.y + child.height - startBottomCornorRaidus.toFloat())
            // 左下圆角
            canvasHelperRadiusRectF.set(0f, 0f, startBottomCornorRaidus.toFloat() * 2, startBottomCornorRaidus.toFloat() * 2)
            canvasHelperRadiusRectF.offsetTo(child.x, child.y + child.height - startBottomCornorRaidus.toFloat() * 2)
            canvasHelperPath.arcTo(canvasHelperRadiusRectF, 180f, -90f)
            // 下直线
            canvasHelperPath.lineTo(child.x + child.width - endBottomCornorRaidus, child.y + child.height)
            // 右下圆角
            canvasHelperRadiusRectF.set(0f, 0f, endBottomCornorRaidus.toFloat() * 2, endBottomCornorRaidus.toFloat() * 2)
            canvasHelperRadiusRectF.offsetTo(child.x + child.width - endBottomCornorRaidus.toFloat() * 2, child.y + child.height - endBottomCornorRaidus.toFloat() * 2)
            canvasHelperPath.arcTo(canvasHelperRadiusRectF, 90f, -90f)
            // 右直线
            canvasHelperPath.lineTo(child.x + child.width, child.y + endTopCornorRaidus.toFloat())
            // 右上圆角
            canvasHelperRadiusRectF.set(0f, 0f, endTopCornorRaidus.toFloat() * 2, endTopCornorRaidus.toFloat() * 2)
            canvasHelperRadiusRectF.offsetTo(child.x + child.width - endTopCornorRaidus.toFloat() * 2, child.y)
            canvasHelperPath.arcTo(canvasHelperRadiusRectF, 0f, -90f)
            // 上直线
            canvasHelperPath.close()
            shadowPaint.xfermode = shadowPaintXfermode
            shadowPaint.maskFilter = null
            _c.drawPath(canvasHelperPath, shadowPaint)
        }
    }

    private fun drawOnModeSolid(layoutParam: () -> DropShadowLayoutParam, childView: () -> View, canvas: () -> Canvas?) {
        val lp = layoutParam()
        val child = childView()
        val c = canvas()
        shadowPaint.color = lp.shadowColor
        val startBottomCornorRaidus = lp.shadowStartBottomCornorRaidus.takeIf { it != 0 }
            ?: lp.shadowCornorRaidus
        val endBottomCornorRaidus = lp.shadowEndBottomCornorRaidus.takeIf { it != 0 }
            ?: lp.shadowCornorRaidus
        val startTopCornorRaidus = lp.shadowStartTopCornorRaidus.takeIf { it != 0 }
            ?: lp.shadowCornorRaidus
        val endTopCornorRaidus = lp.shadowEndTopCornorRaidus.takeIf { it != 0 }
            ?: lp.shadowCornorRaidus
        val startWidth = lp.shadowStartWidth.takeIf { it != 0 } ?: lp.shadowWidth
        val topWidth = lp.shadowTopWidth.takeIf { it != 0 } ?: lp.shadowWidth
        val endWidth = lp.shadowEndWidth.takeIf { it != 0 } ?: lp.shadowWidth
        val bottomWidth = lp.shadowBottomWidth.takeIf { it != 0 } ?: lp.shadowWidth
        canvasHelperPath.reset()
        // 左上圆角
        canvasHelperRadiusRectF.set(0f, 0f, startTopCornorRaidus.toFloat() * 2, startTopCornorRaidus.toFloat() * 2)
        canvasHelperRadiusRectF.offsetTo(child.x - startWidth, child.y - topWidth)
        canvasHelperPath.arcTo(canvasHelperRadiusRectF, -90f, -90f)
        // 左直线
        canvasHelperPath.lineTo(child.x - startWidth, child.y + child.height + bottomWidth - startBottomCornorRaidus.toFloat())
        // 左下圆角
        canvasHelperRadiusRectF.set(0f, 0f, startBottomCornorRaidus.toFloat() * 2, startBottomCornorRaidus.toFloat() * 2)
        canvasHelperRadiusRectF.offsetTo(child.x - startWidth, child.y + child.height + bottomWidth - startBottomCornorRaidus.toFloat() * 2)
        canvasHelperPath.arcTo(canvasHelperRadiusRectF, 180f, -90f)
        // 下直线
        canvasHelperPath.lineTo(child.x + child.width + endWidth - endBottomCornorRaidus, child.y + child.height + bottomWidth)
        // 右下圆角
        canvasHelperRadiusRectF.set(0f, 0f, endBottomCornorRaidus.toFloat() * 2, endBottomCornorRaidus.toFloat() * 2)
        canvasHelperRadiusRectF.offsetTo(child.x + child.width + endWidth - endBottomCornorRaidus.toFloat() * 2, child.y + child.height + bottomWidth - endBottomCornorRaidus.toFloat() * 2)
        canvasHelperPath.arcTo(canvasHelperRadiusRectF, 90f, -90f)
        // 右直线
        canvasHelperPath.lineTo(child.x + child.width + endWidth, child.y - topWidth + endTopCornorRaidus.toFloat())
        // 右上圆角
        canvasHelperRadiusRectF.set(0f, 0f, endTopCornorRaidus.toFloat() * 2, endTopCornorRaidus.toFloat() * 2)
        canvasHelperRadiusRectF.offsetTo(child.x + child.width + endWidth - endTopCornorRaidus.toFloat() * 2, child.y - topWidth)
        canvasHelperPath.arcTo(canvasHelperRadiusRectF, 0f, -90f)
        // 上直线
        canvasHelperPath.close()
        shadowPaint.xfermode = null
        c?.drawPath(canvasHelperPath, shadowPaint)

        //去掉过度绘制部分
        c?.let { _c ->
            canvasHelperPath.reset()
            // 左上圆角
            canvasHelperRadiusRectF.set(0f, 0f, startTopCornorRaidus.toFloat() * 2, startTopCornorRaidus.toFloat() * 2)
            canvasHelperRadiusRectF.offsetTo(child.x, child.y)
            canvasHelperPath.arcTo(canvasHelperRadiusRectF, -90f, -90f)
            // 左直线
            canvasHelperPath.lineTo(child.x, child.y + child.height - startBottomCornorRaidus.toFloat())
            // 左下圆角
            canvasHelperRadiusRectF.set(0f, 0f, startBottomCornorRaidus.toFloat() * 2, startBottomCornorRaidus.toFloat() * 2)
            canvasHelperRadiusRectF.offsetTo(child.x, child.y + child.height - startBottomCornorRaidus.toFloat() * 2)
            canvasHelperPath.arcTo(canvasHelperRadiusRectF, 180f, -90f)
            // 下直线
            canvasHelperPath.lineTo(child.x + child.width - endBottomCornorRaidus, child.y + child.height)
            // 右下圆角
            canvasHelperRadiusRectF.set(0f, 0f, endBottomCornorRaidus.toFloat() * 2, endBottomCornorRaidus.toFloat() * 2)
            canvasHelperRadiusRectF.offsetTo(child.x + child.width - endBottomCornorRaidus.toFloat() * 2, child.y + child.height - endBottomCornorRaidus.toFloat() * 2)
            canvasHelperPath.arcTo(canvasHelperRadiusRectF, 90f, -90f)
            // 右直线
            canvasHelperPath.lineTo(child.x + child.width, child.y + endTopCornorRaidus.toFloat())
            // 右上圆角
            canvasHelperRadiusRectF.set(0f, 0f, endTopCornorRaidus.toFloat() * 2, endTopCornorRaidus.toFloat() * 2)
            canvasHelperRadiusRectF.offsetTo(child.x + child.width - endTopCornorRaidus.toFloat() * 2, child.y)
            canvasHelperPath.arcTo(canvasHelperRadiusRectF, 0f, -90f)
            // 上直线
            canvasHelperPath.close()
            shadowPaint.xfermode = shadowPaintXfermode
            _c.drawPath(canvasHelperPath, shadowPaint)
        }
    }

    private inline fun drawShadow(canvas: () -> Canvas?) {
        val c = canvas()
        repeat(childCount) { index ->
            val child = getChildAt(index)
            val lp = child.layoutParams
            if (child.visibility == VISIBLE
                && lp is DropShadowLayoutParam
                && lp.shadowColor != Color.TRANSPARENT
                && (lp.shadowWidth != 0 || lp.shadowStartWidth != 0 || lp.shadowTopWidth != 0 || lp.shadowEndWidth != 0 || lp.shadowBottomWidth != 0)) {
                when (lp.shadowMode) {
                    SHADOW_SOLID -> {
                        drawOnModeSolid({ lp }, { child }, { c })
                    }
                    SHADOW_BLUR -> {
                        drawOnModeBlur ({ lp }, { child }, { c })
                    }
                    else -> {
                    }
                }
            }
        }
    }
}