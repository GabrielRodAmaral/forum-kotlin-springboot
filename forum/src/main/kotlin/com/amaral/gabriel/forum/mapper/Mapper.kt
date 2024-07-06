package com.amaral.gabriel.forum.mapper

interface Mapper<T, U> {

    fun map(t: T): U
}