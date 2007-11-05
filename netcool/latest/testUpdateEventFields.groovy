/* Copyright (C) 2007 iFountain LLC. All rights reserved.
*  test for updating event fields.
*/ 

serial = 719
def ncAdapter = new NetcoolAdapter()
def myEvent = ncAdapter.getEvent(serial)
assert myEvent.Serial == serial

myEvent.TaskList = 1
myEvent.Summary = "Testing: updated directly"
myEvent.NodeAlias = myEvent.Node + " test1"

myEvent = ncAdapter.getEvent(serial)
assert myEvent.TaskList == 1
assert myEvent.Summary == "Testing: updated directly"
assert myEvent.NodeAlias == myEvent.Node + " test1"

def updateFields =[:]
updateFields.NodeAlias = myEvent.Node + " test2"
updateFields.Summary = "Testing: updated using update method"
updateFields.TaskList = 0
myEvent.update(updateFields)

myEvent = ncAdapter.getEvent(serial)
assert myEvent.NodeAlias == myEvent.Node + " test2"
assert myEvent.Summary == "Testing: updated using update method"
assert myEvent.TaskList == 0

