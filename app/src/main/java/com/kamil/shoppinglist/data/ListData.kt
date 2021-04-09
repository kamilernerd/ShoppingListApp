package com.kamil.shoppinglist.data

data class ListData(val id: String, val userId: String, var listName: String) {
    constructor() : this("", "", "") {}
}
