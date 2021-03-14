package com.kamil.shoppinglist.data

data class ListItem(val itemName: String) {
    override fun toString(): String {
        return "List ${itemName}"
    }
}
