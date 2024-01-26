package com.aamirashraf.model

data class Item(var title: String, var body: String, var isChecked: Boolean = false){
    constructor(): this("", "")
}

