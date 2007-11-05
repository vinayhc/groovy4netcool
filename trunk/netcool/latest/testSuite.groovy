
runTest(new testAddEvent())
runTest(new testAddSummaryEvent())
runTest(new testUpdateEventFields())
runTest(new testAddToJournal())
runTest(new testRetrieveEvents())

def runTest(testInstance){
	try{
		testInstance.run();
	}
		catch(AssertionError ae){
		println "FAILED TEST " +  testInstance.getClass();
		println ae.getMessage();
	}
}
