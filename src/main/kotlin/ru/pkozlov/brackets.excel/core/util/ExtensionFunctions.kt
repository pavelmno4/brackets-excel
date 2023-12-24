package ru.pkozlov.brackets.excel.core.util

import java.util.*

inline fun <T, K> Iterable<T>.groupByDeque(keySelector: (T) -> K): Map<K, Deque<T>> {
    return groupByDequeTo(LinkedHashMap<K, Deque<T>>(), keySelector)
}

inline fun <T, K, M : MutableMap<in K, Deque<T>>> Iterable<T>.groupByDequeTo(destination: M, keySelector: (T) -> K): M {
    for (element in this) {
        val key = keySelector(element)
        val list = destination.getOrPut(key) { LinkedList<T>() }
        list.addLast(element)
    }
    return destination
}