package com.pedro.hernandez.buscam.Model

class ModeloImgSlider {
    var id : String = ""
    var imagenUrl : String = ""

    constructor()

    constructor(id: String, imagenUrl: String) {
        this.id = id
        this.imagenUrl = imagenUrl
    }
}