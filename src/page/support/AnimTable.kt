package page.support

abstract class AnimTable<T> : AbJTable() {
    abstract fun getSelected(): Array<T?>
    abstract fun insert(dst: Int, data: Array<T>): Boolean
    abstract fun reorder(dst: Int, ori: IntArray): Boolean

    companion object {
        private const val serialVersionUID = 1L
    }
}
