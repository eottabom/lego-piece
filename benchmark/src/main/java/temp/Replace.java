package temp;

public class Replace {

	public static String replaceCharacters(String input) {
		return input.replace("<", "&#60;").replace(">", "&#62;").replace("?", "&#63;");
	}

	public static void main(String[] args) {
		String input = """
							public void compile(Collection<JavaFileObject> sourceFileObjects,
												Collection<String> classnames,
												Iterable<? extends Processor> processors,
												Collection<String> addModules)
							{
								// 1. 준비 및 초기화 단계
								if (!taskListener.isEmpty()) {
									taskListener.started(new TaskEvent(TaskEvent.Kind.COMPILATION));
								}

								if (hasBeenUsed)
									checkReusable();
								hasBeenUsed = true;

								options.put(XLINT_CUSTOM.primaryName + "-" + LintCategory.OPTIONS.option, "true");
								options.remove(XLINT_CUSTOM.primaryName + LintCategory.OPTIONS.option);

								start_msec = now();

								try {
									// 5. Annotation Visitor 및 Annotation Processing 단계
									initProcessAnnotations(processors, sourceFileObjects, classnames);

									for (String className : classnames) {
										int sep = className.indexOf('/');
										if (sep != -1) {
											modules.addExtraAddModules(className.substring(0, sep));
										}
									}

									for (String moduleName : addModules) {
										modules.addExtraAddModules(moduleName);
									}

									// 2. 구문 분석 (Parsing) 단계
									processAnnotations(
										enterTrees(
											stopIfError(CompileState.ENTER,
												// 3. Enter 단계
												initModules(stopIfError(CompileState.ENTER,\s
													parseFiles(sourceFileObjects))) // 여기서 파싱 수행
											)
										),
										classnames
									);

									// 4. MemberEnter 단계
									// MemberEnter 단계는 Enter 단계의 일부분으로,\s
									// 엔터트리 과정에서 클래스의 멤버를 스캔하고 심볼 테이블에 등록함

									if (taskListener.isEmpty() &&\s
										implicitSourcePolicy == ImplicitSourcePolicy.NONE) {
										todo.retainFiles(inputFiles);
									}

									if (!CompileState.ATTR.isAfter(shouldStopPolicyIfNoError)) {
										switch (compilePolicy) {
										case SIMPLE:
											// 6. 의미 분석 (Semantic Analysis) 단계 및 7. Desugar
											generate(desugar(flow(attribute(todo))));
											break;

										case BY_FILE: {
												Queue<Queue<Env<pre>>> q = todo.groupByFile();
												while (!q.isEmpty() && !shouldStop(CompileState.ATTR)) {
													// 6. 의미 분석 (Semantic Analysis) 단계 및 7. Desugar
													generate(desugar(flow(attribute(q.remove()))));
												}
											}
											break;

										case BY_TODO:
											while (!todo.isEmpty()) {
												// 6. 의미 분석 (Semantic Analysis) 단계 및 7. Desugar
												generate(desugar(flow(attribute(todo.remove()))));
											}
											break;

										default:
											Assert.error("unknown compile policy");
										}
									}
								} catch (Abort ex) {
									if (devVerbose)
										ex.printStackTrace(System.err);
								} finally {
									if (verbose) {
										elapsed_msec = elapsed(start_msec);
										log.printVerbose("total", Long.toString(elapsed_msec));
									}

									reportDeferredDiagnostics();

									if (!log.hasDiagnosticListener()) {
										printCount("error", errorCount());
										printCount("warn", warningCount());
										printSuppressedCount(errorCount(), log.nsuppressederrors, "count.error.recompile");
										printSuppressedCount(warningCount(), log.nsuppressedwarns, "count.warn.recompile");
									}
									if (!taskListener.isEmpty()) {
										taskListener.finished(new TaskEvent(TaskEvent.Kind.COMPILATION));
									}
									\s
									// 8. Generate 및 바이트 코드 생성 단계
									// 바이트 코드가 생성된 후, 자원 정리 및 종료 작업을 수행
									close();
									\s
									if (procEnvImpl != null)
										procEnvImpl.close();
								}
								// 9. 바이트 코드 생성 및 출력 단계
								// 위의 generate 메서드 호출에서 바이트 코드가 생성되어, .class 파일로 저장됨
							}
				""";

		String output = replaceCharacters(input);

		System.out.println(output);
	}

}
