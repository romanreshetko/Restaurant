package kitchen

import commands.Order
import java.util.concurrent.Semaphore

private const val NUMBER_OF_COOKS = 5

object Kitchen {

    private val s = Semaphore(NUMBER_OF_COOKS)

    fun prepareOrder(order: Order) {
        order.accepted()
        Thread(Runnable {
            val threads = mutableListOf<Thread>()
            var i = 0
            while (i < order.list.size) {
                val localI = i
                val thread = Thread(Runnable {
                    s.acquire()
                    order.startCooking()
                    order.list[localI].execute()
                    s.release()
                })
                if (order.status == "Canceled") {
                    break
                }
                threads.add(thread)
                thread.start()
                ++i
            }
            threads.forEach { it.join() }
            order.finishCooking()
        }).start()
    }
}