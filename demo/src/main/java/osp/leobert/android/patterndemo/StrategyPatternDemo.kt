package osp.leobert.android.patterndemo

import osp.leobert.android.pattern.IHandler
import osp.leobert.android.pattern.strategyOf

/**
 * <p><b>Package:</b> osp.leobert.android.patterndemo </p>
 * <p><b>Project:</b> DesignPatternUtils </p>
 * <p><b>Classname:</b> StrategyPatternDemo </p>
 * <p><b>Description:</b> TODO </p>
 * Created by leobert on 2022/1/20.
 */
data class Note(val str: String)

interface IDemo1 : IHandler {
    fun save(note: Note)
}

object StrategyPatternDemo {

    const val REMOTE = "REMOTE"
    const val LOCAL = "LOCAL"


    val strategy = strategyOf<String, IDemo1>()
        .register(REMOTE, object : IDemo1 {
            override fun save(note: Note) {
                println("save to remote. $note")
            }
        }).register(LOCAL, object : IDemo1 {
            override fun save(note: Note) {
                println("save to local. $note")
            }
        }).wheMiss(object :IDemo1{
            override fun save(note: Note) {
                println("cannot save $note")
            }

        })

    fun demo1(str: String,note: Note) {
        strategy.fetch(str)?.save(note)
    }

}

fun main() {

    StrategyPatternDemo.demo1(StrategyPatternDemo.REMOTE, Note("aaa"))

    StrategyPatternDemo.demo1(StrategyPatternDemo.LOCAL,Note("bbb"))

    StrategyPatternDemo.demo1("will miss",Note("ccc"))

}