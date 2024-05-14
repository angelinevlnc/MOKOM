package com.proyekmokom.chastethrift

class FakeUserList (
    var id: Int,
    var gambar: Int,
    var username: String,
    var password: String,
    val role:Int // 1 = penjual; 2 = pembeli
){
    companion object{
        val user = mutableListOf<FakeUserList>(
            FakeUserList(1,R.drawable.laptop,"penjual","penjual", 1),
            FakeUserList(2,R.drawable.kucing,"pembeli","pembeli", 2)
        )
    }
}