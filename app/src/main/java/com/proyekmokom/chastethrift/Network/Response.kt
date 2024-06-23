package com.proyekmokom.chastethrift.Network

import com.google.gson.annotations.SerializedName

data class UploadResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("title")
	val title: String,
)
