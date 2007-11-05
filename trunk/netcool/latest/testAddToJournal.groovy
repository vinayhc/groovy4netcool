/* Copyright (C) 2007 iFountain LLC. All rights reserved.
*  test for updating event fields.
*/ 

// set the serial to the Serial value of an existing event for testing

def ncAdapter = new NetcoolAdapter()
serial = ncAdapter.getSerial("testEvent1")
def myEvent = ncAdapter.getEvent(serial)
assert myEvent.Serial == serial

myEvent.addToJournal("from addToJournal test")
