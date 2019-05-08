package com.zhu.bishopscores

import android.widget.RadioGroup

fun RadioGroup.checkedIndex(): Int {
    return this.indexOfChild(findViewById(this.checkedRadioButtonId))
}