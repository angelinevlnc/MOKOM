package com.proyekmokom.chastethrift

class FakeList (
    var id: Int,
    var gambar: Int,
    var nama: String,
    var harga: Int
){
    companion object{
        val catalog = mutableListOf<FakeList>(
            FakeList(1,R.drawable.gucci,"Baju Gucci Authentic",30000),
            FakeList(2,R.drawable.nike,"Nike Air Force 1",500000),
            FakeList(3,R.drawable.kucing,"Kucing Persia",5000),
            FakeList(4,R.drawable.tas,"Tas Putih CNK",100000),
            FakeList(5,R.drawable.sepatu,"Sepatu Bola Bekas Messi",500000),
            FakeList(6,R.drawable.laptop,"Laptop MSI Ryzen 10",100000)
        )
    }
}