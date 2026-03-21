package com.mrdarip.stepy.domain.model

private const val PERCENTILE = 0.9
private const val PERCENTILE_DROP = (1.0 - PERCENTILE) / 2.0

/**
 * Represents statistical data regarding the duration and estimated time of arrival (ETA) for steps
 * 
 * May represent a single step type or a collection of steps.
 * 
 * In the case of a collection of steps, the statistics are aggregated across all executions of those steps, providing insights into their typical durations and variability.
 *
 * @property lowerBoundETA The minimum duration recorded among the executions, in seconds.
 * @property upperBoundETA The maximum duration recorded among the executions, in seconds.
 * @property averageETA The arithmetic mean of all recorded durations, in seconds.
 * @property sampleSize The total number of execution samples used to calculate these statistics.
 */
data class StepStats(
    val lowerBoundETA: Long = 0,
    val upperBoundETA: Long = 0,
    val averageETA: Long = 0,
    val sampleSize: Int = 0
) {
    companion object {
        fun fromExecutions(executions: List<Execution>): StepStats {
            if (executions.isEmpty()) return StepStats()

            if (executions.map { it.stepId }.distinct().size == 1) {
                val executionsToDrop = (executions.size * PERCENTILE_DROP).toInt()
                val orderedExecutions = executions
                    .sortedBy { it.end - it.start } // Sort by duration of execution in ascending order
                    .dropLast(executionsToDrop) // Drop the fastest and slowest executions
                    .drop(executionsToDrop)

                val firstExecution = orderedExecutions.first()
                val lastExecution = orderedExecutions.last()

                val lowerBoundETA = firstExecution.end - firstExecution.start
                val upperBoundETA = lastExecution.end - lastExecution.start
                val averageETA = orderedExecutions.map { it.end - it.start }.average().toLong()
                val sampleSize = executions.size

                return StepStats(lowerBoundETA, upperBoundETA, averageETA, sampleSize)
            } else {
                val executionsGroupedByStep = executions.groupBy { it.stepId }
                val stepStatsList = mutableListOf<StepStats>()

                executionsGroupedByStep.map { (_, executions) ->
                    this.fromExecutions(executions)
                }.forEach {
                    stepStatsList.add(it)
                }

                val lowerBoundETA = stepStatsList.sumOf { it.lowerBoundETA }
                val upperBoundETA = stepStatsList.sumOf { it.upperBoundETA }
                val averageETA = stepStatsList.sumOf { it.averageETA }
                val sampleSize = stepStatsList.sumOf { it.sampleSize }

                return StepStats(lowerBoundETA, upperBoundETA, averageETA, sampleSize)
            }
        }
    }
}
