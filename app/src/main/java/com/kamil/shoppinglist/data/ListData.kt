package com.kamil.shoppinglist.data

data class ListData(val id: String, val userId: String, val listName: String) {
    constructor() : this("", "", "") {}
}
