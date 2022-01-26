package osp.leobert.android.pattern

class Strategy<K, H : IHandler>(
    private val distinguish: IDistinguish<K, H> = DefaultDistinguish()
) {
    private val strategyMap: MutableMap<K, H> = hashMapOf()

    private var whenMiss:H? = null

    fun register(key: K, handler: H): Strategy<K, H> {
        synchronized(this) {
            strategyMap[key] = handler
            return this
        }
    }

    fun wheMiss(handler: H): Strategy<K, H> {
        synchronized(this) {
            whenMiss = handler
            return this
        }
    }

    fun fetch(key: K): H? {
        synchronized(this) {
            return distinguish.fetch(strategyMap, key)?:whenMiss
        }
    }
}

fun <K, H : IHandler> strategyOf():Strategy<K, H> {
    return Strategy()
}

fun <K, H : IHandler> strategyOf(distinguish: IDistinguish<K, H>):Strategy<K, H> {
    return Strategy(distinguish)
}


interface IHandler

interface IDistinguish<KEY, H : IHandler> {
    fun fetch(strategyMap: Map<KEY, H>, key: KEY): H?
}

private class DefaultDistinguish<KEY, H : IHandler> : IDistinguish<KEY, H> {
    override fun fetch(strategyMap: Map<KEY, H>, key: KEY): H? {
        return strategyMap[key]
    }
}