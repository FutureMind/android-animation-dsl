package com.fm.android.animatorsdsl.lib

import android.graphics.PointF
import android.view.View

fun View.topLeft(): PointF = PointF(0f, 0f)
fun View.topRight(): PointF = PointF(0f, measuredHeight.toFloat())
fun View.bottomRight(): PointF = PointF(measuredWidth.toFloat(), measuredHeight.toFloat())
fun View.bottomLeft(): PointF = PointF(measuredWidth.toFloat(), 0f)
fun View.center(): PointF = PointF(measuredWidth.toFloat() / 2, measuredHeight.toFloat() / 2)