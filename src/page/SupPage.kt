package page

import common.pack.IndexContainer.Indexable

interface SupPage<T : Indexable<*, T>?> {
    fun getSelected(): T?
    fun getThisPage(): Page? {
        return this as Page
    }
}
