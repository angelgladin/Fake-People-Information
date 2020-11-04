package xyz.people.data.entity

data class User(
    val results: List<Result>
)

data class Result(
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val login: Login,
    val picture: Picture
)

data class Name(
    val title: String,
    val first: String,
    val last: String
)

data class Location(
    val country: String,
    val coordinates: Coordinate
)

data class Coordinate(
    val latitude: String,
    val longitude: String
)

data class Login(
    val uuid: String
)

data class Picture(
    val large: String
)