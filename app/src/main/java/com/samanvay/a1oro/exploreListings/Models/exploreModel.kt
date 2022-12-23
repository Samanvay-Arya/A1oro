package com.samanvay.a1oro.exploreListings.Models

class exploreModel {

    fun availableLocationModel(title:String,secondaryImage:String, primaryImage:String,ratings:String,hotelAddress:String,features:ArrayList<String>,price:String,discountedPrice:String) {
        this.title=title
        this.secondaryImage=secondaryImage
        this.primaryImage =primaryImage
        this.ratings=ratings
        this.hotelAddress=hotelAddress
        this.features=features
        this.price=price
        this.discountedPrice=discountedPrice
    }
    var primaryImage:String =""//
    var secondaryImage:String=""//
    var ratings:String =""//
    var hotelAddress:String="" //
    var title:String=""//
    lateinit var features:ArrayList<String>
    var price:String="" //
    var discountedPrice:String="" //


}