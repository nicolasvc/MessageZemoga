package com.example.messagezemoga.origindata.post

import com.example.messagezemoga.transversal.constants.RetrofitConstants
import com.example.messagezemoga.transversal.retrofithelper.RetrofitHelper

class PostClient {

    companion object {
        fun getClient(): PostOriginData =
            RetrofitHelper.getCliente(RetrofitConstants.URL_BASE).create(PostOriginData::class.java)
    }

}