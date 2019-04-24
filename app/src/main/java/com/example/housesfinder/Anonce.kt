package com.example.housesfinder

import android.net.Uri

class Anonce(area: Double, wilaya: String, price: Double, type: String, image: ArrayList<Uri>, seller: Seller) {
    var area : Double ?= area
    var wilaya : String ?= wilaya
    var price : Double ?= price
    var type : String ?= type
    var image : ArrayList<Uri> ?= image
    var seller : Seller ?= seller

}