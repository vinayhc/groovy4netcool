/* Copyright (C) 2007 iFountain LLC. All rights reserved.
*  test for updating event fields.
*/ 

def ncAdapter = new NetcoolAdapter()

def fields = [:]
fields.Severity = 1
fields.Identifier = "testEvent1"
fields.Node = "Test"
fields.AlertGroup = "Test events"
fields.AlertKey = "Test event 1"
fields.Summary = "Created as a test event"
fields.LastOccurrence = ncAdapter.now()
def newEvent = ncAdapter.createEvent(fields)

assert newEvent.Identifier == fields.Identifier
println newEvent.Serial


