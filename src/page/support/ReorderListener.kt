package page.support

interface ReorderListener<T> {
    fun add(t: T): Boolean {
        return false
    }

    fun reordered(ori: Int, fin: Int)
    fun reordering()
}
