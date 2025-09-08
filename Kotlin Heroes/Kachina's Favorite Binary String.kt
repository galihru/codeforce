import java.io.BufferedInputStream
import java.io.InputStream

private class FastScanner(private val input: InputStream = BufferedInputStream(System.`in`)) {
    private val buf = ByteArray(1 shl 16)
    private var len = 0
    private var ptr = 0
    private fun read(): Int {
        if (ptr >= len) {
            len = input.read(buf)
            ptr = 0
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
    val T = fs.nextInt()

    repeat(T) {
        val n = fs.nextInt()

        // Ask n-1 queries: F[i] = f(1, i) for i = 2..n
        val F = IntArray(n + 1)
        F[1] = 0
        for (i in 2..n) {
            println("? 1 $i")
            System.out.flush()
            F[i] = fs.nextInt()
        }

        // Reconstruct s from the sequence of deltas D[i] = F[i] - F[i-1]
        val s = CharArray(n) { '?' }
        var zeros = 0       // jumlah '0' yang sudah kita tetapkan di prefiks yang fix
        var cut = 0         // indeks terakhir yang sudah dipastikan (1-based)
        var prev = 0        // F[i-1]

        for (i in 2..n) {
            val d = F[i] - prev
            when {
                // Kita perlu jumlah '0' sebelum i sebesar d; tambah di blok [cut+1 .. i-1]
                d > zeros -> {
                    val need = d - zeros
                    val l = cut + 1
                    val r = i - 1
                    // isi yang paling kanan 'need' posisi sebagai '0', sisanya '1'
                    val split = r - need
                    for (j in l..split) s[j - 1] = '1'
                    for (j in split + 1..r) s[j - 1] = '0'
                    cut = r
                    zeros = d
                    s[i - 1] = '1'
                    cut = i
                }
                d == zeros && zeros > 0 -> {
                    // tak perlu menambah '0' di blok tak pasti, semuanya '1'
                    val l = cut + 1
                    val r = i - 1
                    for (j in l..r) s[j - 1] = '1'
                    cut = r
                    s[i - 1] = '1'
                    cut = i
                }
                else -> { // d == 0
                    if (zeros > 0) {
                        // ada '0' sebelumnya, jadi karakter ini pasti '0'
                        val l = cut + 1
                        val r = i - 1
                        for (j in l..r) s[j - 1] = '1'
                        cut = r
                        s[i - 1] = '0'
                        zeros++
                        cut = i
                    } else {
                        // belum ada '0' sama sekali → belum bisa dipastikan (biarkan '?')
                    }
                }
            }
            prev = F[i]
        }

        if (zeros == 0) {
            // Semua jawaban nol
            println("! IMPOSSIBLE")
            System.out.flush()
        } else {
            // Sisa yang belum dipastikan (prefiks awal sebelum '0' pertama) semuanya '1'
            for (i in 0 until n) if (s[i] == '?') s[i] = '1'
            println("! ${String(s)}")
            System.out.flush()
        }
    }
}
