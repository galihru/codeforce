import java.io.BufferedInputStream
import java.io.InputStream

private class FastScanner(private val input: InputStream = BufferedInputStream(System.`in`)) {
    private val buf = ByteArray(1 shl 16)
    private var len = 0
    private var ptr = 0
    private fun read(): Int {
        if (ptr >= len) {
            len = input.read(buf); ptr = 0
            if (len <= 0) return -1
        }
        return buf[ptr++].toInt()
    }
    fun nextInt(): Int {
        var c = read()
        while (c <= 32) c = read()
        var sgn = 1
        if (c == '-'.code) { sgn = -1; c = read() }
        var x = 0
        while (c > 32) { x = x * 10 + (c - '0'.code); c = read() }
        return x * sgn
    }
}

fun main() {
    val fs = FastScanner()
    val t = fs.nextInt()
    val out = StringBuilder()

    repeat(t) {
        val n = fs.nextInt()
        val a = IntArray(n) { fs.nextInt() }

        // Build logs and sparse table for bitwise AND
        val lg = IntArray(n + 1)
        for (i in 2..n) lg[i] = lg[i shr 1] + 1
        val K = lg[n] + 1
        val st = Array(K) { IntArray(n) }
        for (i in 0 until n) st[0][i] = a[i]
        var kPow = 1
        for (k in 1 until K) {
            val limit = n - (kPow shl 1) + 1
            val half = kPow
            for (i in 0 until n - kPow) {
                st[k][i] = st[k - 1][i] and st[k - 1][i + half]
            }
            kPow = kPow shl 1
        }

        fun rangeAnd(L: Int, R: Int): Int {
            val len = R - L + 1
            val k = lg[len]
            return st[k][L] and st[k][R - (1 shl k) + 1]
        }

        val q = fs.nextInt()
        repeat(q) {
            val l1 = fs.nextInt() - 1
            val need = fs.nextInt()
            if (a[l1] < need) { // AND can only go down from a[l1]
                out.append("-1\n")
            } else {
                var lo = l1
                var hi = n - 1
                var ans = l1
                while (lo <= hi) {
                    val mid = (lo + hi) ushr 1
                    if (rangeAnd(l1, mid) >= need) {
                        ans = mid
                        lo = mid + 1
                    } else hi = mid - 1
                }
                out.append(ans + 1).append('\n') // back to 1-based
            }
        }
    }

    print(out.toString())
}
