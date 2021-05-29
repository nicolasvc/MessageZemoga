package com.example.messagezemoga.transversal.constants

class RetrofitConstants {

    companion object {
        const val URL_BASE : String = "https://jsonplaceholder.typicode.com/"
        const val GET_ALL_POSTS : String = "posts"
        const val GET_COMMENTS_FOR_POST : String = "posts/{idPost}/comments"
        const val GET_ALL_USERS : String = "users"

    }
}