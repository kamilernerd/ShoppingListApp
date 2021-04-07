package com.kamil.shoppinglist.data

data class ListItem(val id: String, var itemName: String, val listId: String, var checked: Boolean) {
    constructor() : this("", "", "", false) {}
}
