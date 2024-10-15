

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
| LoggingBenchmark.benchmarkJavaLoggerDisabled | thrpt| 10 | 2618.238 | ±  90.077  | ops/s |
| LoggingBenchmark.benchmarkJavaLoggerEnabled  | thrpt| 10   | 2.986 | ±   0.711  | ops/s |
| LoggingBenchmark.benchmarkLoggerDisabled     | thrpt| 10 | 2480.615 | ± 272.262 | ops/s |
| LoggingBenchmark.benchmarkLoggerEnabled      | thrpt| 10 | 1419.497 | ± 221.123 | ops/s |
| LoggingBenchmark.benchmarkSystemOutPrintln   | thrpt| 10  |    6.468 | ±   0.692 | ops/s |


### JSON vs Protobuf 

| Benchmark                               | Mode | Cnt | Score    | Error    | Units |
|-----------------------------------------|------|-----|----------|----------|-------|
| SerializationBenchmark.jsonBenchmark    | thrpt | 10  | 3550.345 | ± 46.399 | ops/s |
| SerializationBenchmark.protobufBenchmark | thrpt | 10  | 4203.831 | ± 97.497 | ops/s |