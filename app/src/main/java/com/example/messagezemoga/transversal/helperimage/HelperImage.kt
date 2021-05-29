package com.example.messagezemoga.transversal.helperimage

import com.example.messagezemoga.R
import kotlin.random.Random

class HelperImage {

    companion object {
        fun getRandoProfilePicture(): Int {
            val randonImage = List(1){ Random.nextInt(0,5)}
            return when (randonImage[0]) {
                0 -> R.drawable.person1
                1 -> R.drawable.person2
                2 -> R.drawable.person3
                3 -> R.drawable.person4
                4 -> R.drawable.person5
                5 -> R.drawable.person6
                else -> R.drawable.person1
            }
        }
    }
}