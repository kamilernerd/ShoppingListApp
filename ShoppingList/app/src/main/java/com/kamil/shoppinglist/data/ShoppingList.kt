package com.kamil.shoppinglist.data

data class ShoppingList(val listName: String, val listItems: MutableList<ListItem>) {
    override fun toString(): String {
        return "List ${listName}"
    }
}
