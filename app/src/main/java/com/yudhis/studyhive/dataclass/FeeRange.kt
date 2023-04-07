package com.yudhis.studyhive.dataclass

data class FeeRange (
    val bottomLimit: Int,
    val topLimit: Int
)

val feeRanges: MutableSet<FeeRange> = mutableSetOf(
    FeeRange(0,0),
    FeeRange(0, 49_999),
    FeeRange(50_000, 999_999),
    FeeRange(100_000, 199_999),
    FeeRange(200_000, 299_999),
    FeeRange(300_000, 499_999),
    FeeRange(500_000, 749_999),
    FeeRange(750_000, 1000_000)
)
