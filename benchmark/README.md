

### final string benchmark result

---

| Benchmark                                      | Mode  | Cnt| Score | Error    | Units |
|------------------------------------------------|-------|----|-------|----------|-------|
| BenchmarkRunner.compilerOptimizationString| avgt  |5| 0.339 | ± 0.006  | ns/op |
| BenchmarkRunner.finalStrings| avgt  |5| 0.340 | ± 0.004  | ns/op |
| BenchmarkRunner.nonFinalStrings| avgt |5| 4.404 | ± 0.188  | ns/op |


### final method benchmark result

---

| Benchmark                               |Mode|Cnt| Score    | Error           | Units |
|-----------------------------------------|--|--|----------|-----------------|-------|
| BenchmarkRunner.finalMethodBenchmark    |avgt|5| 355.031  | ±6.035  | ns/op |
| BenchmarkRunner.nonFinalMethodBenchmark |avgt|5| 369.924  | ± 18.398  | ns/op |


### logger disabled vs logger enabled vs system.out.println

| Benchmark                               |Mode|Cnt| Score    | Error           | Units |
|-----------------------------------------|--|--|----------|-----------------|-------|
|LoggingBenchmark.benchmarkLoggerDisabled    |thrpt   |10  |2380.160 |± 528.324  | ops/s |
|LoggingBenchmark.benchmarkLoggerEnabled     |thrpt   |10  |1291.681 |± 304.764  | ops/s |
|LoggingBenchmark.benchmarkSystemOutPrintln  |thrpt   |10     |5.920 |±   0.760  | ops/s |
