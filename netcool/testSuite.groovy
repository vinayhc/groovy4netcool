eventSerialForTest = 719

runTest(new testUpdateEventFields())
//runTest(new g4nTest2())



def runTest(testInstance){
	try{
		testInstance.binding = new Binding(serial: eventSerialForTest)
		testInstance.run();
	}
		catch(AssertionError ae){
		println "FAILED TEST " +  testInstance.getClass();
		println ae.getMessage();
	}
}
